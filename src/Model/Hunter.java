package Model;

import Utils.Coordinate;
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
    public Coordinate hunted;
    

    
    public Hunter(String nickname){
        this.tour=false;
        this.nickname = nickname;
        this.canMoove=false;
        map= new Maps();
        map.initMap();
        map.initShoot();
        map.getMaps();
        this.hunted= new Coordinate(0,0);
    }

    public Coordinate getHunted() {
        return hunted;
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

    public void shoot(int x, int y){
        this.map.getMapShoot()[x][y]=true;
        performActionThatChangesState(x, y);
    }

    public void performActionThatChangesState(int x, int y) {
        // Effectuez les opérations qui modifient l'état de Hunter ici
        // Par exemple, enregistrez les coordonnées du clic ou de l'action

        Coordinate coordonnees = new Coordinate(x, y);

        // Après les modifications, appelez notifyObservers avec l'objet Coordonnees en paramètre
        notifyObservers(coordonnees);
    }
}
