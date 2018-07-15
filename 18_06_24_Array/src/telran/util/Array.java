package telran.util;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Сергей on 24.06.2018.
 */
public class Array<E> implements List<E> {
    static final int INITIAL_CAPCITY = 16;
    static final int FACTOR = 2;
    Object[] ar = new Object[INITIAL_CAPCITY];
    int size;

    public boolean add(E object) {
        if (size == ar.length)
            allocateArray();
        ar[size++] = object;
        return true;
    }

    private void allocateArray() {
        ar = Arrays.copyOf(ar, ar.length * FACTOR);
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    public E get(int i) {

        E res = null;
        if (i >= 0 && i < size)
            res = (E) ar[i];
        return res;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    public void add(int index, E object) {
        if (index >= 0 && index <= size) {
            if (size == ar.length)
                allocateArray();
            for (int i = size; i > index; i--)
                ar[i] = ar[i - 1];
            ar[index] = object;
            size++;
            // return true;
        }
        //  return false;
    }


    public void shuffle() {
        Object tmp[] = new Object[ar.length];
        int[] indexes = getRandomUniquIndexis();
        for (int i = 0; i < size; i++)
            tmp[i] = ar[indexes[i]];
        ar = tmp;
    }

    private int[] getRandomUniquIndexis() {
        boolean[] history = new boolean[size];
        int[] res = new int[size];
        int ind = 0;
        int size = this.size();

        while (size > 0) {
            int number = (int) (Math.random() * this.size);
            if (history[number])
                continue;
            res[ind++] = number;
            history[number] = true;
            size--;
        }
        return res;
    }

    public int indexOf(Object pattern) { //передаем не Е чтобы мы могли сравнить не только Е с Е но и  Е по другим параметрам (объектам) (какой то внутренний объект)
        return indexOf(ob1 -> ob1.equals(pattern));
    }

    @Override
    public int lastIndexOf(Object o) {
        return indexOfLast(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public int indexOfLast(Object pattern) {
        return indexOfLast(ob1 -> ob1.equals(pattern));

    }


    public int indexOf(Predicate<E> predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test((E) ar[i]))
                return i;
        }
        return -1;
    }

    public int indexOfLast(Predicate<E> predicate) {
        for (int i = size - 1; i >= 0; i--)
            if (predicate.test((E) ar[i]))
                return i;
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<E>(this);
    }

    @Override
    public E remove(int index) {
        E e = null;
        if (index >= 0 && index < size) {
            e = (E) ar[index];
            for (int i = index; i < size - 1; i++)
                ar[i] = ar[i + 1];
            ar[size - 1] = null;
            size--;
            return e;
        }
        return e;
    }

    @Override
    public boolean remove(Object pattern) {
        if (pattern != null) {
            for (int i = 0; i < size; i++)
                if (ar[i].equals(pattern)) {
                    remove(i);
                    return true;
                }
        }
        return false;
    }

    //TODO
   /* @Override
    public boolean removeIf(Predicate<? super E> predicate) {
        boolean isRemoved = false;
        if (predicate != null) {
            Object tmp[] = new Object[ar.length];
            int size = 0;
            for (int i = 0; i < this.size; i++) {
                if (predicate.test((E) ar[i]))
                    isRemoved = true;
                else {
                    tmp[size] = ar[i];
                    size++;
                }
            }
            ar = tmp;
            this.size = size;
        }
        return isRemoved;
    }*/


    public void sort() {
        sort((o1, o2) -> ((Comparable) o1).compareTo(o2));
    }

    @Override
    public void sort(Comparator<? super E> comp) {
        int n = size;
        boolean isSort;
        do {
            isSort = true;
            n--;
            for (int i = 0; i < n; i++) {
                if (comp.compare((E) ar[i], (E) ar[i + 1]) > 0) {
                    swap(i, i + 1);
                    isSort = false;
                }
            }
        } while (!isSort);
    }

    @Override
    public void clear() {

    }

    private void swap(int i, int j) {
        E tmp = (E) ar[i];
        ar[i] = ar[j];
        ar[j] = tmp;
    }


}
