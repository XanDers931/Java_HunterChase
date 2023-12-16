package Menu; // Remplacez par le vrai nom de votre package

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu{
   
  
    public Stage createStage() {
        try {
            Stage stage= new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = loader.load();

            // Créer la scène
            Scene scene = new Scene(root);

            // Configurer la scène et afficher la fenêtre
            stage.setTitle("Monster Hunter");
            stage.setScene(scene);
            return stage;
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception d'entrée/sortie ici
            return null;
        }
    }
}
