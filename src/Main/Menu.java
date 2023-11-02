package Main;

import Model.Hunter;
import Model.Monster;
import View.VueHunter;
import View.VueMonster;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Menu {
    public void rules(){
        Stage stage = new Stage();
        Label rules = new Label("A hunter and a monster play against each other on a variable-sized board. Each turn, the monster moves first to one of the 8 neighbouring squares, provided it is empty. Thereafter, the hunter shoots on a square of his choice. He will then see if the square has already been visited. The hunter's aim is to hit the monster. The monster's aim is to reach the exit square without getting hit.");
        stage.setScene(rules.getScene());
    }

    public void playMenu(){
        Stage stage = creerStage();
        stage.show();
    }

    public Stage creerStage() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        Label labelPlay = new Label("Play");
        Label labelRules = new Label("Rules");
        vbox.getChildren().addAll(labelPlay, labelRules);
        stage.setScene(new Scene(vbox));
        labelPlay.setOnMouseClicked(event -> {
            start();
        });
        labelRules.setOnMouseEntered(event -> {
            rules();
        });
        return stage;
    }

    void start(){
        Hunter hunter= new Hunter("julien");
        Monster monster = new Monster("lala");
        VueHunter test = new VueHunter(hunter,monster);
        VueMonster test2 = new VueMonster(monster,hunter);
        Stage stage1 = test.creerStage();
        Stage stage2= test2.creerStage();
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        
        // Divisez l'écran en deux en ajustant les dimensions et les positions des stages
        stage1.setX(0);
        stage1.setY(0);
        stage1.setWidth(screenWidth / 2);
        stage1.setHeight(screenHeight);

        stage2.setX(screenWidth / 2);
        stage2.setY(0);
        stage2.setWidth(screenWidth / 2);
        stage2.setHeight(screenHeight);
        // Déplacez le deuxième stage à droite du premier
        stage2.setX(stage1.getX() + stage1.getWidth());
        stage2.setY(stage1.getY());
        stage1.show();
        stage2.show();
    }
}
