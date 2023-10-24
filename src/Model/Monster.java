package Model;

import Main.Maps;
import Utils.Subject;

public class Monster extends Subject{

    public String nickname;
    public Maps map;

    public Monster(String nickname){
        this.nickname = nickname;
        map.initMap();
    }
    
    public String getNickname() {
        return nickname;
    }
    
}
