package Utils;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class File<E> {
    public ArrayList<E> cells;

    public File(){
        this.cells = new ArrayList<E>();
    }

    public void push(E e){
        if(e.getClass().equals(Coordinate.class)){
            cells.add(e);
        }
    }

    public E pop(){
        if(cells.isEmpty()){
            throw new EmptyStackException();
        }
        return cells.remove(0);
    }

    public E peek(){
        if(cells.isEmpty()){
            throw new EmptyStackException();
        }
        return cells.get(0);
    }

    public boolean isLast() {
        return cells.size()==1;
    }

    public boolean isEmpty () {
        return cells.isEmpty();
    }
}
