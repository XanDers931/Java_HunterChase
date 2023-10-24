package View;

import Main.Maps;
import Model.Hunter;
import Model.Monster;
import Utils.Observer;
import Utils.Subject;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VueHunter implements Observer{

    private Hunter hunter;

    public void eventHunter(Hunter hunter){
        //A REMPLIR //
    }

    public Stage creerStage(){
        Stage stage = new Stage();
        Maps map = hunter.getMap();
        GridPane gridPane = new GridPane();
        for(int i = 0; i < map.getMap().length; i++){
            for(int j = 0; j < map.getMap()[i].length; j++){
                gridPane.add(new Label(map.getMap()[i][j].toString()), j , i );
            }
            
        }
        Scene scene = new Scene(gridPane, 1000, 1000);
        stage.setScene(scene);
        return stage;
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
