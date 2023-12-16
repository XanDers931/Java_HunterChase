package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import Model.Monster;
import Utils.Coordinate;
import View.VueMonster;



public class ControlMonsterPlayer implements ControlMonster {
    private VueMonster view;
    private Coordinate clickedCase;


    public ControlMonsterPlayer(VueMonster view) {
        this.view = view;
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
        refresh();
        view.getGridPane().setOnMouseClicked(this::handleMouseClickMonster);
    }

    private void handleMouseClickMonster(MouseEvent event) {
        Node source = event.getPickResult().getIntersectedNode();
        StackPane clickedStackPane = findClickedStackPane(source);

        if (clickedStackPane != null) {
            int clickedRow = GridPane.getRowIndex(clickedStackPane);
            int clickedCol = GridPane.getColumnIndex(clickedStackPane);

            this.clickedCase = new Coordinate(clickedRow, clickedCol);
            Monster monster = view.getMonster();

            if (monster.getGameModel().currentPlayer == 1) {
                handleMonsterPlayerTurn(monster, clickedRow, clickedCol);
            }
        }
    }

    private void handleMonsterPlayerTurn(Monster monster, int clickedRow, int clickedCol) {
        Model.GameModel gameModel = monster.getGameModel();

        if (monster.victory(clickedRow, clickedCol) && monster.moveMonster(clickedRow, clickedCol)) {
            gameModel.currentPlayer = 3;
            view.showVictoryMessage();
        } else {
            handleMonsterMove(monster, clickedRow, clickedCol);
        }
    }

    private void handleMonsterMove(Monster monster, int clickedRow, int clickedCol) {
        if (monster.moveMonster(clickedRow, clickedCol)) {
            view.updatePlateau(clickedRow, clickedCol);
            monster.addCurrentCordMonster(clickedRow, clickedCol);
            monster.getGameModel().changeCurrentPlayer();
        }
    }

    private StackPane findClickedStackPane(Node source) {
        if (source instanceof ImageView) {
            return (StackPane) source.getParent();
        } else if (source instanceof StackPane) {
            return (StackPane) source;
        }
        return null;
    }
        
    public void refresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Coordinate cord = view.getMonster().getGameModel().getHunter().getHunted();
            StackPane stackPane = getStackPaneByRowColumnIndex(cord.getRow(), cord.getCol(), view.getGridPane());
            if (stackPane != null) {
                ImageView imageView = (ImageView) stackPane.getChildren().get(0);
                ColorAdjust color = new ColorAdjust();
                color.setHue(0.5);
                imageView.setEffect(color);
            }
            if (view.getMonster().getGameModel().currentPlayer==1) {
                view.getCurrentLabel().setText("C'est au tour du Monstre");
            }else{
                view.getCurrentLabel().setText("C'est au tour du Chasseur");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    
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

    public Coordinate getClickedCase() {
        return clickedCase;
    }

    

    
}
