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
import javafx.stage.Screen;
import javafx.stage.Stage;
 
public class TestJavafx extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
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