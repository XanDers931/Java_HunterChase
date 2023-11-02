package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import Model.Hunter;
import Model.Monster;
import Utils.Coordinate;
import View.VueMonster;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class ControlMonster {
    private VueMonster view;
    private int tourCpt;


    public ControlMonster(VueMonster view) {
        this.view = view;
        this.tourCpt = 0;
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
        Monster monster = view.getMonster();
        Hunter hunter = view.getHunter();
        refresh();
        GridPane gridPane= view.getGridPane();

        gridPane.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                // Récupère les coordonnées du monstre.
                Coordinate cordMonster = hunter.getMap().getCordUser(CellInfo.MONSTER);

                if ((view.getMonster()).getCanMoove()) {
                    if(view.getMonster().victory(clickedRow, clickedCol)){
                            System.out.println("VICTOIRE DU MONSTER");
                            view.getMonster().canMoove = false;
                            view.getHunter().canMoove = false;
                    }
                    if (!monster.moveMonster(clickedRow, clickedCol)) {
                        System.out.println("Tu ne peux pas te déplacer sur cette case !");
                        mMouvement();
                    }
                    else{
                        view.getMonster().changeCanMoove();
                        view.getHunter().changeCanMoove();
                        monster.path[cordMonster.getRow()][cordMonster.getCol()] = tourCpt;
                        this.tourCpt++;
                        // Remplace la case d'origine du monstre par CellInfo.EMPTY et la case de destination par CellInfo.MONSTER.
                        hunter.getMap().setCellInfo(cordMonster.getRow(), cordMonster.getCol(), CellInfo.EMPTY);
                        hunter.getMap().setCellInfo(clickedRow, clickedCol, CellInfo.MONSTER);
                        view.chargePlateau();
                    }
                }
            }
        });
    }

    public void refresh() {
        // Crée une instance de la classe Timeline pour gérer les rafraîchissements.
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            view.chargePlateau();
            // À chaque rafraîchissement (toutes les 1 seconde), appelez la méthode "chargePlateau" de la vue .
        }));
        // Configure la répétition indéfinie de la timeline, ce qui signifie que le rafraîchissement continuera indéfiniment.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public int getTour(){
        return this.tourCpt;
    }
}
