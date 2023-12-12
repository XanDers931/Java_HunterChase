package View;


import Model.Hunter;
import Model.Monster;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GameView {
    private VueHunter vueHunter;
    private VueMonster vueMonster;
    private Stage stage;

    public GameView(Hunter hunter, Monster monster) {
        this.vueHunter = new VueHunter(hunter,false);
        this.vueMonster = new VueMonster(monster, hunter,false);
        this.stage = createStage();
    }

    private Stage createStage() {
        Stage stage = new Stage();
        HBox hBox = new HBox(vueHunter.getGridPane(), vueMonster.getGridPane());
        Scene scene = new Scene(hBox, 1800, 900);
        stage.setScene(scene);
        return stage;
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public VueHunter getVueHunter() {
        return vueHunter;
    }

    public VueMonster getVueMonster() {
        return vueMonster;
    }

    public Stage getStage() {
        return stage;
    }

    
}
