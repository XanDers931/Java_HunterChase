package Model;

import java.util.HashMap;
import java.util.Map;



import Main.Maps;
import Utils.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class GameModel {
    //Controles généraux du jeu
    private Monster monster;
    private Hunter hunter;

    private int turn;

    public int currentPlayer;

    private Map<Coordinate, Integer> path;

    public static Maps map;


    public GameModel(Monster monster, Hunter hunter,int size){
        this.monster = monster;
        this.hunter = hunter;
        this.turn = 0;
        map = new Maps(size,size,10);
        this.path = new HashMap<>();
        currentPlayer= 1;
        this.addPath(map.getCordUser(CellInfo.MONSTER));
       
    }

    

  


    //retourne le tour actuel
    public int getTurn(){
        return this.turn;
    }

    //retourne le numéro du tour en case "co" renvoie null si n'existe pas
    public int getPath(Coordinate co){
        return path.get(co);
    }

    //Ajoute une les coordonnées
    public void addPath(Coordinate co){
        System.out.println("Adding path for coordinates: " + co.toString());
        path.put(co, turn+1);
    }

    //renvoie True si l'un des deux joueurs a gagné
    public boolean anyoneWin(Coordinate co){
        return (monster.victory(co.getRow(),co.getCol())||hunter.victory(co.getRow(),co.getCol()));
    }

    public boolean isWall(Coordinate co){
        return map.getMaps()[co.getRow()][co.getCol()]==CellInfo.WALL;
    }

    public void changeCurrentPlayer(){
        if(currentPlayer==1){
            currentPlayer=2;
        }else{
            currentPlayer=1;
        }
    }

    //Execute un tour de jeu
    public void Playround(){
        turn++;
    }

    public Maps getMap() {
        return map;
    }

    public Monster getMonster() {
        return monster;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public Map<Coordinate, Integer> getPath() {
        return path;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }


    

    

    
}
