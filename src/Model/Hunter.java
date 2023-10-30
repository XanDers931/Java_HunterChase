package Model;

import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.scene.control.Cell;

import java.util.HashMap;

import Main.Maps;

public class Hunter extends Subject {

    private String nickname;
    private Maps map;
    public boolean tour ;
    public boolean canMoove;
    public int[] hunted;

    
    public Hunter(String nickname){
        this.tour=false;
        this.nickname = nickname;
        this.canMoove=false;
        map= new Maps();
        map.initMap();
        map.initShoot();
        /* for(int i = 0; i < map.getMaps().length; i++){
            for(int j = 0; j < map.getMaps()[i].length; j++){
                if(map.getMaps()[i][j].equals(CellInfo.MONSTER)){
                    //map.getMaps()[i][j] = CellInfo.EMPTY;
                }
            }
        }
        */
        map.getMaps();
        this.hunted= new int[2];
    }

    public int[] getHunted() {
        return hunted;
    }

    public void setHunted(int[] hunted) {
        this.hunted = hunted;
    }

    public String getNickname() {
        return nickname;
    }

    public Maps getMap() {
        return map;
    }

    public boolean victory(int x,int y){
        return map.getMaps()[x][y]==CellInfo.MONSTER;
    }

    public void changeCanMoove(){
        canMoove=!canMoove;
    }
}
