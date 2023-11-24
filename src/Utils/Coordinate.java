package Utils;

import java.util.Objects;

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

    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Coordinate other = (Coordinate) obj;
    return this.row == other.row && this.col == other.col;
}

@Override
public int hashCode() {
    return Objects.hash(row, col);
}


    int[] toArray() {
        return new int[] {row, col};
    }

    @Override
    public String toString() {
        return "Coordinate [row=" + row + ", col=" + col + "]";
    }

    
}
