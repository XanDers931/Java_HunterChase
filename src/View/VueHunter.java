package View;

import Controller.ControlHunter;
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


public class VueHunter implements Observer {
    private Hunter hunter;
    private GridPane gridPane;
    private ControlHunter controlleur;
    private Stage stage;

    public VueHunter(Hunter hunter) {
        this.hunter = hunter;
        this.controlleur = new ControlHunter(this);
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

        int rowCount = 11 ;
        int colCount = 11 ;

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
                ImageView imageView = createImageViewWithBorder(map.getMaps()[i][j], imageSize, imageSize);
                imageView.setImage(image);

                if (mapShoot[i][j]) {
                    imageView = createImageViewWithBorder(map.getMaps()[i][j], imageSize, imageSize);
                    imageView.setStyle("-fx-border-color: red; -fx-border-width: 1;"); // Bordure rouge pour les tirs
                }

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

    private ImageView createImageViewWithBorder(CellInfo cellInfo, double fitWidth, double fitHeight) {
        ImageView imageView = new ImageView();
        String imagePath = determineImagePath(cellInfo);
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imageView.setImage(image);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        return imageView;
    }

    public void updatePlateau() {
        int clickedRow = controlleur.getClickedCase().getRow();
        int clickedCol = controlleur.getClickedCase().getCol();

        Node node = getNodeByRowColumnIndex(clickedRow, clickedCol, gridPane);

        if (node != null && node instanceof ImageView) {
            ImageView existingImageView = (ImageView) node;
            existingImageView.toBack();
            String imagePathClicked = determineImagePath(hunter.getGameModel().getMap().getMaps()[clickedRow][clickedCol]);
            Image imageClicked = new Image(getClass().getResourceAsStream(imagePathClicked));
            existingImageView.setImage(imageClicked);
            existingImageView.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
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

    public void showVictoryMessage() {
        Stage victoryStage = new Stage();
        victoryStage.setTitle("Victory!");

        Label victoryLabel = new Label("Congratulations! Hunter won!");

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
        if (data instanceof Coordinate) {
            Coordinate coordonnees = (Coordinate) data;
            int row = coordonnees.getRow();
            int col = coordonnees.getCol();
            chargePlateau(row, col, 50); 
        }
    }
}
