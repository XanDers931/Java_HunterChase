package Model;


import Utils.Coordinate;
import Utils.Subject;


public class Hunter extends Subject {

    private String nickname;
    private GameModel gameModel;
    public boolean tour ;
    public boolean canMoove;
    public Coordinate hunted;
        
    public Hunter(String nickname,GameModel gameModel){
        this.tour=false;
        this.nickname = nickname;
        this.canMoove=false;
        this.gameModel=gameModel;
        this.hunted= new Coordinate(1000, 1000);
    }

    public Coordinate getHunted() {
        return hunted;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean victory(int x, int y){
        return gameModel.isMonster(new Coordinate(x, y));
    }

    public void changeCanMoove(){
        canMoove=!canMoove;
    }
 
    public void shoot(int x, int y){
        this.gameModel.getMap().getMapShoot()[x][y]=true;
        this.hunted= new Coordinate(x, y);
        //performActionThatChangesState(x, y);
    }

    public void performActionThatChangesState(int x, int y) {
        // Effectuez les opérations qui modifient l'état de Hunter ici
        // Par exemple, enregistrez les coordonnées du clic ou de l'action

        Coordinate coordonnees = new Coordinate(x, y);

        // Après les modifications, appelez notifyObservers avec l'objet Coordonnees en paramètre
        notifyObservers(coordonnees);
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    
}
