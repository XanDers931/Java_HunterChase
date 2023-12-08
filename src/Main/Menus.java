package Main;

import javax.swing.text.html.HTMLDocument.BlockElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Model.GameModel;
import Model.Hunter;
import Model.Monster;
import View.VueHunter;
import View.VueMonster;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
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

        TextField pseudo = new TextField();
        TextField tabSize = new TextField();
        GridPane param = new GridPane();
        Button playButton = new Button("Play");
        Button rulesButton = new Button("Rules");

        param.setPadding(new Insets(10, 10, 10, 10));
        param.setVgap(5);
        param.setHgap(5);
        //ajout du textfield pour le pseudo
        param.add(new Label("Pseudo :"), 0, 0,2,1);
        param.add(pseudo, 0, 1,2,1);

        //Ajout du TextField qui ne prend que des int pour la taille de plateau
        param.add(new Label("Taille du plateau :"), 0, 2, 2, 1);
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        });
        // Lie le filtre au TextField
        tabSize.setTextFormatter(textFormatter);
        param.add(tabSize, 0, 3, 2, 1);

        // Ajout de la liste déroulante du choix du mode de jeu
        VBox choixBots = new VBox();
        final ObservableList<String> lst = FXCollections.observableArrayList("Joueur","Bot");


        ComboBox<String> choixMonstreComboBox = new ComboBox<>();
        Label choixMonstreLabel = new Label("Sélection  Monstre:");
        choixMonstreComboBox.setItems(lst);
        choixBots.getChildren().add(choixMonstreLabel);
        choixBots.getChildren().add(choixMonstreComboBox);

        ComboBox<String> choixChasseurComboBox = new ComboBox<>();
        choixChasseurComboBox.setItems(lst);
        Label choixChasseurLabel = new Label("Sélection chasseur:");
        choixBots.getChildren().add(choixChasseurLabel);
        choixBots.getChildren().add(choixChasseurComboBox);
        choixBots.setAlignment(Pos.CENTER);
        GridPane.setHalignment(choixBots, HPos.CENTER);

        String[] bots = new String[2];

        choixMonstreComboBox.valueProperty().addListener(observable -> bots[0] = choixMonstreComboBox.getValue());
        choixChasseurComboBox.valueProperty().addListener(observable -> bots[1] = choixChasseurComboBox.getValue());

        

        playButton.setOnAction(event -> {
            if(tabSize.getText().isEmpty()) play("test",10, bots);
            else play("test",Integer.parseInt(tabSize.getText()), bots);
        });

        
        rulesButton.setOnAction(event -> {
            primaryStage.setScene(rulesScene);
        });
        param.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, param, choixBots, playButton, rulesButton);

        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        mainMenuScene = new Scene(root, 400, 400);
    }

    public void createRulesPage() throws IOException {
        String rules = readRulesFromFile();
        Label rulesLabel = new Label(rules);
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

    private String readRulesFromFile() throws IOException {
        FileReader fileReader = new FileReader("../../res/rules.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        StringBuilder rulesBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            rulesBuilder.append(line).append("\n");
        }

        fileReader.close();
        return rulesBuilder.toString();

    }

    public void play(String nickname,int tailleTab, String[] bots) {
        primaryStage.close();

        //Création des objets
        GameModel gameModel = new GameModel(null, null, tailleTab);
        // Ensuite, utilisez ce GameModel pour créer un Monster
        Monster monster = new Monster("STYLESHEET_CASPIAN", gameModel);

        // Puis, utilisez le GameModel et le Monster pour créer un Hunter
        Hunter hunter = new Hunter("STYLESHEET_CASPIAN", gameModel);

        // Enfin, utilisez le Monster et le Hunter pour mettre à jour le GameModel si nécessaire
        gameModel.setMonster(monster);
        gameModel.setHunter(hunter);
        boolean controlMonster = bots[0].equals("Bot");
        boolean controlHunter = bots[1].equals("Bot");

        VueHunter hunterView = new VueHunter(hunter,controlHunter);
        VueMonster monsterView = new VueMonster(monster,hunter,controlMonster);

        //Création des deux stages joueurs
        Stage hunterStage= hunterView.creerStage();
        Stage monsterStage = monsterView.creerStage();


        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        
        // Divisez l'écran en deux en ajustant les dimensions et les positions des stages
            //Screen 1
        hunterStage.setX(0);
        hunterStage.setY(0);
        hunterStage.setWidth(screenWidth / 2);
        hunterStage.setHeight(screenHeight);
            //Screen 2
        monsterStage.setX(screenWidth / 2);
        monsterStage.setY(0);
        monsterStage.setWidth(screenWidth / 2);
        monsterStage.setHeight(screenHeight);
        // Déplacez le deuxième stage à droite du premier
        monsterStage.setX(hunterStage.getX() + hunterStage.getWidth());
        monsterStage.setY(hunterStage.getY());
        hunterStage.show();
        monsterStage.show();

    }
}