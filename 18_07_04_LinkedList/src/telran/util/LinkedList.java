package telran.util;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Сергей on 04.07.2018.
 */
public class LinkedList<E> implements List<E> {

    protected NodeList<E> head;
    protected NodeList<E> tail;
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int index) {
        if (checkIndex(index)) {
            if (index == 0)
                return head.object;
            if (index == size - 1)
                return tail.object;
            else {
                NodeList<E> nodeList = getNode(index);
                return nodeList.object;
            }
        }
        return null;
    }

    @Override
    public boolean add(E e) {
        NodeList<E> newNode = new NodeList<E>(e);
        if (head == null)
            head = tail = newNode;
        else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index >= 0 && index <= size) {
            if (index == size)
                add(element);
            else {
                if (index == 0)
                    addHead(element);
                else {
                    NodeList<E> newElement = new NodeList<>(element);
                    NodeList<E> previous = getPreviousNode(index);
                    newElement.next = previous.next;
                    previous.next = newElement;
                    size++;
                }
            }
        }
    }

    private void addHead(E element) {
        NodeList<E> nodeList = new NodeList<E>(element);
        nodeList.next = head;
        head = nodeList;
        size++;
    }

    private NodeList<E> getNode(int index) {
        NodeList<E> nodeList = head;
        for (int i = 1; i <= index; i++, nodeList = nodeList.next) {
        }
        return nodeList;
    }

    private NodeList<E> getPreviousNode(int index) {
        NodeList<E> nodeList = head;
        for (int i = 1; i < index; i++, nodeList = nodeList.next) {
        }
        return nodeList;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if ((index >= 0 && index <= size) && c != null) {
            boolean isHead = (index == 0 && !c.isEmpty());
            NodeList<E> newNode;
            NodeList<E> previousNode = isHead ? null : getPreviousNode(index);
            NodeList<E> nextNode = isHead ? head : previousNode.next;
            for (E e : c) {
                newNode = new NodeList<>(e);
                if (isHead) {
                    head = newNode;
                    previousNode = newNode;
                    newNode.next = nextNode;
                    isHead = false;
                } else {
                    previousNode.next = newNode;
                    previousNode = newNode;
                    newNode.next = nextNode;
                }
            }
            size += c.size();
            return true;
        }
        return false;
    }

    @Override
    public E remove(int index) {
        if (checkIndex(index)) {
            if (index == 0) {
                /*removeObject = head.object;
                head = head.next;
                size--;
                return removeObject;*/
                return removeFirst();
            }
            if (index == size - 1) {
               /* NodeList<E> previous = getPreviousNode(index);
                removeObject = tail.object;
                previous.next = null;
                tail = previous;
                size--;
                return removeObject;*/
                return removeLast();
            } else {
                /*NodeList<E> previous = getPreviousNode(index);
                NodeList<E> nodeRemove = getNode(index);
                previous.next = nodeRemove.next;
                size--;
                return nodeRemove.object;*/
                return removeAtIndex(index);
            }
        }
        return null;
    }

    private E removeAtIndex(int index) {
        NodeList<E> previous = getPreviousNode(index);
        E removeObject = previous.next.object;
        previous.next = previous.next.next;
        size--;
        return removeObject;
    }

    public E removeFirst() {
        if (head == null)
            return null;
        E removeObject = head.object;
        head = head.next;
        if (head == null)
            tail = null;
        size--;
        return removeObject;
    }

    public E removeLast() {
        if (head == null)
            return null;
        E removeObject = tail.object;
        if (head == tail)
            head = tail = null;
        else {
            tail = getPreviousNode(size - 1);
            tail.next = null;
        }
        size--;
        return removeObject;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isDeleted = false;
        if (c != null && !c.isEmpty()) {
            boolean isHead = true;
            NodeList<E> previous = null;
            for (NodeList<E> node = head; node != null; node = node.next) {
                if (c.contains(node.object)) {
                    isDeleted = true;
                    if (isHead) {
                        head = head.next;
                    } else {
                        previous.next = node.next;
                    }
                    size--;
                } else {
                    isHead = false;
                    previous = node;
                }
            }
        }
        return isDeleted;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isDeleted = false;
        if (c != null) {
            boolean isHead = true;
            NodeList<E> previous = null;
            for (NodeList<E> node = head; node != null; node = node.next) {
                if (!c.contains(node.object)) {
                    isDeleted = true;
                    if (isHead) {
                        head = head.next;
                    } else {
                        previous.next = node.next;
                    }
                    size--;
                } else {
                    isHead = false;
                    previous = node;
                }
            }
        }
        return isDeleted;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            int index = 0;
            for (NodeList<E> node = head; node != null; node = node.next, index++) {
                if (node.object == null)
                    return index;
            }
        } else {
            int index = 0;
            for (NodeList<E> node = head; node != null; node = node.next, index++) {
                if (o.equals(node.object))
                    return index;


            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        if (o == null) {
            int i = 0;
            for (NodeList<E> node = head; node != null; node = node.next, i++) {
                if (node.object == null)
                    index = i;
            }
        } else {
            int i = 0;
            for (NodeList<E> node = head; node != null; node = node.next, i++) {
                if (o.equals(node.object))
                    index = i;
            }
        }
        return index;
    }

    //TODO removeIf
    /*@Override
    public boolean removeIf(Predicate<? super E> filter) {
        boolean isRemoved = false;
        if (filter != null) {
            int sizeReduction = 0;
            NodeList<E> previous = null;
            for (NodeList<E> node = head; node != null; node = node.next) {
                if (filter.test(node.object)) {
                    isRemoved = true;
                    sizeReduction++;
                    if (previous == null)
                        removeHead();
                    else {
                        if (node == tail) {
                            tail = previous;
                            tail.next = null;
                        } else previous.next = node.next;
                    }
                } else previous = node;
            }
            size -= sizeReduction;
        }
        return isRemoved;
    }

    private void removeHead() {
        if (head == tail)
            head = tail = null;
        else head = head.next;
    }*/

    @Override
    public void sort(Comparator<? super E> c) {
        Object[] objects = new Object[size];
        int index = 0;
        for (Object obj : this) {
            objects[index++] = obj;
        }
        Arrays.sort((E[]) objects, c);
        index = 0;
        for (NodeList<E> node = head; node != null; node = node.next, index++)
            node.object = (E) objects[index];
    }

    @Override
    public Object[] toArray() {
        return toArray(new Object[size]);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a != null && a.length >= size) {
            int i = 0;
            for (NodeList<E> nodeList = head; nodeList != null; nodeList = nodeList.next) {
                a[i++] = (T) nodeList.object;
            }
            return a;
        }
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null)
            return false;
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }


    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public E set(int index, E element) {
        if (!checkIndex(index))
            return null;
        NodeList<E> nodeUpdate = getNode(index);
        E old = nodeUpdate.object;
        nodeUpdate.object = element;
        return old;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex <= toIndex && fromIndex >= 0 && toIndex <= size) {
            if (fromIndex == toIndex)
                return new LinkedList<>();
            LinkedList<E> subList = new LinkedList<>();
            NodeList<E> nodeList = getNode(fromIndex);
            subList.addHead(nodeList.object);
            NodeList<E> previous = subList.head;
            for (int i = fromIndex + 1; i < toIndex; i++) {
                nodeList = nodeList.next;
                NodeList<E> node = new NodeList<>(nodeList.object);
                previous.next = node;
                previous = node;
            }
            subList.tail = previous;
            subList.size = toIndex - fromIndex;
            return subList;
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator<E>(this);
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }


    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

    public static void main(String[] args) {
        // java.util.LinkedList<Integer> linkedList = new java.util.LinkedList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        Integer[] integers = new Integer[6];

        List<Integer> arrList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            linkedList.add(i);
            integers[i] = (i + 1) * 10;
            //   arrList.add((i + 1) * 10);
        }

        System.out.println("linkedList: " + linkedList);
        linkedList.remove(0);
        System.out.println("linkedList: " + linkedList.indexOf(null));


    }

}
