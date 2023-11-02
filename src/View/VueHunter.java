package View;

import Controller.ControlHunter;
import Controller.ControlMonster;
import Main.Maps;
import Model.Hunter;
import Model.Monster;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VueHunter implements Observer {
    private Monster monster;
    private Hunter hunter;
    private GridPane gridPane;
    private ControlHunter controlleur;
    private static final int GRID_SIZE = 900;

    public VueHunter(Hunter hunter, Monster monster) {
        this.hunter = hunter;
        this.monster = monster;
        this.controlleur = new ControlHunter(this);
    }

    public GridPane getGridPane() {
        return gridPane;
    }


    public Hunter getHunter() {
        return hunter;
    }

    public Monster getMonster() {
        return monster;
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
        Scene scene = new Scene(vbox, GRID_SIZE, GRID_SIZE);
        stage.setScene(scene);
        return stage;
    }

    public void chargePlateau(int row, int col) {
        gridPane.getChildren().clear(); // Supprime tous les enfants actuels du GridPane
        Maps map = hunter.getMap();
        boolean[][] mapShoot = map.getMapShoot();
        CellInfo[][] maps = map.getMaps();
        Label[][] labels= new Label[maps.length][maps[0].length];
        
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++) {
                Label label = new Label();
                if (mapShoot[i][j]) {
                    label.setText(maps[i][j].toString());
                    if(monster.path[i][j]!=-1){
                        label.setText(""+monster.path[i][j]);
                    }
                }
                if(i == row && j == col) {
                    styleHuntedLabel(label);
                }
                gridPane.add(label, j, i);
                labels[i][j]=label;
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

    @Override
    public void update(Subject subj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void update(Subject subj, Object data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
