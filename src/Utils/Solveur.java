package Utils;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class Solveur {
    
    CellInfo[][] map;
    // les marques
    char[][] marque;

    public Solveur(CellInfo[][] map) {
        this.map = map;
        initMarque();;
    }

    int[] getEntre(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j]==CellInfo.MONSTER) return new int[] {i,j};
            }
        }
        return null;
    }

    int[] getSortie(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j]==CellInfo.EXIT) return new int[] {i,j};
            }
        }
        return null;
    }

    boolean estSortie(int x,int y){
        return x == getSortie()[0] && y == getSortie()[1];
    }

    boolean estSortie(int[] s) {
        return s[0] == getSortie()[0] && s[1] == getSortie()[1];
    }

    // est-ce que (x,y) est un mur ?
    boolean estMur(int x, int y) {
        return map[x][y].equals(CellInfo.WALL);
    }

    // est-ce que (x,y) est un mur ?
    boolean estMur(int[] m) {
        return estMur(m[0], m[1]);
    }

    final char VIDE = 'a', ROUGE = 'b', ROSE = 'c';

    void initMarque(){
        marque = new char[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                marque[i][j] = VIDE;
            }
        }
    }

    // teste si la cellule (x,y) comporte une marque
    boolean estMarque(int x, int y) {
        return marque[x][y] != VIDE;
    }

    boolean estMarque(int[] c) {
        return estMarque(c[0], c[1]);
    }

    // pose une marque
    void poserMarque(int x, int y) {
        marque[x][y] = ROSE;
    }

    void poserMarque(int[] c) {
        marque[c[0]][c[1]] = ROSE;
    }

    void poserMarqueChemin(int x, int y) {
        marque[x][y] = ROUGE;
    }

    void poserMarqueChemin(int[] c) {
        marque[c[0]][c[1]] = ROUGE;
    }

    public boolean estFaisable(){       
        Stack<Coordinate> p = new Stack<Coordinate>();
        int[] entre = getEntre();
        int[] sortie = getSortie();
        Coordinate end = new Coordinate(sortie[0], sortie[1]);
        Coordinate start = new Coordinate(entre[0], entre[1]);
        Coordinate voisin;
        p.push(new Coordinate(entre[0], entre[1]));
        poserMarque(start.getRow(), start.getCol());
        Coordinate c;
        while(!p.isEmpty()){
            c = p.peek();
            if(c.equals(end)){
                return true;
            }
            else{
                voisin = getVoisines(c);  
                if(voisin!= null){
                    p.push(voisin);
                    poserMarque(voisin.toArray());
                }
                else{
                    p.pop();
                }
            }
        }
        return false;
    }

    public Coordinate getVoisines(Coordinate c) {
        int[] cell = c.toArray();
        if (!estMarque(cell[0],cell[1]+1 ) && !estMur(cell[0],cell[1]+1  )) {
            return new Coordinate(cell[0],cell[1]+1);
        }
        if (!estMarque(cell[0]+1,cell[1] ) && !estMur(cell[0]+1,cell[1] )) {
            return new Coordinate(cell[0]+1,cell[1]);
        }
        if (!estMarque(cell[0]-1,cell[1] ) && !estMur(cell[0]-1,cell[1] )) {
            return new Coordinate(cell[0]-1,cell[1]);
        }
        if (!estMarque(cell[0],cell[1]-1 ) && !estMur(cell[0],cell[1]-1 )) {
            return new Coordinate(cell[0],cell[1]-1);
        }
        return null;
    }

    
}
