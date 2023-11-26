package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Controller.ControlMonster;
import Main.Maps;
import Model.Hunter;
import Model.Monster;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class VueMonster implements Observer {
    private Monster monster;
    private ControlMonster controlleur;
    private GridPane gridPane;
    private Stage stage;

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Monster getMonster() {
        return monster;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public VueMonster(Monster monster, Hunter hunter) {
        this.monster = monster;
        this.controlleur = new ControlMonster(this);
        this.stage = creerStage();
        monster.attach(this);
    }

    public Stage creerStage() {
        Stage stage = new Stage();
        gridPane = new GridPane();
        chargePlateau();
        HBox hbox = new HBox(new Label("MONSTER"));
        hbox.setAlignment(Pos.CENTER);

        int imageSize = 50; // Taille de l'image ajustée à 50
        
        controlleur.mMouvement();
        //controlleur.botMouvement();

        VBox vbox = new VBox(hbox, gridPane);
        styleGridPane(imageSize);
        Scene scene = new Scene(vbox, imageSize * 11,imageSize * 11);
        stage.setScene(scene);
        return stage;
    }

    public void chargePlateau() {
        gridPane.getChildren().clear();
        Maps map = monster.getGameModel().getMap();
        for (int i = 0; i < map.getMaps().length; i++) {
            for (int j = 0; j < map.getMaps()[i].length; j++) {
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
        int row = Monster.getCordMonster().getRow();
        int col = Monster.getCordMonster().getCol();
        
    
        Node node = getNodeByRowColumnIndex(clickedRow, clickedCol, gridPane);
        Node nodeMonster = getNodeByRowColumnIndex(row, col, gridPane);
    
        if (node != null && node instanceof StackPane) {
            StackPane existingStackPane = (StackPane) node;
            StackPane monsterStackPane = (StackPane) nodeMonster;
    
            // Mise à jour de l'image du monstre
            String imagePathMonster = determineImagePath(CellInfo.EMPTY);
            Image imageMonster = new Image(getClass().getResourceAsStream(imagePathMonster));
            ImageView monsterImageView = (ImageView) monsterStackPane.getChildren().get(0);
            monsterImageView.setImage(imageMonster);
    
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
    
            existingStackPane.toBack();
            monsterStackPane.toBack();
    
            // Mise à jour de l'image cliquée
            String imagePathClicked = determineImagePath(CellInfo.MONSTER);
            Image imageClicked = new Image(getClass().getResourceAsStream(imagePathClicked));
            ImageView existingImageView = (ImageView) existingStackPane.getChildren().get(0);
            existingImageView.setImage(imageClicked);
    
            existingStackPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
            monsterStackPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        }
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

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> victoryStage.close());

        VBox vbox = new VBox(victoryLabel, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        Scene victoryScene = new Scene(vbox, 300, 200);

        victoryStage.setScene(victoryScene);

        victoryStage.show();
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