package Model;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import Utils.Subject;
import Main.Maps;

public class Hunter extends Subject implements IHunterStrategy {

    private String nickname;
    private Maps map;
    
    public Hunter(String nickname){
        this.nickname = nickname;
    }

    

    @Override
    public ICoordinate play() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    @Override
    public void update(ICellEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void initialize(int arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }



    public String getNickname() {
        return nickname;
    }

    public Maps getMap() {
        return map;
    }
    
}