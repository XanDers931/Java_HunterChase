package Model;

import Main.Maps;
import Utils.Coordinate;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class Monster extends Subject{

    public String nickname;
    public boolean tour;
    public boolean canMoove;
    public static Coordinate cordMonster;
    public GameModel gameModel;
    public int[][] path;
  

    public Monster(String nickname,GameModel gameModel){
        this.tour=true;
        this.nickname = nickname;
        this.gameModel= gameModel;
        canMoove=true;
        //initPath();
        /*for(int i=0; i<map.getMaps().length; i++){
            for(int j=0;j<map.getMaps()[i].length; j++){
                if(map.getMaps()[i][j]==CellInfo.MONSTER){
                    cordMonster.setCol(j);
                    cordMonster.setRow(i);
                }
            }
        }*/
        cordMonster= new Coordinate(0, 0);
        initPath();
    }
    
    public static Coordinate getCordMonster() {
        return cordMonster;
    }

    public String getNickname() {
        return nickname;
    }

    
    public boolean isTour() {
        return tour;
    }

    public boolean getCanMoove() {
        return canMoove;
    }

    private boolean isAdjacent(int currentX, int currentY, int newX, int newY) {
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);
        // Vérifiez si les coordonnées (newX, newY) sont adjacentes horizontalement, verticalement ou en diagonale
        return (dx >= 0 && dx <= 1 && dy >= 0 && dy <= 1);
    }
    
    public boolean moveMonster(int x, int y) {
        Coordinate cord = cordMonster;
        Maps map = gameModel.getMap();
        if(gameModel.isWall(new Coordinate(x, y)) || (x==cord.getRow()&& y==cord.getCol())) return false;
        // Vérifiez si les nouvelles coordonnées sont adjacentes aux coordonnées actuelles du monstre
        if (isAdjacent(cord.getRow(), cord.getCol(), x, y)) {
            map.getMaps()[cord.getRow()][cord.getCol()] = CellInfo.EMPTY;
            map.getMaps()[x][y] = CellInfo.MONSTER;
           // performActionThatChangesState(x, y);
            return true;
        }
        return false;
    }

    public void addCurrentCordMonster(int x, int y){
        cordMonster.setRow(x);
        cordMonster.setCol(y);
    }

    public boolean victory(int x,int y){
        return gameModel.getMap().getMaps()[x][y]==CellInfo.EXIT;
    }

    public void changeCanMoove(){
        canMoove=!canMoove;
    }

    

    public void initPath(){
        this.path = new int[10][5];
        for(int i = 0; i < 10; i++)
        {
          for(int y = 0; y < 5; y++)
          {
            path[i][y] = -1;
          }
        }
    }

    public void performActionThatChangesState(int x, int y) {
        // Effectuez les opérations qui modifient l'état de Hunter ici
        // Par exemple, enregistrez les coordonnées du clic ou de l'action

        Coordinate coordonnees = new Coordinate(x, y);

        // Après les modifications, appelez notifyObservers avec l'objet Coordonnees en paramètre
        notifyObservers(coordonnees);
    }

    public GameModel getGameModel() {
        return gameModel;
    }
}
