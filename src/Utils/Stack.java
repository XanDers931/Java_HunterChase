package Utils;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class Stack<E> {
    private ArrayList<E> cells;

    public Stack(){
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
        return cells.remove(cells.size()-1);
    }

    public E peek(){
        if(cells.isEmpty()){
            throw new EmptyStackException();
        }
        return cells.get(cells.size()-1);
    }

    @Override
    public String toString() {
        return "Stack [cells=" + cells + "]";
    }

    public boolean isEmpty () {
        return cells.isEmpty();
    }
}
