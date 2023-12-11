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



public class ControlMonsterBot implements ControlMonster{

    private View.VueMonster view;
    private int tourCpt;
    private Coordinate clickedCase;

    
    
    public ControlMonsterBot(VueMonster view) {
        this.view = view;
    }



    public View.VueMonster getView() {
        return view;
    }



    public int getTourCpt() {
        return tourCpt;
    }



    public Coordinate getClickedCase() {
        return clickedCase;
    }





    @Override
    public void mMouvement() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                MonsterStrategy strategy = new MonsterStrategy();
                ICoordinate coord = strategy.play();
                int clickedRow = coord.getRow();
                int clickedCol = coord.getCol();
                this.clickedCase = new Coordinate(clickedRow, clickedCol);
                Monster monster = view.getMonster();
              
                
                if (monster.getGameModel().currentPlayer == 1) {
                    if (monster.victory(clickedRow, clickedCol) && monster.moveMonster(clickedRow, clickedCol)) {
                        monster.getGameModel().currentPlayer = 3;
                        view.showVictoryMessage();
                    } else {
                        if (monster.moveMonster(clickedRow, clickedCol)) {
                            view.updatePlateau(clickedRow,clickedCol);
                            monster.addCurrentCordMonster(clickedRow, clickedCol);
                            monster.getGameModel().changeCurrentPlayer();
                        }
                    }
                }

            Coordinate cord = view.getMonster().getGameModel().getHunter().getHunted();
            StackPane stackPane = getStackPaneByRowColumnIndex(cord.getRow(), cord.getCol(), view.getGridPane());
            if (stackPane != null) {
                ImageView imageView = (ImageView) stackPane.getChildren().get(0);
                ColorAdjust color = new ColorAdjust();
                color.setHue(0.5);
                imageView.setEffect(color);
            }
        }));
        // Configure la répétition indéfinie de la timeline, ce qui signifie que le rafraîchissement continuera indéfiniment.
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
    
}
