package Main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Menus menu = new Menus();
        menu.setPrimaryStage(primaryStage);
        menu.getPrimaryStage().setTitle("Monster Hunter");
        menu.createMainMenu();
        menu.createRulesPage();
        primaryStage.setScene(menu.getMainMenuScene());
        primaryStage.show();
    }
}