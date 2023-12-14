package View;


import java.util.Objects;

import Controller.ControlHunter;
import Controller.ControlHunterBot;
import Controller.ControlHunterPlayer;
import Main.Maps;
import Model.Hunter;
import Utils.Coordinate;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import Main.Menus;


public class VueHunter implements Observer {
    private Hunter hunter;
    private GridPane gridPane;
    private ControlHunter controlleur;
    private Stage stage;

    public VueHunter(Hunter hunter,boolean control) {
        this.hunter = hunter;
        if(!control){
            this.controlleur = new ControlHunterPlayer(this);
        }else{
            this.controlleur = new ControlHunterBot(this);
        }
        this.stage = creerStage();
        hunter.attach(this);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public Stage creerStage() {
        Stage stage = new Stage();
        gridPane = new GridPane();
        HBox hbox = new HBox(new Label("HUNTER"));
        hbox.setAlignment(Pos.CENTER);

        int imageSize = 50; // Taille de l'image ajustée à 50
        chargePlateau(-1, -1, imageSize);
        controlleur.hMouvement();
        styleGridPane(gridPane, imageSize);

        int rowCount = hunter.getGameModel().getMap().getRow()+1 ;
        int colCount = hunter.getGameModel().getMap().getCol()+1;

        VBox vbox = new VBox(hbox, gridPane);
        Scene scene = new Scene(vbox, colCount * imageSize, rowCount * imageSize);
        stage.setScene(scene);
        return stage;
    }

    public void chargePlateau(int row, int col, double imageSize) {
        gridPane.getChildren().clear();
        Maps map = hunter.getGameModel().getMap();
        boolean[][] mapShoot = map.getMapShoot();
        CellInfo[][] maps = map.getMaps();

        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++) {
                Image image = new Image(getClass().getResourceAsStream("carrenoir.png"));
                StackPane stackPane = createStackPaneWithBorder(map.getMaps()[i][j]);
                ImageView imageView= (ImageView) stackPane.getChildren().get(0);
                imageView.setImage(image);

                if (mapShoot[i][j]) {
                    stackPane = createStackPaneWithBorder(map.getMaps()[i][j]);
                    stackPane.setStyle("-fx-border-color: red; -fx-border-width: 1;"); // Bordure rouge pour les tirs
                }
                gridPane.add(stackPane, j, i);
            }
        }
    }

    private String determineImagePath(CellInfo cellInfo) {
        switch (cellInfo) {
            case WALL:
                return "trou.jpg";
            case MONSTER:
                return "loup.jpg";
            case EXIT:
                return "monstre.avif";
            default:
                return "green.jpg";
        }
    }

    private StackPane createStackPaneWithBorder(CellInfo cellInfo) {
        StackPane stackPane = new StackPane();
        String imagePath = determineImagePath(cellInfo);
        Image image = new Image(getClass().getResourceAsStream(imagePath));
    
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
    
        stackPane.getChildren().add(imageView);
    
        return stackPane;
    }

    public void updatePlateau() {
        // Récupération des coordonnées de la case cliquée
        Coordinate clickedCoordinates = controlleur.getClickedCase();
        int clickedRow = clickedCoordinates.getRow();
        int clickedCol = clickedCoordinates.getCol();
    
        // Récupération du nœud correspondant à la case cliquée dans le GridPane
        Node node = getNodeByRowColumnIndex(clickedRow, clickedCol, gridPane);
    
        if (node != null && node instanceof StackPane) {
            StackPane existingStackPane = (StackPane) node;
    
            // Mise à jour de l'image de la case cliquée
            updateClickedImage(existingStackPane, clickedRow, clickedCol);
    
            // Mise à jour du style de l'image
            updateImageStyle(existingStackPane);
    
            // Vérification et ajout d'un label s'il y a un chemin
            addPathLabel(existingStackPane, clickedRow, clickedCol);
    
            // Réarrangement du panneau dans le GridPane
            existingStackPane.toBack();
        }
    }
    
    private void updateClickedImage(StackPane existingStackPane, int clickedRow, int clickedCol) {
        String imagePathClicked = determineImagePath(hunter.getGameModel().getMap().getMaps()[clickedRow][clickedCol]);
        Image imageClicked = new Image(getClass().getResourceAsStream(imagePathClicked));
        ImageView existingImageView = (ImageView) existingStackPane.getChildren().get(0);
        existingImageView.setImage(imageClicked);
    }
    
    private void updateImageStyle(StackPane existingStackPane) {
        ImageView existingImageView = (ImageView) existingStackPane.getChildren().get(0);
        existingImageView.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
    }
    
    private void addPathLabel(StackPane existingStackPane, int clickedRow, int clickedCol) {
        if (hunter.getGameModel().getPath().containsKey(new Coordinate(clickedRow, clickedCol))) {
            int path = hunter.getGameModel().getPath(new Coordinate(clickedRow, clickedCol));
            Label label = new Label(String.valueOf(path));
            existingStackPane.getChildren().add(label);
        }
    }
    

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public void styleGridPane(GridPane pane, double imageSize) {
        pane.setStyle("-fx-background-color: #ececec;");
        pane.setPadding(new Insets(2));
        pane.setHgap(1);
        pane.setVgap(1);

        for (Node node : pane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                imageView.setFitWidth(imageSize);
                imageView.setFitHeight(imageSize);
            }
        }
    }

    public Stage getStage() {
        return this.stage;
    }

   

    private void fermerToutesLesFenetres() {
        int nombreDeFenetres = Stage.getWindows().size();
    
        if (nombreDeFenetres == 1) {
            return;
        }
    
        for (Window stage : Stage.getWindows()) {
            Stage stageCasted = Objects.requireNonNull((Stage) stage);
            stageCasted.close();
        }
    }

    public void showVictoryMessage() {
        Stage victoryStage = new Stage();
        victoryStage.setTitle("Victory!");

        Label victoryLabel = new Label("Congratulations! Hunter won!");
        Button replay = new Button("Replay");

        replay.setOnAction(e->{
            victoryStage.close();
            fermerTousLesStages();
            fermerStage();
            createMenu().show();
            fermerToutesLesFenetres();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            // Ferme toutes les fenêtres ouvertes
           victoryStage.close();
           fermerTousLesStages();
           fermerStage();
           fermerToutesLesFenetres();
        });
        VBox vbox = new VBox(victoryLabel, replay,closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        Scene victoryScene = new Scene(vbox, 300, 200);

        victoryStage.setScene(victoryScene);

        victoryStage.show();


    }
    public void fermerStage() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
    }
    

    private void fermerTousLesStages() {
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage && !((Stage) window).isIconified()) {
                ((Stage) window).close();
            }
        }

      
    }
    
    public Stage createMenu(){
        Stage primaryStage= new Stage();
        Menus menu = new Menus();
        menu.setPrimaryStage(primaryStage);
        menu.getPrimaryStage().setTitle("Monster Hunter");
        menu.createMainMenu();
        //menu.createRulesPage();
        primaryStage.setScene(menu.getMainMenuScene());
        return primaryStage;
    }
    

    @Override
    public void update(Subject subj) {
        update(subj, null);
    }

    @Override
    public void update(Subject subj, Object data) {
        if (data instanceof Coordinate) {
            Coordinate coordonnees = (Coordinate) data;
            int row = coordonnees.getRow();
            int col = coordonnees.getCol();
            chargePlateau(row, col, 50); 
        }
    }
}
