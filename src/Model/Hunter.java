package Model;

import Utils.Subject;
import Main.Maps;

public class Hunter extends Subject {

    private String nickname;
    private Maps map;
    
    public Hunter(String nickname){
        this.nickname = nickname;
        map.initMap();
        map.initShoot();
    }

    public String getNickname() {
        return nickname;
    }

    public Maps getMap() {
        return map;
    }
    
}
