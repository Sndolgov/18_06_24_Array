package telran.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Сергей on 29.07.2018.
 */
public class HashTableIterator<E> implements Iterator<E> {
    private HashTable<E> table;
    private List<E>[] hashTable;
    private int basket = 0;
    private int indexInList = 0;
    private int previousIndex;
    private List<E> currentList;
    private List<E> previousList;
    private E current;

    public HashTableIterator(HashTable<E> table) {
        this.table=table;
        hashTable = table.hashTable;
        if (table.size() > 0)
            current = getNext();
    }


    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public E next() {
        E e = current;
        previousIndex = indexInList - 1;
        previousList = currentList;
        current = getNext();
        return e;
    }

    private E getNext() {
        while ((currentList == null || indexInList >= currentList.size()) && basket < hashTable.length) {
            currentList = hashTable[basket++];
            indexInList = 0;
        }
        if (currentList != null && indexInList < currentList.size()) {
            return currentList.get(indexInList++);
        }
        return null;
    }

    @Override
    public void remove() {
        previousList.remove(previousIndex);
        table.size--;
        if (previousList == currentList)
            indexInList--;
    }
}
