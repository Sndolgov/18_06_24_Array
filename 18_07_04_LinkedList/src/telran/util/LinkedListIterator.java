package telran.util;

import java.util.Iterator;

/**
 * Created by Сергей on 11.07.2018.
 */
public class LinkedListIterator<E> implements Iterator<E> {
    NodeList<E> current;
    LinkedList<E> list;

    public LinkedListIterator(LinkedList<E> list) {
        this.current=list.head;
        this.list=list;
    }

    @Override
    public boolean hasNext() {
        return current!=null;
    }

    @Override
    public E next() {
        E e = current.object;
        current=current.next;
        return e;
    }

    @Override
    public void remove() {
        //TODO
    }
}
