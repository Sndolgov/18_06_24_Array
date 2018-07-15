package telran.util;

import java.util.Iterator;

/**
 * Created by Сергей on 11.07.2018.
 */
public class ArrayIterator<E> implements Iterator<E> {
    private int current=0;
    private Object[] ar;
    private int size;

    public ArrayIterator(Object[] ar, int size) {

        this.ar = ar;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return current<size;
    }

    @Override
    public E next() {
        return (E)ar[current++];
    }
}
