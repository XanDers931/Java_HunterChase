package Utils;

import java.util.ArrayList;
import java.util.Collections;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class Solveur {
    
    private CellInfo[][] map;
    // les marques
    private char[][] marque;

    public Solveur(CellInfo[][] map) {
        this.map = map;
        initMarque();;
    }

    public void setMap(CellInfo[][] map) {
        this.map = map;
    }

    public CellInfo[][] getMap() {
        return map;
    }

    private int[] getEntre(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j]==CellInfo.MONSTER) return new int[] {i,j};
            }
        }
        return null;
    }

    private int[] getSortie(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j]==CellInfo.EXIT) return new int[] {i,j};
            }
        }
        return null;
    }

    // est-ce que (x,y) est un mur ?
    private boolean estMur(int x, int y) {
        return map[x][y].equals(CellInfo.WALL);
    }

    private final char VIDE = 'a',  ROSE = 'c';

    private void initMarque(){
        marque = new char[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                marque[i][j] = VIDE;
            }
        }
    }

    // teste si la cellule (x,y) comporte une marque
    private boolean estMarque(int x, int y) {
        return marque[x][y] != VIDE;
    }

    // pose une marque
    private void poserMarque(int x, int y) {
        marque[x][y] = ROSE;
    }

    private void poserMarque(int[] c) {
        marque[c[0]][c[1]] = ROSE;
    }

    //renvoi une ArrayList du plus court chemin en partant du monstre et commencant directement par le premier coup à jouer
    public ArrayList<Coordinate> estFaisable(){       
        File<Coordinate> p = new File<Coordinate>();
        int[] entre = getEntre();
        int[] sortie = getSortie();
        Coordinate end = new Coordinate(sortie[0], sortie[1]);
        Coordinate start = new Coordinate(entre[0], entre[1]);
        System.out.println("SORTIE: " + end + "\nENTREE " + start);
        Coordinate voisin;
        boolean found = false;
        p.push(new Coordinate(entre[0], entre[1]));
        poserMarque(start.getRow(), start.getCol());
        Coordinate c;
        while(!p.isEmpty()){
            c = p.peek();
            if(c.equals(end)){
                found = true;
                System.out.println(found);
                break;
            }
            else{
                voisin = getVoisinesDiagonal(c, start);  
                System.out.println("Case actuel : "+c+ " | Case voisine choisi : "+voisin);
                if(voisin!= null){
                    p.push(voisin);
                    poserMarque(voisin.toArray());
                }
                else{
                    p.pop();
                }
            }
        }
        if(found){
            ArrayList<Coordinate> res = new ArrayList<>();
            Coordinate tmp = p.pop();
            while(!tmp.equals(start)){
                res.add(tmp);
                tmp = tmp.getFather();
            }
            reverseArrayList(res);
            return res;
        }
        else{
            return null;
        }
    }

    private  Coordinate getVoisines(Coordinate c, Coordinate start) {
        int[] cell = c.toArray();
        if (!estMarque(cell[0],cell[1]+1 ) && !estMur(cell[0],cell[1]+1  )) {
            return new Coordinate(cell[0],cell[1]+1, c);
        }
        if (!estMarque(cell[0]+1,cell[1] ) && !estMur(cell[0]+1,cell[1] )) {
            return new Coordinate(cell[0]+1,cell[1], c);
        }
        if (!estMarque(cell[0]-1,cell[1] ) && !estMur(cell[0]-1,cell[1] )) {
            return new Coordinate(cell[0]-1,cell[1], c);
        }
        
        if (!estMarque(cell[0],cell[1]-1 ) && !estMur(cell[0],cell[1]-1 )) {
            return new Coordinate(cell[0],cell[1]-1, c);
        }
        return null;
    }

    private  Coordinate getVoisinesDiagonal(Coordinate c,Coordinate start) {
        int[] cell = c.toArray();
        if (!estMarque(cell[0],cell[1]+1 ) && !estMur(cell[0],cell[1]+1  )) {
            return new Coordinate(cell[0],cell[1]+1, c);
        }
        if (!estMarque(cell[0]+1,cell[1] ) && !estMur(cell[0]+1,cell[1] )) {
            return new Coordinate(cell[0]+1,cell[1], c);
        }
        if (!estMarque(cell[0]-1,cell[1] ) && !estMur(cell[0]-1,cell[1] )) {
            return new Coordinate(cell[0]-1,cell[1], c);
        }
        if (!estMarque(cell[0],cell[1]-1 ) && !estMur(cell[0],cell[1]-1 )) {
            return new Coordinate(cell[0],cell[1]-1, c);
        }

        if (!estMarque(cell[0]+1,cell[1]+1 ) && !estMur(cell[0]+1,cell[1]+1 )) {
            return new Coordinate(cell[0]+1,cell[1]+1, c);
        }
        if (!estMarque(cell[0]-1,cell[1]+1 ) && !estMur(cell[0]-1,cell[1]+1 )) {
            return new Coordinate(cell[0]-1,cell[1]+1, c);
        }
        if (!estMarque(cell[0]-1,cell[1]-1 ) && !estMur(cell[0]-1,cell[1]-1 )) {
            return new Coordinate(cell[0]-1,cell[1]-1, c);
        }
        if (!estMarque(cell[0]+1,cell[1]-1 ) && !estMur(cell[0]+1,cell[1]-1 )) {
            return new Coordinate(cell[0]+1,cell[1]-1, c);
        }
        return null;
    }

    public static <T> void reverseArrayList(ArrayList<T> list) {
        Collections.reverse(list);
    }
}
