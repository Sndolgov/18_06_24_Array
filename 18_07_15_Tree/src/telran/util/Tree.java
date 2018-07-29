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
        return findNode(o) != null;
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
        NodeTree<E> remove = findNode(o);
        boolean res = false;
        if (remove != null) {
            res = true;
            removeNode(remove);
        }
        return res;
    }

    void removeNode(NodeTree<E> remove) {
        if (remove.left != null && remove.right != null)
            removeJunction(remove);
        else removeSimpleNode(remove);
    }

    private void removeJunction(NodeTree<E> node) {
        NodeTree<E> substitute = getSubstitute(node.right);
        node.obj = substitute.obj;
        removeSimpleNode(substitute);
    }

    private NodeTree<E> getSubstitute(NodeTree<E> node) {
        NodeTree<E> substitute = node;
        while ((node = node.left) != null) {
            substitute = node;
        }
        return substitute;
    }

    private void removeSimpleNode(NodeTree<E> remove) {
        NodeTree<E> child = remove.left != null ? remove.left : remove.right;
        NodeTree<E> parent = remove.parent;

        if (parent != null) {
            if (parent.left == remove) parent.left = child;
            else parent.right = child;
            if (child != null)
                child.parent = parent;
        } else {
            removeSimpleRoot(child);
        }
        size--;
    }

    private void removeSimpleRoot(NodeTree<E> child) {
        root = child;
        if (child != null)
            child.parent = null;
    }

    private NodeTree<E> findNode(Object o) {
        NodeTree<E> current = root;
        while (current != null && !o.equals(current.obj)) {
            current = comp.compare((E) o, current.obj) < 0 ?
                    current.left : current.right;
        }
        return current;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        //TODO
        for (Object o : c)
            if (!this.contains(o))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        //TODO
        boolean res = false;
        for (Object o : c)
            if (add((E) o))
                res = true;
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        //TODO удалить все элементы, которых нет в коллекции
        if (c.containsAll(this))
            return false;
        removeIf(x -> !c.contains(x));
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        //TODO удалить все элементы, которые есть в коллекции
        boolean res = false;
        if (removeIf(c::contains))
            res = true;
        return res;
    }

    @Override
    public void clear() {
        //TODO
        removeIf(x->true);
    }
}


