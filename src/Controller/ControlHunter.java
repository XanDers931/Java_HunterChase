package Controller;

import Model.GameModel;
import View.VueHunter;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ControlHunter {
    public VueHunter view;

    public ControlHunter(VueHunter view) {
        this.view = view;
    }
    /**
     * Gère le mouvement du chasseur dans le jeu en réponse aux clics de souris sur la grille.
     * Lorsque le joueur clique sur le Label de la grille, cette méthode est appelée
     * pour gérer le déplacement du chasseur.
     *
     * @implNote Cette méthode récupère la grille à partir de la vue et configure un gestionnaire
     *           d'événements pour les clics de souris sur la grille. Si le joueur clique sur une étiquette,
     *           le chasseur peut se déplacer en fonction de certaines conditions (notamment, l'état "canMoove").
     *           Si le chasseur réussit à se déplacer vers une nouvelle position, la carte du chasseur est mise
     *           à jour pour refléter le tir et la victoire. De plus, les états "canMoove" du chasseur et du
     *           monstre sont modifiés en conséquence.
     *
     * @implNote Cette méthode est liée à un jeu ou à une simulation où le chasseur peut tirer sur la grille
     *           pour éliminer le monstre. La victoire est déterminée en fonction de la position du chasseur.
     */
    public void hMouvement() {
        GridPane gp = view.getGridPane();

        gp.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();

            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                if (view.getHunter().getGameModel().currentPlayer==2) {
                    view.getHunter().shoot(clickedRow, clickedCol);
                    if (view.getHunter().victory(clickedRow, clickedCol)) {
                        view.getHunter().getGameModel().currentPlayer=3;
                        view.showVictoryMessage();
                    }else {
                        view.getHunter().getGameModel().changeCurrentPlayer();
                    }
                    updateHunterPosition(clickedRow, clickedCol);
                }
            }
        });
    }


    private void updateHunterPosition(int row, int col) {
        view.getHunter().getHunted().setCol(col);
        view.getHunter().getHunted().setRow(row);
    }
}




