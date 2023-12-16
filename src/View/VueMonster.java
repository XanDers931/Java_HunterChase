package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import Controller.ControlMonster;
import Controller.ControlMonsterBot;
import Controller.ControlMonsterPlayer;
import Main.Maps;
import Menu.Menu;
import Model.Hunter;
import Model.Monster;
import Utils.Coordinate;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class VueMonster implements Observer {
    private Monster monster;
    private ControlMonster controlleur;
    private GridPane gridPane;
    private Stage stage;
    private Label currentLabel;

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Monster getMonster() {
        return monster;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public VueMonster(Monster monster, Hunter hunter, boolean control) {
        this.monster = monster;
        if (!control) {
            this.controlleur = new ControlMonsterPlayer(this);
        } else {
            this.controlleur = new ControlMonsterBot(this);
        }
        this.stage = creerStage();
        monster.attach(this);
    }

    public Stage creerStage() {
        Stage stage = new Stage();
        gridPane = new GridPane();
        chargePlateau();
        HBox hbox = new HBox(new Label("MONSTER"));
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        currentLabel = new Label("C'est au tour du Monstre");
        vbox.getChildren().add(currentLabel);
        vbox.setAlignment(Pos.CENTER);
        int imageSize = 50; 
        controlleur.mMouvement();
        VBox vboxall = new VBox(hbox,vbox, gridPane);
        styleGridPane(imageSize);
        int rowCount = monster.getGameModel().getMap().getRow()+2 ;
        int colCount = monster.getGameModel().getMap().getCol()+2;
        Scene scene = new Scene(vboxall, imageSize * rowCount,imageSize * colCount);
        stage.setScene(scene);
        return stage;
    }

    public Label getCurrentLabel() {
        return currentLabel;
    }

    public void setCurrentLabel(Label currentLabel) {
        this.currentLabel = currentLabel;
    }

    public void chargePlateau() {
        gridPane.getChildren().clear();
        Maps map = monster.getGameModel().getMap();
        for (int i = 0; i < map.getRow(); i++) {
            for (int j = 0; j < map.getCol(); j++) {
                StackPane stackPane = createStackPaneWithBorder(map.getMaps()[i][j]);
                gridPane.add(stackPane, j, i);
            }
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

    public void updatePlateau(int clickedRow, int clickedCol) {
        // Récupération des coordonnées du monstre
        Coordinate monsterCoordinates = monster.getCordMonster();
        int row = monsterCoordinates.getRow();
        int col = monsterCoordinates.getCol();
    
        // Récupération des nœuds correspondants aux positions cliquée et du monstre dans le GridPane
        Node clickedNode = getNodeByRowColumnIndex(clickedRow, clickedCol, gridPane);
        Node monsterNode = getNodeByRowColumnIndex(row, col, gridPane);
    
        if (clickedNode instanceof StackPane && monsterNode instanceof StackPane) {
            StackPane existingStackPane = (StackPane) clickedNode;
            StackPane monsterStackPane = (StackPane) monsterNode;
    
            // Mise à jour de l'image du monstre
            updateMonsterImage(monsterStackPane);
    
            // Mise à jour du label du monstre
            updateMonsterLabel(monsterStackPane);
    
            // Réarrangement des panneaux dans le GridPane
            rearrangePanes(existingStackPane, monsterStackPane);
    
            // Mise à jour de l'image cliquée
            updateClickedImage(existingStackPane);
    
            // Application du style aux panneaux
            applyPaneStyles(existingStackPane, monsterStackPane);
        }
    }
    
    private void updateMonsterImage(StackPane monsterStackPane) {
        String imagePathMonster = determineImagePath(CellInfo.EMPTY);
        Image imageMonster = new Image(getClass().getResourceAsStream(imagePathMonster));
        ImageView monsterImageView = (ImageView) monsterStackPane.getChildren().get(0);
        monsterImageView.setImage(imageMonster);
    }
    
    private void updateMonsterLabel(StackPane monsterStackPane) {
        Label label;
        if (monsterStackPane.getChildren().size() > 1 && monsterStackPane.getChildren().get(1) instanceof Label) {
            // Si le label existe déjà, mettez à jour son texte
            label = (Label) monsterStackPane.getChildren().get(1);
            label.setText(String.valueOf(monster.getGameModel().getTurn()));
        } else {
            // Sinon, créez un nouveau label
            label = new Label(String.valueOf(monster.getGameModel().getTurn()));
            label.setTextFill(Color.BLACK);
            monsterStackPane.getChildren().add(label);
        }
    }
    
    private void rearrangePanes(StackPane existingStackPane, StackPane monsterStackPane) {
        existingStackPane.toBack();
        monsterStackPane.toBack();
    }
    
    private void updateClickedImage(StackPane existingStackPane) {
        String imagePathClicked = determineImagePath(CellInfo.MONSTER);
        Image imageClicked = new Image(getClass().getResourceAsStream(imagePathClicked));
        ImageView existingImageView = (ImageView) existingStackPane.getChildren().get(0);
        existingImageView.setImage(imageClicked);
    }
    
    private void applyPaneStyles(StackPane existingStackPane, StackPane monsterStackPane) {
        existingStackPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        monsterStackPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    
    private void styleGridPane(double imageSize) {
        gridPane.setStyle("-fx-background-color: #ececec;");
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(1);          
        gridPane.setVgap(1);
        
        for (Node node : gridPane.getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                stackPane.setMinSize(imageSize, imageSize);
                stackPane.setMaxSize(imageSize, imageSize);
            }
        }
    }

    public Stage getStage() {
        return this.stage;
    }

    public void showVictoryMessage() {
        Stage victoryStage = new Stage();
        victoryStage.setTitle("Victory!");
        Label victoryLabel = new Label("Congratulations! Monster won!");
        Button replay = new Button("Retour au menu");
        replay.setOnAction(e->{
            Menu menu = new Menu();
            victoryStage.close();
            fermerTousLesStages();
            fermerStage();
            menu.createStage().show();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            // Ferme toutes les fenêtres ouvertes
           victoryStage.close();
           fermerTousLesStages();
           fermerStage();
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
    
    @Override
    public void update(Subject subj) {
        update(subj, null);
    }

    @Override
    public void update(Subject subj, Object data) {
        chargePlateau();
    }
}