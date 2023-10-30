package Utils;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class Coordinate implements ICoordinate {

    private int row;
    private int col;

    public Coordinate(int row,int col){
        this.row=row;
        this.col=col;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }    
}
