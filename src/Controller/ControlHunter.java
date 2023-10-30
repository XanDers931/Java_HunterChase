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
    public int cpt;

 

    
    
    public ControlHunter(VueHunter view) {
        this.view = view;
        this.cpt=0;
    }

   



    public void hMouvement(GridPane gp){
            gp.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                if(view.getHunter().canMoove){
                view.getHunter().getMap().getMapShoot()[clickedRow][clickedCol] = true;
                if(view.getHunter().getMap().getMaps()[clickedRow][clickedCol].equals(CellInfo.MONSTER)){
                    //VICTOIRE DU HUNTER
                    System.out.println("VICTOIRE DU HUNTER"); 
                }
                int[] cord = new int[2];
                cord[0]= clickedRow;
                cord[1]= clickedCol;
                view.getHunter().setHunted(cord);
                
                view.chargePlateau(gp,clickedRow,clickedCol);
                System.out.println("Case cliquée : Ligne " + clickedRow + ", Colonne " + clickedCol);
                view.getHunter().canMoove=!view.getHunter().canMoove;
                view.getMonster().canMoove=!view.getMonster().canMoove;
            }
            }
        });
     
        
    }
}
