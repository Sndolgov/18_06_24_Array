package telran.util;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Сергей on 18.07.2018.
 */


public class TreeIterator<E> implements Iterator<E> {
    NodeTree<E> current;
    Tree<E> tree;

    public TreeIterator(Tree<E> tree) {
        this.tree = tree;
        current = getLeastNode(tree.root);
    }

    private NodeTree<E> getLeastNode(NodeTree<E> root) {
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
        // TODO
        E e = current.obj;
        if (current.right != null)
            current = getLeastNode(current.right);
        else {
            NodeTree<E> last = current;
            current = current.parent;
            while (current != null && current.left != last) {
                last = current;
                current = current.parent;
            }
        }
        return e;
    }

    public static void main(String[] args) {
        Tree<Integer> set = new Tree<>();
        TreeIterator iterator = new TreeIterator(set);
        iterator.getLeastNode(null);
    }
}

