package telran.util;

/**
 * Created by Сергей on 15.07.2018.
 */
class NodeTree<E> {
    E obj;
    NodeTree<E> parent;
    NodeTree<E> left;
    NodeTree<E> right;

    public NodeTree(E obj) {
        this.obj = obj;
    }
}
