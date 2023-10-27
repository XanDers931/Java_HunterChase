package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.print.attribute.standard.MediaPrintableArea;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class Maps {
    
    
    
    
    public CellInfo[][] getMaps() {
        return map;
    }

    public boolean[][] getMapShoot() {
        return mapShoot;
    }

    private CellInfo[][] map;
    private boolean[][] mapShoot;

    public void initMap(){
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

    public void initShoot(){
        this.mapShoot= new boolean[10][5];
        for(int i = 0; i < 10; i++)
        {
          for(int y = 0; y < 5; y++)
          {
            this.mapShoot[i][y] = false;
          }
        }
    }


    public void displayMap(){
        for (CellInfo[] cellInfos : map) {
            for (CellInfo cellInfo : cellInfos) {
                String temp = cellInfo.toString().substring(0, 1);
                System.out.print(temp);
            }
            System.out.println();
        }
    }

    public void setOnMap(int x, int y){
        map[x][y]=CellInfo.WALL;
    }

    public void readMapFromCSV(int ligne, int colonne){
        File csv = new File("./res/Maps.csv");
        this.map = new CellInfo[ligne][colonne];
        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String str;
            int cpt = 0;
            while ((str = reader.readLine()) != null) {
                String[] lst  = str.split(",");
                for(int i=0; i<colonne;i++){
                    map[cpt][i] = CellInfo.valueOf(lst[i]);                    
                }
                cpt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Maps map= new Maps();
        map.readMapFromCSV(5, 5);
        map.displayMap();
        //map.initMap();
    }
}
