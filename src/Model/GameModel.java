package Model;

import java.util.HashMap;
import java.util.Map;

import Main.Maps;
import Utils.Coordinate;

public class GameModel {
    //Controles généraux du jeu
    private Monster monster;
    private Hunter hunter;

    private int turn;

    private Map<Coordinate, Integer> path;

    private Maps map;

    public GameModel(Monster monster, Hunter hunter){
        this.monster = monster;
        this.hunter = hunter;
        this.turn = 0;
        this.map = new Maps();
        this.path = new HashMap<>();
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
        path.put(co, turn);
    }

    //renvoie True si l'un des deux joueurs a gagné
    public boolean anyoneWin(Coordinate co){
        return (monster.victory(co)||hunter.victory(co));
    }

    //Execute un tour de jeu
    public void Playround(){
        turn++;
    }
}
