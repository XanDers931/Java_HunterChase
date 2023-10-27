import Model.Hunter;
import Model.Monster;
import View.VueHunter;
import View.VueMonster;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class TestMonster extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
       VueMonster test = new VueMonster(new Monster("test"));
       test.creerStage().show();
    }
}