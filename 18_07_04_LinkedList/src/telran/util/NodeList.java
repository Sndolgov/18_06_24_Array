package telran.util;

/**
 * Created by Сергей on 04.07.2018.
 */
class NodeList<E> {
    public E object;
    public NodeList <E> next;

    public NodeList(E object) {
        this.object = object;
    }
}
