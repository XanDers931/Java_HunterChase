package Controller;

import Model.HunterStrategy;
import Utils.Coordinate;
import View.VueHunter;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class ControlHunterBot implements ControlHunter {

    public VueHunter view;
    public Coordinate clickedCase;

    public ControlHunterBot(VueHunter view) {
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
       Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            HunterStrategy strategy = new HunterStrategy();
            ICoordinate coordinate = strategy.play();
            int clickedRow = coordinate.getRow();
            int clickedCol = coordinate.getCol();
     
       
            this.clickedCase= new Coordinate(clickedRow, clickedCol);

            if (view.getHunter().getGameModel().currentPlayer==2) {
                view.getHunter().shoot(clickedRow, clickedCol);
                view.updatePlateau();
                if (view.getHunter().victory(clickedRow, clickedCol)) {
                    view.getHunter().getGameModel().currentPlayer=3;
                    view.showVictoryMessage();
                }else {
                    view.getHunter().getGameModel().changeCurrentPlayer();
                }
                updateHunterPosition(clickedRow, clickedCol);
            }
            if (view.getHunter().getGameModel().currentPlayer==1) {
                view.getCurrentLabel().setText("C'est au tour du Monstre");
            }else{
                view.getCurrentLabel().setText("C'est au tour du Chasseur");
            }
        }));
        // Configure la répétition indéfinie de la timeline, ce qui signifie que le rafraîchissement continuera indéfiniment.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateHunterPosition(int row, int col) {
        view.getHunter().getHunted().setCol(col);
        view.getHunter().getHunted().setRow(row);
    }
    
    public Coordinate getClickedCase() {
        return clickedCase;
    }

    
}

    

