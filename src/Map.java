import javax.print.attribute.standard.MediaPrintableArea;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class Map {
    private CellInfo[][] map;
    private boolean[][] mapShoot;

    public void init(){
        this.map= new CellInfo[10][5];
        int cpt1=0;
        int cpt2=0;
        for(int i = 0; i < 10; i++)
        {
          for(int y = 0; y < 5; y++)
          {
            this.map[i][y] = CellInfo.EMPTY;
          }
        }
        this.map[0][0]=CellInfo.MONSTER;
        this.map[9][4]= CellInfo.EXIT;
        this.map[5][2]= CellInfo.HUNTER;
    }


    public void displayMap(){
        for (CellInfo[] cellInfos : map) {
            for (CellInfo cellInfo : cellInfos) {
                System.out.print(cellInfo);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Map map= new Map();
        map.init();
        map.displayMap();
    }
}
