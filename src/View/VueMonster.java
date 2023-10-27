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

public class VueMonster implements Observer{

    private Monster monster;
    private GridPane gridPane;


    public VueMonster(Monster monster) {
        this.monster = monster;
    }

    public void eventMonster(Monster monster){
        
    }


    public Stage creerStage(){
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        Stage stage = new Stage();
        Maps map = monster.getMap();
        gridPane = new GridPane();
        chargePlateau(gridPane);
        gridPane.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                monster.moveMonster(clickedRow, clickedCol);
                //monster.setMap(clickedRow, clickedCol);
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
        for(int i = 0; i < monster.getMap().getMaps().length; i++){
            for(int j = 0; j < monster.getMap().getMaps()[i].length; j++){
                Label test = new Label(monster.getMap().getMaps()[i][j].toString());
                test.setPrefWidth(200); 
                test.setPrefHeight(100);
                test.setPadding(new Insets(10,30,10,30));
                test.setBorder(new Border(borderStroke));
                if(monster.getMap().getMaps()[i][j].equals(CellInfo.HUNTER)){
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
