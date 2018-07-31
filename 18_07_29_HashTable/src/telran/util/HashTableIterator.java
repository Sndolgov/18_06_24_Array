package telran.util;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Сергей on 29.07.2018.
 */
public class HashTableIterator<E> implements Iterator<E> {
    private HashTable<E> table;
    private List<E>[] hashTable;
    private Iterator<E> currentIterator;
    private int basket = 0;


    public HashTableIterator(HashTable<E> table) {
        this.table = table;
        this.hashTable = table.hashTable;
        if (table.size != 0) {
            currentIterator = getNextIterator();
        }
    }

    @Override
    public boolean hasNext() {
        if (currentIterator!=null && !currentIterator.hasNext())
            currentIterator = getNextIterator();
        return currentIterator != null;
    }

    @Override
    public E next() {
            return currentIterator.next();
    }

    private Iterator<E> getNextIterator() {
        List<E> list = null;
        while (list == null && basket < hashTable.length) {
            list = hashTable[basket++];
            if (list != null && list.size() < 1)
                list = null;
        }
        return list!=null?list.iterator():null;
    }

    @Override
    public void remove() {
        currentIterator.remove();
        table.size--;
    }

}
