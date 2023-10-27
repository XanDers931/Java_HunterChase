package Model;

import Main.Maps;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.scene.control.Cell;

public class Monster extends Subject{

    public String nickname;
    public Maps map;

    public Monster(String nickname){
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
            for(int j=0;i<map.getMaps()[i].length;j++){
                if(map.getMaps()[i][j]==CellInfo.MONSTER){
                    cord[0]=i;
                    cord[1]=j;
                    return cord;
                }
            }
        }
        return null;
    }

    public void moveMonster(int x, int y){
        int[] cord = getCordMonster();
        map.getMaps()[cord[0]][cord[1]]=CellInfo.EMPTY;
        map.getMaps()[x][y]=CellInfo.MONSTER;
        //System.out.println(cord[0]+""+cord[1]);
    }


    

    
    
}
