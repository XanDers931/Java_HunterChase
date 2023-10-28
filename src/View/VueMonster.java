package View;

import Main.Maps;
import Model.Hunter;
import Model.Monster;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VueMonster implements Observer{

    private Monster monster;
    private GridPane gridPane;
    private Hunter hunter;
    boolean tour=true;
    


    public VueMonster(Monster monster) {
        this.monster = monster;
    }

    

    public VueMonster(Monster monster, Hunter hunter) {
        this.monster = monster;
        this.hunter = hunter;
    }



    public void eventMonster(Monster monster){
        
    }


    public Stage creerStage(){
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        Stage stage = new Stage();
        gridPane = new GridPane();
        chargePlateau(gridPane);
        HBox hbox = new HBox(new Label("MONSTER"));
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
            gridPane.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (source instanceof Label) {
                int clickedRow = GridPane.getRowIndex(source);
                int clickedCol = GridPane.getColumnIndex(source);
                int[] cordMonster= monster.getCordUser(CellInfo.MONSTER);
                hunter.getMap().setCellInfo(cordMonster[0], cordMonster[1], CellInfo.EMPTY);
                hunter.getMap().setCellInfo(clickedRow, clickedCol, CellInfo.MONSTER);
                monster.moveMonster(clickedRow, clickedCol);
                chargePlateau(gridPane); 
                System.out.println(tour);
                System.out.println("Case cliquée : Ligne " + clickedRow + ", Colonne " + clickedCol);
            }
        });

        VBox vbox = new VBox(hbox,gridPane);
        gridPane.setBorder(new Border(borderStroke));
        gridPane.setPadding(new Insets(100, 100, 100, 100)); 
        Scene scene = new Scene(vbox, 900, 900);
        stage.setScene(scene);
        return stage;
    }


    public void chargePlateau(GridPane gp){
        gp.getChildren().clear(); // Supprime tous les enfants actuels du GridPane
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        for(int i = 0; i < monster.getMap().getMaps().length; i++){
            for(int j = 0; j < monster.getMap().getMaps()[i].length; j++){
                Label test = new Label(monster.getMap().getMaps()[i][j].toString());
                test.setPrefWidth(200); 
                test.setPrefHeight(100);
                test.setPadding(new Insets(10,30,10,30));
                test.setBorder(new Border(borderStroke));
                if(monster.getMap().getMaps()[i][j].equals(CellInfo.HUNTER)){
                    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);
                    Background background = new Background(backgroundFill);
                    test.setBackground(background);
                }
                gp.add(test, j , i );
            }
        }
        gridPane = gp;
    }


    public GridPane createGrid(){
        GridPane grid = new GridPane();
         BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
         for(int i = 0; i < monster.getMap().getMaps().length; i++){
            for(int j = 0; j < monster.getMap().getMaps()[i].length; j++){
                Label test = new Label(monster.getMap().getMaps()[i][j].toString());
                test.setPrefWidth(200); 
                test.setPrefHeight(100);
                test.setPadding(new Insets(10,30,10,30));
                test.setBorder(new Border(borderStroke));
                if(monster.getMap().getMaps()[i][j].equals(CellInfo.HUNTER)){
                    BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);
                    Background background = new Background(backgroundFill);
                    test.setBackground(background);
                }
                grid.add(test, j , i );
            }
         }
         return grid;
    }

    


    @Override
    public void update(Subject subj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void update(Subject subj, Object data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
