package Model;

import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import Main.Maps;

public class Hunter extends Subject {

    private String nickname;
    private Maps map;
    public boolean tour ;
    
    public Hunter(String nickname){
        this.tour=false;
        this.nickname = nickname;
        map= new Maps();
        map.initMap();
        map.initShoot();
        for(int i = 0; i < map.getMaps().length; i++){
            for(int j = 0; j < map.getMaps()[i].length; j++){
                /*if(map.getMaps()[i][j].equals(CellInfo.MONSTER)){
                    map.getMaps()[i][j] = CellInfo.EMPTY;
                }*/
            }
        }
        map.getMaps();
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
    
}
