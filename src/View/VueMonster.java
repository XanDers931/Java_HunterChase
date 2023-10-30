package View;

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
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VueMonster implements Observer{

    private Monster monster;
    private GridPane gridPane;
    private Hunter hunter;
    private ControlMonster controlleur;
    private Label label;
   



    public VueMonster(Monster monster, Hunter hunter) {
        this.monster = monster;
        this.hunter = hunter;
        this.controlleur = new ControlMonster(this);
    }



    public Monster getMonster() {
        return monster;
    }



    public void setMonster(Monster monster) {
        this.monster = monster;
    }



    public GridPane getGridPane() {
        return gridPane;
    }



    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }



    public Hunter getHunter() {
        return hunter;
    }



    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }




    public VueMonster(Monster monster) {
        this.monster = monster;
    }

    public Stage creerStage(){
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        Stage stage = new Stage();
        gridPane = new GridPane();
        chargePlateau(gridPane);
        HBox hbox = new HBox(new Label("MONSTER"));
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        controlleur.mMouvement(gridPane);
        VBox vbox = new VBox(hbox,gridPane);
        gridPane.setBorder(new Border(borderStroke));
        gridPane.setPadding(new Insets(100, 100, 100, 100)); 
        Scene scene = new Scene(vbox, 900, 900);
        stage.setScene(scene);
        return stage;
    }


    public void chargePlateau(GridPane gp){
        gp.getChildren().clear(); // Supprime tous les enfants actuels du GridPane
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        for(int i = 0; i < monster.getMap().getMaps().length; i++){
            for(int j = 0; j < monster.getMap().getMaps()[i].length; j++){
                label = new Label(monster.getMap().getMaps()[i][j].toString());
                label.setPrefWidth(200); 
                label.setPrefHeight(100);
                label.setPadding(new Insets(10,30,10,30));
                label.setBorder(new Border(borderStroke));
                if(i==hunter.getHunted()[0] && j==hunter.getHunted()[1]){
                    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);
                    Background background = new Background(backgroundFill);
                    label.setBackground(background);
                }
                gp.add(label, j , i );
            }
        }
        gridPane = gp;
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
