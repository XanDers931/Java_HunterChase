package Controller;

import Model.Monster;
import Model.MonsterStrategy;
import Utils.Coordinate;
import View.VueMonster;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * La classe ControlMonsterBot gère le mouvement du monstre dans le jeu en
 * utilisant une stratégie définie.
 * Cette classe est responsable de l'automatisation du mouvement du monstre.
 *
 * @implNote Cette classe utilise une stratégie définie dans MonsterStrategy
 *           pour déterminer le prochain mouvement du monstre.
 *           Elle met à jour la position du monstre et gère les conditions de
 *           victoire.
 *           De plus, elle applique des effets visuels à la grille pour indiquer
 *           la position actuelle du monstre.
 */
public class ControlMonsterBot implements ControlMonster {

    /**
     * Vue du monstre associée à cette instance de ControlMonsterBot.
     */
    private VueMonster view;

    /**
     * Coordonnée de la case cliquée par le monstre.
     */
    private Coordinate clickedCase;

    /**
     * Constructeur de la classe ControlMonsterBot.
     *
     * @param view La VueMonster associée à cette instance.
     */
    public ControlMonsterBot(VueMonster view) {
        this.view = view;
    }

    /**
     * Obtient la VueMonster associée à cette instance.
     *
     * @return La VueMonster associée à cette instance.
     */
    public VueMonster getView() {
        return view;
    }

    /**
     * Obtient la coordonnée de la case cliquée par le monstre.
     *
     * @return La coordonnée de la case cliquée par le monstre.
     */
    public Coordinate getClickedCase() {
        return clickedCase;
    }

    /**
     * Gère le mouvement automatique du monstre en utilisant une stratégie définie.
     * Configure une timeline pour effectuer le mouvement du monstre à intervalles
     * réguliers.
     */
    @Override
    public void mMouvement() {
        MonsterStrategy strategy = new MonsterStrategy();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Monster monster = view.getMonster();

            if (monster.getGameModel().currentPlayer == 1) {
                ICoordinate coord = strategy.play();
                int clickedRow = coord.getRow();
                int clickedCol = coord.getCol();
                this.clickedCase = new Coordinate(clickedRow, clickedCol);

                if (monster.victory(clickedRow, clickedCol) && monster.moveMonster(clickedRow, clickedCol)) {
                    monster.getGameModel().currentPlayer = 3;
                    view.showVictoryMessage();
                } else {
                    if (monster.moveMonster(clickedRow, clickedCol)) {
                        monster.addCurrentCordMonster(clickedRow, clickedCol);
                        monster.getGameModel().changeCurrentPlayer();
                    }
                }
            }

            Coordinate cord = view.getMonster().getHunted();
            StackPane stackPane = getStackPaneByRowColumnIndex(cord.getRow(), cord.getCol(), view.getGridPane());
            if (stackPane != null) {
                ImageView imageView = (ImageView) stackPane.getChildren().get(0);
                ColorAdjust color = new ColorAdjust();
                color.setHue(0.5);
                imageView.setEffect(color);
            }

            if (view.getMonster().getGameModel().currentPlayer == 1) {
                view.getCurrentLabel().setText("C'est au tour du Monstre");
            } else {
                view.getCurrentLabel().setText("C'est au tour du Chasseur");
            }
        }));
        // Configure la répétition indéfinie de la timeline, ce qui signifie que le
        // rafraîchissement continuera indéfiniment.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Obtient la StackPane associée à une position donnée dans une grille.
     *
     * @param row      L'indice de ligne.
     * @param column   L'indice de colonne.
     * @param gridPane La grille dans laquelle rechercher.
     * @return La StackPane associée à la position spécifiée, ou null si non
     *         trouvée.
     */
    public StackPane getStackPaneByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                if (node instanceof StackPane) {
                    return (StackPane) node;
                }
            }
        }
        return null;
    }
}
