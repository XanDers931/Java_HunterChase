package Model;

import Main.Maps;
import Utils.Coordinate;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.scene.control.Cell;

public class Monster extends Subject{

    public String nickname;
    public Maps map;
    public boolean tour;
    public boolean canMoove;
    public Coordinate cordMonster;
    public int[][] path;

    public Monster(String nickname){
        this.tour=true;
        this.nickname = nickname;
        this.map= new Maps();
        canMoove=true;
        map.initMap();
        initPath();
    }
    
    public String getNickname() {
        return nickname;
    }

    public Maps getMap() {
        return map;
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
        Coordinate cord = map.getCordUser(CellInfo.MONSTER);
        if(getMap().getMaps()[x][y].equals(CellInfo.WALL) || (x==cord.getRow()&& y==cord.getCol())) return false;
        // Vérifiez si les nouvelles coordonnées sont adjacentes aux coordonnées actuelles du monstre
        if (isAdjacent(cord.getRow(), cord.getCol(), x, y)) {
            map.getMaps()[cord.getRow()][cord.getCol()] = CellInfo.EMPTY;
            map.getMaps()[x][y] = CellInfo.MONSTER;
            return true;
        }
        return false;
    }

    public boolean victory(int x,int y){
        return map.getMaps()[x][y]==CellInfo.EXIT;
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
}
