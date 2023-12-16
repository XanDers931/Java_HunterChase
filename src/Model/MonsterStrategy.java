package Model;

import java.util.ArrayList;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class MonsterStrategy implements IMonsterStrategy {

    Utils.Solveur solve = new Utils.Solveur(GameModel.map.getMaps());
    ArrayList<Utils.Coordinate> solution = solve.estFaisable();

    @Override
    public ICoordinate play() {
        ICoordinate caseToPlay = null;
        if (solution != null) {
            caseToPlay = solution.get(0);
            solution.remove(0);
        }

        return caseToPlay;
    }

    @Override
    public void update(ICellEvent arg0) {
        // do nothing
    }

    @Override
    public void initialize(boolean[][] arg0) {
        // do nothing
    }

}
