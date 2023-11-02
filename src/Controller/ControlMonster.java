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


    public ControlMonster(VueMonster view) {
        this.view = view;
    }

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
                Coordinate cordMonster = hunter.getMap().getCordUser(CellInfo.MONSTER);

                if ((view.getMonster()).getCanMoove()) {
                    
                    hunter.getMap().setCellInfo(cordMonster.getRow(), cordMonster.getCol(), CellInfo.EMPTY);
                    hunter.getMap().setCellInfo(clickedRow, clickedCol, CellInfo.MONSTER);
                    monster.moveMonster(clickedRow, clickedCol);
                    view.chargePlateau();
                    view.getMonster().changeCanMoove();
                    view.getHunter().changeCanMoove();
                }
            }
        });
    }

    public void refresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            view.chargePlateau();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
