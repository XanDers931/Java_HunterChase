package Main;

import Model.GameModel;
import Model.Hunter;
import Model.Monster;
import View.GameView;
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
        //primaryStage.show();
        GameModel gameModel = new GameModel(null, null);
        // Ensuite, utilisez ce GameModel pour créer un Monster
        Monster monster = new Monster(STYLESHEET_CASPIAN, gameModel);

        // Puis, utilisez le GameModel et le Monster pour créer un Hunter
        Hunter hunter = new Hunter(STYLESHEET_CASPIAN, gameModel);

    // Enfin, utilisez le Monster et le Hunter pour mettre à jour le GameModel si nécessaire
        gameModel.setMonster(monster);
        gameModel.setHunter(hunter);
        
        GameView gameView = new GameView(hunter, monster);
        gameView.show();
    }
}