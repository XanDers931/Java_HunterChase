package View;

import Controller.ControlHunter;
import Main.Maps;
import Model.Hunter;
import Model.Monster;
import Utils.Coordinate;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VueHunter implements Observer {
    private Hunter hunter;
    private GridPane gridPane;
    private ControlHunter controlleur;
    private Stage stage;

    public VueHunter(Hunter hunter) {
        this.hunter = hunter;
        this.controlleur = new ControlHunter(this);
        this.stage = creerStage();
        hunter.attach(this);
    }

    public GridPane getGridPane() {
        return gridPane;
    }


    public Hunter getHunter() {
        return hunter;
    }

   

    public Stage creerStage() {
        Stage stage = new Stage();
        gridPane = new GridPane();
        HBox hbox = new HBox(new Label("HUNTER"));
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        chargePlateau(-1, -1);
        controlleur.hMouvement();
        styleGridPane(gridPane);
        VBox vbox = new VBox(hbox, gridPane);
        Scene scene = new Scene(vbox, 900, 900);
        stage.setScene(scene);
        return stage;
    }

    public void chargePlateau(int row, int col) {
        gridPane.getChildren().clear(); // Supprime tous les enfants actuels du GridPane 
        Maps map = hunter.getGameModel().getMap();
        boolean[][] mapShoot = map.getMapShoot();
        CellInfo[][] maps = map.getMaps();
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++){
                Label label = new Label();
                if (mapShoot[i][j]) {
                    label.setText(maps[i][j].toString());
                    if(hunter.getGameModel().getMonster().path[i][j]!=-1){
                        label.setText(""+hunter.getGameModel().getMonster().path[i][j]);
                    }
                }
                if(i == row && j == col) {
                    styleHuntedLabel(label);
                }
                gridPane.add(label, j, i);
                styleLabel(label);
            }
        }
    }

    public void styleHuntedLabel(Label label) {
        BackgroundFill backgroundFill = new BackgroundFill(Color.RED, new CornerRadii(5), null);
        Background background = new Background(backgroundFill);
        label.setBackground(background);
    }

    public void styleLabel(Label label) {
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        label.setPrefWidth(200);
        label.setPrefHeight(100);
        label.setPadding(new Insets(10, 30, 10, 30));
        label.setBorder(new Border(borderStroke));
    }

    public void styleGridPane(GridPane pane) {
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        pane.setBorder(new Border(borderStroke));
        pane.setPadding(new Insets(100, 100, 100, 100));
    }


    public Stage getStage(){
        return this.stage;
    }

    public void showVictoryMessage() {
    // Créez une nouvelle fenêtre de dialogue pour le message de victoire
    Stage victoryStage = new Stage();
    victoryStage.setTitle("Victory!");

    // Créez un label pour afficher le message de victoire
    Label victoryLabel = new Label("Congratulations! Hunter won!");

    // Créez un bouton pour fermer la fenêtre de dialogue
    Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> victoryStage.close());

    // Créez un conteneur pour le label et le bouton
    VBox vbox = new VBox(victoryLabel, closeButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(20);

    // Créez une nouvelle scène pour la fenêtre de dialogue
    Scene victoryScene = new Scene(vbox, 300, 200);

    // Définissez la scène pour la fenêtre de dialogue
    victoryStage.setScene(victoryScene);

    // Affichez la fenêtre de dialogue
    victoryStage.show();
}

    @Override
    public void update(Subject subj) {
        update(subj,null);
    }

    @Override
    public void update(Subject subj, Object data) {
        if (data instanceof Coordinate) {
            Coordinate coordonnees = (Coordinate) data;
            int row = coordonnees.getRow();
            int col = coordonnees.getCol();
            // Réagissez aux mises à jour du sujet en fonction des coordonnées passées en paramètres
            chargePlateau(row, col);
        }
    }
}
