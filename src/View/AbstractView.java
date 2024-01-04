package View;

import java.util.ArrayList;
import java.util.List;

import Menu.Menu;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class AbstractView {

    protected String determineImagePath(CellInfo cellInfo) {
        switch (cellInfo) {
            case WALL:
                return "trou.jpg";
            case MONSTER:
                return "loup.jpg";
            case EXIT:
                return "sortie.png";
            default:
                return "green.jpg";
        }
    }

    protected StackPane createStackPaneWithBorder(CellInfo cellInfo) {
        StackPane stackPane = new StackPane();
        String imagePath = determineImagePath(cellInfo);
        Image image = null;
        try {
            image = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        stackPane.getChildren().add(imageView);

        return stackPane;
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public void styleGridPane(GridPane pane, double imageSize) {
        pane.setStyle("-fx-background-color: #ececec;");
        pane.setPadding(new Insets(1));
        pane.setHgap(1);
        pane.setVgap(1);

        for (Node node : pane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
            }
        }
    }

    protected void fermerTousLesStages() {
        List<Stage> stagesToClose = new ArrayList<>();

        // Collect open stages
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage && !((Stage) window).isIconified()) {
                stagesToClose.add((Stage) window);
            }
        }

        // Close the collected stages
        for (Stage stage : stagesToClose) {
            stage.close();
        }
    }

    /**
     * Affiche un message de victoire lorsque le monstre gagne.
     */
    public void showVictoryMessage(GridPane gridPane, String whoWon) {
        Stage victoryStage = new Stage();
        victoryStage.setTitle("Victory!");
        Label victoryLabel = new Label("Congratulations! " + whoWon + " won!");
        Button replay = new Button("Retour au menu");
        replay.setOnAction(e -> {
            Menu menu = new Menu();
            victoryStage.close();
            fermerTousLesStages();
            fermerStage(gridPane);
            menu.createStage().show();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            // Ferme toutes les fenêtres ouvertes
            victoryStage.close();
            fermerTousLesStages();
            fermerStage(gridPane);
        });
        VBox vbox = new VBox(victoryLabel, replay, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        Scene victoryScene = new Scene(vbox, 300, 200);
        victoryStage.setScene(victoryScene);
        victoryStage.show();
    }

    public void fermerStage(GridPane gridPane) {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

}
