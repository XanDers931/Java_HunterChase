package View;

import java.util.Map;

import Model.Hunter;
import Model.Monster;
import Utils.Observer;
import Utils.Subject;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VueHunter implements Observer{

    private Hunter hunter;

    public void eventHunter(Hunter hunter){
        //A REMPLIR
    }

    public void creerStage(){
        Stage stage = new Stage();
        Map map = hunter.getMap();
        GridPane gridPane = new GridPane();
        for(int i = 0; i < map.map.size(); i++){
            for(int j = 0; j < map.size(); j++){
                gridPane.addRow(i, null);
            }
            
        }
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
