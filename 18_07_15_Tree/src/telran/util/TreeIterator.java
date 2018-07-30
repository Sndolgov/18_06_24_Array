package telran.util;

import java.util.Iterator;

/**
 * Created by Сергей on 18.07.2018.
 */


public class TreeIterator<E> implements Iterator<E> {
    NodeTree<E> current;
    NodeTree<E> previous;
    Tree<E> tree;

    public TreeIterator(Tree<E> tree) {
        this.tree = tree;
        current = getLeastNode(tree.root);
    }

    NodeTree<E> getLeastNode(NodeTree<E> root) {
        NodeTree<E> nodeLeft = root;
        if (root != null) {
            while ((root = root.left) != null)
                nodeLeft = root;
        }
        return nodeLeft;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public E next() {
        E e = current.obj;
        previous = current;
        if (current.right != null)
            current = getLeastNode(current.right);
        else {
            current = getNextNodeParent(current);
        }
        return e;
    }

    private NodeTree<E> getNextNodeParent(NodeTree<E> current) {
        NodeTree<E> node = current;
        while ((current = current.parent) != null && current.left != node) {
            node = current;
        }
        return current;
    }

    @Override
    public void remove() {
        tree.removeNode(previous);
        if (previous.right != null)
            current = previous.left != null ? previous : getLeastNode(previous.right);
    }

}

