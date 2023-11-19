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
        controlleur.mMouvement();
        VBox vbox = new VBox(hbox, gridPane);
        styleGridPane();
        Scene scene = new Scene(vbox, 900, 900);
        stage.setScene(scene);
        return stage;
    }

    public void chargePlateau() {
        gridPane.getChildren().clear();
        Maps map = monster.getGameModel().getMap();
        for (int i = 0; i < map.getMaps().length; i++) {
            for (int j = 0; j < map.getMaps()[i].length; j++) {
                ImageView imageView = createImageViewWithBorder(map.getMaps()[i][j]);
                ColorAdjust color = new ColorAdjust();
                color.setHue(0.5);
                imageView.setEffect(color);

                gridPane.add(imageView, j, i);
            }
        }
    }

    private String determineImagePath(CellInfo cellInfo) {
        switch (cellInfo) {
            case WALL:
                return "ground.png";
            case MONSTER:
                return "monstre.png";
            default:
                return "ground.png";
        }
    }

    private ImageView createImageViewWithBorder(CellInfo cellInfo) {
        ImageView imageView = new ImageView();
        String imagePath = determineImagePath(cellInfo);
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }

    public void updatePlateau() {
        int clickedRow = controlleur.getClickedCase().getRow();
        int clickedCol = controlleur.getClickedCase().getCol();
        int row = Monster.getCordMonster().getRow();
        int col = Monster.getCordMonster().getCol();

        Node node = getNodeByRowColumnIndex(clickedRow, clickedCol, gridPane);
        Node nodeMonster = getNodeByRowColumnIndex(row, col, gridPane);

        if (node != null && node instanceof ImageView) {
            ImageView existingImageView = (ImageView) node;
            ImageView monsterImageView = (ImageView) nodeMonster;

            String imagePathMonster = determineImagePath(CellInfo.EMPTY);
            Image imageMonster = new Image(getClass().getResourceAsStream(imagePathMonster));
            monsterImageView.setImage(imageMonster);
            existingImageView.toBack();
            monsterImageView.toBack();

            String imagePathClicked = determineImagePath(CellInfo.MONSTER);
            Image imageClicked = new Image(getClass().getResourceAsStream(imagePathClicked));
            existingImageView.setImage(imageClicked);

            existingImageView.setStyle("-fx-border-color: black; -fx-border-width: 1;");
            monsterImageView.setStyle("-fx-border-color: black; -fx-border-width: 1;");
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

    public void styleGridPane() {
        gridPane.setStyle("-fx-background-color: #ececec;");
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
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