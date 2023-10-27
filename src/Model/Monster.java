package Model;

import Main.Maps;
import Utils.Subject;

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

    
    
}
