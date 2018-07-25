package telran.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Сергей on 15.07.2018.
 */

@SuppressWarnings("unchecked")
public class Tree<E> implements Set<E> {
    Comparator<E> comp;
    NodeTree<E> root;

    public Tree() {
        comp = (Comparator<E>) Comparator.naturalOrder();
    }

    public Tree(Comparator<E> comp) {
        this.comp = comp;
    }

    int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        NodeTree<E> current = root;
        while (current != null && !o.equals(current.obj)) {
            current = comp.compare((E) o, current.obj) < 0 ?
                    current.left : current.right;
        }
        return current != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeIterator<E>(this);
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
        if (contains(e))
            return false;
        NodeTree<E> parent = null;
        NodeTree<E> newNode = new NodeTree<>(e);
        if (root == null) {
            root = newNode;
        } else {
            parent = getParent(e);
            if (comp.compare(e, parent.obj) < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
        newNode.parent = parent;
        size++;
        return true;
    }

    private NodeTree<E> getParent(E e) {
        NodeTree<E> parent = root;
        NodeTree<E> current = root;
        while (current != null) {
            parent = current;
            current = comp.compare(e, current.obj) < 0 ?
                    current.left : current.right;
        }
        return parent;
    }

    @Override
    public boolean remove(Object o) {
        return false;
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
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}

