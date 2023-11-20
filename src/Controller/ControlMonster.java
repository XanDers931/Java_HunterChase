package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import Model.Monster;
import Utils.Coordinate;
import View.VueMonster;


public class ControlMonster {
    private VueMonster view;
    private int tourCpt;
    private Coordinate clickedCase;


    public ControlMonster(VueMonster view) {
        this.view = view;
        this.tourCpt = 0;
        this.clickedCase= new Coordinate(0, 0);
    }

    /**
     * Gère le mouvement du monstre dans le jeu. Le monstre peut se déplacer en cliquant
     * sur une case de la grille. La méthode récupère les objets Monster et Hunter à partir
     * de la vue et actualise l'affichage du jeu en appelant la méthode refresh(). Ensuite,
     * elle configure un gestionnaire d'événements pour les clics de souris sur la grille, permettant
     * au monstre de se déplacer vers la case cliquée.
     *
     * @implNote Le monstre ne peut se déplacer que si son état "canMoove" le permet.
     * @implNote Lorsque le monstre se déplace, la case d'origine devient vide et la case de destination
     *           est occupée par le monstre. Le monstre met à jour sa position interne et le plateau de jeu
     *           est rechargé pour refléter les changements.
     * @implNote Le monstre change son état "canMoove" pour indiquer qu'il a effectué un mouvement, et le
     *           chasseur fait de même. De plus, le monstre enregistre le chemin suivi dans le tableau "path".
     *           Chaque case visitée est enregistrée avec le numéro du tour (tourCpt).
     */
    public void mMouvement() {
        GridPane gridPane = view.getGridPane();
        gridPane.setOnMouseClicked(event -> {
            Node source = event.getPickResult().getIntersectedNode();
            StackPane clickedStackPane = null;
    
            // Trouver le StackPane cliqué
            if (source instanceof ImageView) {
                // Si l'événement est sur l'ImageView, remonte au parent (StackPane)
                clickedStackPane = (StackPane) source.getParent();
            } else if (source instanceof StackPane) {
                // Si l'événement est déjà sur le StackPane, utilisez-le directement
                clickedStackPane = (StackPane) source;
            }
    
            if (clickedStackPane != null) {
                int clickedRow = GridPane.getRowIndex(clickedStackPane);
                int clickedCol = GridPane.getColumnIndex(clickedStackPane);
    
                this.clickedCase = new Coordinate(clickedRow, clickedCol);
                Monster monster = view.getMonster();
    
                if (monster.getGameModel().currentPlayer == 1) {
                    if (monster.victory(clickedRow, clickedCol)) {
                        monster.getGameModel().currentPlayer = 3;
                        view.showVictoryMessage();
                    } else {
                        if (monster.moveMonster(clickedRow, clickedCol)) {
                            view.updatePlateau();
                            monster.addCurrentCordMonster(clickedRow, clickedCol);
                            monster.getGameModel().changeCurrentPlayer();
                        }
                    }
                }
            }
        });
    }
    

    /*public void refresh(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Coordinate cord = view.getMonster().getGameModel().getHunter().getHunted();
            Node node = view.getNodeByRowColumnIndex(cord.getRow(), cord.getCol(), view.getGridPane());
            ImageView image = (ImageView) node;
            ColorAdjust color = new ColorAdjust();
            color.setHue(0.1);
            image.setEffect(color);
            // À chaque rafraîchissement (toutes les 1 seconde), on appele la méthode "chargePlateau" de la vue .
        }));
        // Configure la répétition indéfinie de la timeline, ce qui signifie que le rafraîchissement continuera indéfiniment.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }*/

    public int getTour(){
        return this.tourCpt;
    }

    public Coordinate getClickedCase() {
        return clickedCase;
    }

    
}
