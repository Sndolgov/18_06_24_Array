package telran.util;

import java.util.Iterator;

/**
 * Created by Сергей on 11.07.2018.
 */
public class LinkedListIterator<E> implements Iterator<E> {
    NodeList<E> current;
    NodeList<E> previous;
    NodeList<E> twiceBack;
    LinkedList<E> list;

    public LinkedListIterator(LinkedList<E> list) {
        this.current = list.head;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public E next() {
        E e = current.object;
        if (previous != null)
            twiceBack = previous;
        previous = current;
        current = current.next;
        return e;
    }

    @Override
    public void remove() {
        //TODO
        if (previous == list.head) {
            list.head = current;
            if (previous == list.tail)
                list.tail = null;
        } else {
            if (previous == list.tail) {
                list.tail = twiceBack;
                list.tail.next = null;
            } else twiceBack.next = current;
        }
        list.size--;
        previous = twiceBack;
    }
}
