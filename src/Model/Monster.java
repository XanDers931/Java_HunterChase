package Model;

import Utils.Subject;

public class Monster extends Subject{

    public String nickname;

    public Monster(String nickname){
        this.nickname = nickname;
    }
    
    public String getNickname() {
        return nickname;
    }
    
}
