package View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



import Controller.ControlMonster;
import Model.Hunter;
import Model.Monster;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class VueMonster implements Observer {
    private Monster monster;
    private Hunter hunter;
    private ControlMonster controlleur;
    private GridPane gridPane; 

    

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Monster getMonster(){
        return monster;
    }

    public Hunter getHunter() {
        return hunter;
    }


    public GridPane getGridPane() {
        return gridPane;
    }


    public VueMonster(Monster monster, Hunter hunter) {
        this.monster = monster;
        this.hunter = hunter;
        this.controlleur = new ControlMonster(this);
    }

    public Stage creerStage() {
        Stage stage = new Stage();
        gridPane = new GridPane();
        chargePlateau();
        HBox hbox = new HBox(new Label("MONSTER"));
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        controlleur.mMouvement();
        VBox vbox = new VBox(hbox, gridPane);
        styleGridPane();
        Scene scene = new Scene(vbox, 900, 900);
        stage.setScene(scene);
        return stage;
    }

    public void chargePlateau() {
        gridPane.getChildren().clear();
        for (int i = 0; i < monster.getMap().getMaps().length; i++) {
            for (int j = 0; j < monster.getMap().getMaps()[i].length; j++) {
                Label label;
                label = new Label(monster.getMap().getMaps()[i][j].toString());
                styleLabel(label);
                if (i == hunter.getHunted().getRow() && j == hunter.getHunted().getCol()) {
                    styleHuntedLabel(label);
                }
                if(monster.path[i][j]!=-1){
                    label.setText(""+monster.path[i][j]);
                }
                gridPane.add(label, j, i);
            }
        }
    }

    public void styleLabel(Label label) {
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        label.setPrefWidth(200);
        label.setPrefHeight(100);
        label.setPadding(new Insets(10, 30, 10, 30));
        label.setBorder(new Border(borderStroke));
    }

    public void styleGridPane() {
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        gridPane.setBorder(new Border(borderStroke));
        gridPane.setPadding(new Insets(100, 100, 100, 100));
    }

    public void styleHuntedLabel(Label label) {
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);
        Background background = new Background(backgroundFill);
        label.setBackground(background);
    }

    @Override
    public void update(Subject subj) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void update(Subject subj, Object data) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
