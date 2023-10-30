package Controller;

import java.util.HashMap;

import Model.Hunter;
import Model.Monster;
import View.VueMonster;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ControlMonster {

    public VueMonster view;
    private CellInfo passage;

    

    public CellInfo getPassage() {
        return passage;
    }


    public void setPassage(CellInfo passage) {
        this.passage = passage;
    }
    

    public ControlMonster(VueMonster view) {
        this.view = view;
    }



    public void mMouvement(GridPane gridPane){
        Monster monster= view.getMonster();
        Hunter hunter= view.getHunter();
        refresh(gridPane);
            gridPane.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                int[] cordMonster= monster.getCordUser(CellInfo.MONSTER);
                if(view.getMonster().canMoove){

                // TODO
                hunter.getMap().setCellInfo(cordMonster[0], cordMonster[1], CellInfo.EMPTY);   
                hunter.getMap().setCellInfo(clickedRow, clickedCol, CellInfo.MONSTER);

                monster.moveMonster(clickedRow, clickedCol);
                view.chargePlateau(gridPane); 
                System.out.println("Case cliquée : Ligne " + clickedRow + ", Colonne " + clickedCol);
                view.getMonster().canMoove=!view.getMonster().canMoove;
                view.getHunter().canMoove=!view.getHunter().canMoove;
                }
            }
            });
        
    }


    public void refresh(GridPane gridPane){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            view.chargePlateau(gridPane);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Pour que la Timeline continue à s'exécuter indéfiniment
        timeline.play();
    }
}