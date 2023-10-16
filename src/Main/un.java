package src.Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class un extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Application JavaFX avec Boutons");

        // Créez des boutons
        Button bouton1 = new Button("Bouton 1");
        Button bouton2 = new Button("Bouton 2");
        Button bouton3 = new Button("Bouton 3");

        // Créez un gestionnaire d'événements pour les boutons
        bouton1.setOnAction(e -> System.out.println("Bouton 1 a été cliqué"));
        bouton2.setOnAction(e -> System.out.println("Bouton 2 a été cliqué"));
        bouton3.setOnAction(e -> System.out.println("Bouton 3 a été cliqué"));

        // Créez un conteneur pour les boutons
        VBox layout = new VBox(10); // espacement vertical entre les boutons
        layout.getChildren().addAll(bouton1, bouton2, bouton3);

        // Créez une scène
        Scene scene = new Scene(layout, 300, 200);

        // Définissez la scène pour la fenêtre principale
        primaryStage.setScene(scene);

        // Affichez la fenêtre
        primaryStage.show();
    }

}
