package Model;

import Utils.Coordinate;
import View.VueHunter;
import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;

public class HunterStrategy implements IHunterStrategy {

   Coordinate sizeOfMap;

   

   @Override
   public Coordinate play() {
      int tailleMinCol = 1;
      int tailleMaxCol = sizeOfMap.getCol()-1;
      int tailleMinRow = 1;
      int tailleMaxRow = sizeOfMap.getRow()-1;

      int caseToPlayCol = tailleMinCol + (int) (Math.random() * (tailleMaxCol-tailleMinCol));
      int caseToPlayRow = tailleMinRow + (int) (Math.random() * (tailleMaxRow-tailleMinRow));

      Coordinate caseToPlay = new Coordinate(caseToPlayRow, caseToPlayCol);
      return caseToPlay;
   }

   public Coordinate smartPlay(Coordinate lastCaseClicked, VueHunter view) {
      if (lastCaseClicked.getRow() == -1) {
         return play();
      }
      if (view.getHunter().getGameModel().getPath().containsKey(lastCaseClicked)) {
         int nbTour = view.getHunter().getGameModel().getPath().get(lastCaseClicked);

         int maxCol = lastCaseClicked.getCol() + nbTour;
         int minCol = lastCaseClicked.getCol() - nbTour;
         int maxRow = lastCaseClicked.getRow() + nbTour;
         int minRow = lastCaseClicked.getRow() - nbTour;

         int tailleCol = GameModel.map.getCol();
         int tailleRow = GameModel.map.getRow();

         if (maxCol >= tailleCol)
            maxCol = tailleCol-1;
         if (minCol <= 0)
            minCol = 1;
         if (maxRow >= tailleRow)
            maxRow = tailleRow-1;
         if (minRow <= 0)
            minRow = 1;

         int caseToPlayCol = minCol + (int) (Math.random() * (maxCol - minCol));
         int caseToPlayRow = minRow + (int) (Math.random() * (maxRow - minRow));

         Coordinate caseToPlay = new Coordinate(caseToPlayRow, caseToPlayCol);
         return caseToPlay;
      }
      return play();
   }

   @Override
   public void update(ICellEvent arg0) {
      // do nothing
   }

   @Override
   public void initialize(int arg0, int arg1) {
      this.sizeOfMap = new Coordinate(arg0, arg1);
   }

}
