package Controller;

import java.util.HashMap;

import View.VueHunter;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ControlHunter {
    public VueHunter view;

    public ControlHunter(VueHunter view) {
        this.view = view;
    }

    public void hMouvement(){
        GridPane gp= view.getGridPane();
            gp.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                if(view.getHunter().canMoove){
                    view.getHunter().getMap().getMapShoot()[clickedRow][clickedCol] = true;
                    if(view.getHunter().victory(clickedRow,clickedCol)){
                        System.out.println("VICTOIRE DU HUNTER");
                        view.getMonster().canMoove = false;
                        view.getHunter().canMoove = false;
                    }
                    else{
                        view.getHunter().changeCanMoove();
                        view.getMonster().changeCanMoove();
                    }
                    view.getHunter().getHunted().setCol(clickedCol);
                    view.getHunter().getHunted().setRow(clickedRow);
                    view.chargePlateau(clickedRow,clickedCol);
                }
            }
        });
    }
}
