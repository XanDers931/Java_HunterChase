package Model;

import Utils.Coordinate;
import View.VueHunter;
import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
public class HunterStrategy implements IHunterStrategy {

   @Override
   public Coordinate play() {
      int tailleCol = GameModel.map.getCol();
      int tailleRow = GameModel.map.getRow();

      int caseToPlayCol = (int) (Math.random() * tailleCol);
      int caseToPlayRow = (int) (Math.random() * tailleRow);

      Coordinate caseToPlay = new Coordinate(caseToPlayRow, caseToPlayCol);

      return caseToPlay;
   }

   public Coordinate smartPlay(Coordinate lastCaseClicked, VueHunter view){
      if(lastCaseClicked.getRow()==-1){
         return play();
      }
      if(view.getHunter().getGameModel().getPath().containsKey(lastCaseClicked)){
         int nbTour = view.getHunter().getGameModel().getPath().get(lastCaseClicked);

         int maxCol = lastCaseClicked.getCol()+nbTour;
         int minCol = lastCaseClicked.getCol()-nbTour;
         int maxRow = lastCaseClicked.getRow()+nbTour;
         int minRow = lastCaseClicked.getRow()-nbTour;

         int tailleCol = GameModel.map.getCol();
         int tailleRow = GameModel.map.getRow();

         if(maxCol>tailleCol) maxCol = tailleCol;
         if(minCol<0) minCol = 0;
         if(maxRow>tailleRow) maxRow = tailleRow;
         if(minRow<0) minRow = 0;

         int caseToPlayCol = (int) (Math.random() * (maxCol-minCol));
         int caseToPlayRow = (int) (Math.random() * (maxRow-minRow));

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
      // do nothing
   }

}
