package View;

import java.io.IOException;

import Model.Hunter;
import Model.Monster;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameView {

    @FXML
    GridPane grid;

    private VueHunter vueHunter;
    private VueMonster vueMonster;
    private Stage stage;

    private Scene player1Scene;
    private Scene player2Scene;
    private Scene transitionScene;

    private Stage gameStage;

    public GameView(Hunter hunter, Monster monster) {
        // initStage();
        this.vueHunter = new VueHunter(hunter, false);
        this.vueMonster = new VueMonster(monster, hunter, false);
        this.player1Scene = this.vueHunter.getScene();

        HBox main = new HBox(vueHunter.getMainBox());

        // main.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(main);
        Scene sceneWithVBox = new Scene(stackPane);

        this.player1Scene = sceneWithVBox;

        this.player2Scene = this.vueMonster.getScene();
        this.stage = vueMonster.getStage();
        this.transitionScene = new Scene(new Label("Changement de joueur"), player1Scene.getWidth(),
                player1Scene.getHeight());
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void show() {
        refresh();
        stage.setScene(player2Scene);
        stage.show();
    }

    public void changeStage() {
        int playerTurn = vueHunter.getHunter().getGameModel().currentPlayer;
        if (playerTurn == 1) {
            this.stage.setScene(player2Scene);
        } else if (playerTurn == 2) {
            this.stage.setScene(player1Scene);
        } else {
            this.stage.setScene(player2Scene);
        }
    }

    public void refresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            changeStage();
        }));
        // Configure la répétition indéfinie de la timeline, ce qui signifie que le
        // rafraîchissement continuera indéfiniment.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void close() {
        stage.close();
    }

    public VueHunter getVueHunter() {
        return vueHunter;
    }

    public VueMonster getVueMonster() {
        return vueMonster;
    }

    public Stage getStage() {
        return stage;
    }

    public void setFullScreen(boolean b) {
    }

}