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
            // Configure un gestionnaire d'événements pour les clics de souris sur la grille.
            gp.setOnMouseClicked(event -> {
            // Récupère l'élément graphique sur lequel l'événement a été déclenché.
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                if(view.getHunter().canMoove){
                    // Met à jour la map pour indiquer que le chasseur a tiré à cet emplacement.
                    view.getHunter().getMap().getMapShoot()[clickedRow][clickedCol] = true;
                    if(view.getHunter().victory(clickedRow,clickedCol)){
                        System.out.println("VICTOIRE DU HUNTER");
                        // Désactive la possibilité de mouvement du monstre et du chasseur.
                        view.getMonster().canMoove = false;
                        view.getHunter().canMoove = false;
                    }
                    else{
                        // Si la partie n'est pas encore gagnée, change les états de "canMoove" du chasseur et du monstre.
                        view.getHunter().changeCanMoove();
                        view.getMonster().changeCanMoove();
                    }
                    // Met à jour la position du chasseur pour refléter le clic de l'utilisateur.
                    view.getHunter().getHunted().setCol(clickedCol);
                    view.getHunter().getHunted().setRow(clickedRow);
                    // Met à jour l'affichage de la grille en fonction de la nouvelle position du chasseur.
                    view.chargePlateau(clickedRow,clickedCol);
                }
            }
        });
    }
}
