package telran.util;

import java.util.*;

/**
 * Created by Сергей on 29.07.2018.
 */

@SuppressWarnings("unchecked")
public class HashTable<E> implements Set<E> { //сложность добавления и поиска - 1
    List<E>[] hashTable;
    private float factor = 0.75f; //size/hashTable.length
    int size;
    private static final int INITIAL_LENGTH = 16;

    public HashTable() {
        this(INITIAL_LENGTH);
    }

    public HashTable(int length) {
        hashTable = new List[length];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o);
        return hashTable[index] != null && hashTable[index].contains(o);
    }

    private int getIndex(Object o) {
        int hashCode = o.hashCode();
        return Math.abs(hashCode) % hashTable.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator<E>(this);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        int index = getIndex(e);
        if (hashTable[index] == null)
            hashTable[index] = new LinkedList<E>();
        else if (hashTable[index].contains(e))
            return false;
        return addIndex(index, e);
    }

    private boolean addIndex(int index, E e) {
        if (hashTable[index].add(e)) {
            size++;
            if ((float) size / hashTable.length > factor)
                tableRecreation();
            return true;
        }
        return false;
    }

    private void tableRecreation() {
        List<E>[] tmp = hashTable;
        hashTable = new List[tmp.length * 2];
        size = 0;
        for (List<E> list : tmp)
            if (list != null)
                this.addAll(list);
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);
        return hashTable[index].remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!this.contains(o))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean res = false;
        for (E e : c) {
            if (this.add(e))
                res = true;
        }
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.containsAll(this))
            return false;
        removeIf(x -> !c.contains(x));
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean res = false;
        if (removeIf(c::contains))
            res = true;
        return res;
    }

    @Override
    public void clear() {
        hashTable = new List[INITIAL_LENGTH];
        size = 0;
    }
}
