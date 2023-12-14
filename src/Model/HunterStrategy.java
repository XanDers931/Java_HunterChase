package Model;


import Utils.Coordinate;
import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class HunterStrategy implements IHunterStrategy {

    @Override
    public ICoordinate play() {
       int tailleCol = GameModel.map.getCol();
       int tailleRow = GameModel.map.getRow();

       int caseToPlayCol = (int) (Math.random() * tailleCol);
       int caseToPlayRow = (int) (Math.random() * tailleRow);
    
       ICoordinate caseToPlay = new Coordinate(caseToPlayRow, caseToPlayCol);

       return caseToPlay;
    }

    @Override
    public void update(ICellEvent arg0) {
       // do nothing
    }

    @Override
    public void initialize(int arg0, int arg1) {
       // do nothing
    }
    
}
