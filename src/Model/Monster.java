package Model;

import Main.Maps;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.scene.control.Cell;

public class Monster extends Subject{

    public String nickname;
    public Maps map;
    public boolean tour;

    public Monster(String nickname){
        this.tour=true;
        this.nickname = nickname;
        this.map= new Maps();
        map.initMap();
    }
    
    public String getNickname() {
        return nickname;
    }

    public Maps getMap() {
        return map;
    }

    public void setMap(int x,int y){
        map.setOnMap(x, y);
    }

    public int[] getCordMonster(){
        int[] cord = new int[2];
        for(int i=0;i<map.getMaps().length;i++){
            for(int j=0;j<map.getMaps()[i].length;j++){
                if(map.getMaps()[i][j]==CellInfo.MONSTER){
                    cord[0]=i;
                    cord[1]=j;
                    return cord;
                }
            }
        }
        return null;
    }

    private boolean isAdjacent(int currentX, int currentY, int newX, int newY) {
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);
    
        // Vérifiez si les coordonnées (newX, newY) sont adjacentes horizontalement, verticalement ou en diagonale
        return (dx >= 0 && dx <= 1 && dy >= 0 && dy <= 1);
    }
    
    

    public boolean moveMonster(int x, int y) {
        int[] cord = getCordMonster();
        if(getMap().getMaps()[x][y]==CellInfo.WALL) return false;
        // Vérifiez si les nouvelles coordonnées sont adjacentes aux coordonnées actuelles du monstre
        if (isAdjacent(cord[0], cord[1], x, y)) {
            map.getMaps()[cord[0]][cord[1]] = CellInfo.EMPTY;
            map.getMaps()[x][y] = CellInfo.MONSTER;
            return true;
        }
        return false;
    }
    


    

    
    
}
