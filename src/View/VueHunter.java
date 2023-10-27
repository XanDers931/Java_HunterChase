package View;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VueHunter implements Observer{
    private Monster monster;
    private Hunter hunter;
    GridPane gridPane;

    public VueHunter(Hunter hunter, Monster monster) {
        this.hunter = hunter;
        this.monster = monster;
    }

    public void eventHunter(Hunter hunter){
        //A REMPLIR //
    }


    public Stage creerStage(){
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        Stage stage = new Stage();
        gridPane = new GridPane();
        chargePlateau(gridPane);
        gridPane.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                hunter.getMap().getMapShoot()[clickedRow][clickedCol] = true;
                if(monster.getMap().getMaps()[clickedRow][clickedCol].equals(CellInfo.MONSTER)){
                    //VICTOIRE DU HUNTER
                    System.out.println("VICTOIRE DU HUNTER");
                }
                hunter.setMap(clickedRow, clickedCol);
                chargePlateau(gridPane);
                System.out.println("Case cliquée : Ligne " + clickedRow + ", Colonne " + clickedCol);
            }
        });
        gridPane.setBorder(new Border(borderStroke));
        gridPane.setPadding(new Insets(100, 100, 100, 100)); 
        Scene scene = new Scene(gridPane, 1000, 1000);
        stage.setScene(scene);
        return stage;
    }

    public void chargePlateau(GridPane gp){
        gp.getChildren().clear(); // Supprime tous les enfants actuels du GridPane
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        Maps map = hunter.getMap();
        for(int i = 0; i < map.getMaps().length; i++){
            for(int j = 0; j < map.getMaps()[i].length; j++){
                Label test;
                if(map.getMapShoot()[i][j] == true){
                    test = new Label(map.getMaps()[i][j].toString());
                }
                else{
                    test = new Label();
                }
                test.setPrefWidth(200); 
                test.setPrefHeight(100);
                test.setPadding(new Insets(10,30,10,30));
                test.setBorder(new Border(borderStroke));
                if(hunter.getMap().getMaps()[i][j].equals(CellInfo.HUNTER)){
                    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);
                    Background background = new Background(backgroundFill);
                    test.setBackground(background);
                }
                gp.add(test, j , i );
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
