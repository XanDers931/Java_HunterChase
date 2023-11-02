package Main;

import Model.Hunter;
import Model.Monster;
import View.VueHunter;
import View.VueMonster;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Menus {
    private Stage primaryStage;
    private Scene mainMenuScene;
    private Scene rulesScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene getMainMenuScene() {
        return mainMenuScene;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void createMainMenu() {
        Label titleLabel = new Label("Monster Hunter");
        titleLabel.setStyle("-fx-font-size: 20px;");

        Button playButton = new Button("Play");
        Button rulesButton = new Button("Rules");

        playButton.setOnAction(event -> {
            play();
        });

        rulesButton.setOnAction(event -> {
            primaryStage.setScene(rulesScene);
        });

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, playButton, rulesButton);

        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        mainMenuScene = new Scene(root, 400, 300);
    }

    public void createRulesPage() {
        Label rulesLabel = new Label("A hunter and a monster play against each other on a variable-sized board. Each turn, the monster moves first to one of the 8 neighboring squares, provided it is empty. Thereafter, the hunter shoots on a square of his choice. He will then see if the square has already been visited. The hunter's aim is to hit the monster. The monster's aim is to reach the exit square without getting hit.");
        rulesLabel.setWrapText(true);

        Button backButton = new Button("Retour");
        backButton.setOnAction(event -> {
            primaryStage.setScene(mainMenuScene);
        });

        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");
        vbox.getChildren().addAll(rulesLabel, backButton);
        rulesScene = new Scene(vbox, 600, 400);
    }

    public void play() {
        primaryStage.close();
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

    public void close() {
        primaryStage.close();
    }
}
