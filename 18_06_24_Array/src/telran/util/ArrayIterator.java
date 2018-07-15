package telran.util;

import java.util.Iterator;

/**
 * Created by Сергей on 11.07.2018.
 */
public class ArrayIterator<E> implements Iterator<E> {
    private int current = 0;
    private Object[] ar;
    private int size;
    private Array<E> array;

    public ArrayIterator(Array<E> array) {
        this.ar = array.ar;
        this.size = array.size;
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return current < size;
    }

    @Override
    public E next() {
        return (E) ar[current++];
    }

    @Override
    public void remove() {
        //TODO
        current--;
        for (int i = current; i < size - 1; i++)
            ar[i] = ar[i + 1];
        ar[size - 1] = null;
        array.size = --size;
    }
}
