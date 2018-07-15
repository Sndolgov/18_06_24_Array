package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Сергей on 04.07.2018.
 */
class LinkedListTest {
    private List<Integer> emptyLinkedInt;
    private List<Integer> integers;
    private List<String> strings;
    private Integer[] arrExp = {0, 1, 2, 3, null, 4, 5};
    private String[] arrExpString = {"a", "b", "c"};
    private Integer[] arrForCollections = {10, 20, 30, 40, 50, 60};
    private List<Integer> arrListInt = new ArrayList<>(Arrays.asList(arrForCollections));
    private List<Integer> emptyArrListI = new ArrayList<>();
    private List<String> emptyArrListS = new ArrayList<>();


    @BeforeEach
    void setUp() {
        emptyLinkedInt = new LinkedList<>();
        integers = new LinkedList<>();
        strings = new LinkedList<>();
        fillList(integers, arrExp);
        fillList(strings, arrExpString);
    }

    private <T> void fillList(List<T> list, T[] arr) {
        for (int i = 0; i < arr.length; i++)
            list.add(arr[i]);
    }

    private <T> void compareToArray(T[] arrExp, List<T> list) {
        Object[] actual = new Object[list.size()];
        list.toArray(actual);
        assertArrayEquals(arrExp, actual);
    }

    @Test
    void size() {
        assertEquals(0, emptyLinkedInt.size());
        assertEquals(7, integers.size());
        integers.add(6);
        assertEquals(8, integers.size());
        assertEquals(3, strings.size());
    }


    @Test
    void isEmpty() {
    }

    @Test
    void contains() {
    }

    @Test
    void iterator() {
    }


    @Test
    void toArrayObject() {
        Object[] i = integers.toArray();
        Object[] s = strings.toArray();
        assertArrayEquals(arrExp, i);
        assertArrayEquals(arrExpString, s);
    }

    @Test
    void toArray() {
        Integer[] i = new Integer[integers.size()];
        String[] s = new String[strings.size()];
        integers.toArray(i);
        strings.toArray(s);
        assertArrayEquals(arrExp, i);
        assertArrayEquals(arrExpString, s);
    }

    @Test
    void add() {
        assertEquals(0, emptyLinkedInt.size());
        emptyLinkedInt.add(10);
        emptyLinkedInt.add(20);
        emptyLinkedInt.add(30);
        assertEquals(3, emptyLinkedInt.size());
        assertEquals(10, (int) emptyLinkedInt.get(0));
        assertEquals(20, (int) emptyLinkedInt.get(1));
        assertEquals(30, (int) emptyLinkedInt.get(2));
        strings.add(1, "bbb");
        assertEquals("bbb", strings.get(1));
    }

    @Test
    void addIndex() {
        Integer[] arrExp = {10, 0, 1, 2, 3, null, 4, 5, 19, 22};
        String[] arrExpString = {null, "a", "b", "c"};
        assertEquals(7, integers.size());
        assertEquals(0, (int) integers.get(0));
        integers.add(0, 10);
        assertEquals(10, (int) integers.get(0));
        assertEquals(0, (int) integers.get(1));
        assertEquals(8, integers.size());
        integers.add(integers.size(), 22);
        assertEquals(9, integers.size());
        assertEquals(22, (int) integers.get(integers.size() - 1));
        integers.add(integers.size() - 1, 19);
        assertEquals(19, (int) integers.get(integers.size() - 2));
        assertEquals(22, (int) integers.get(integers.size() - 1));
        compareToArray(arrExp, integers);
        strings.add(0, null);
        compareToArray(arrExpString, strings);
    }

    @Test
    void remove() {
        Integer[] arrExp = {1, null, 2, 3, null, 4, 5};
        Integer[] arrExp1 = {1, 2, 3, null, 5};
        Integer[] arrExp2 = {10};
        Integer[] arrExp3 = {};

        integers.add(2, null);
        assertEquals(0, (int) integers.remove(0));
        compareToArray(arrExp, integers);
        assertNull(integers.remove(1));
        assertEquals(4, (int) integers.remove(4));
        compareToArray(arrExp1, integers);
        integers.clear();
        fillList(integers, arrExp2);
        assertEquals(10, (int) integers.remove(0));
        compareToArray(arrExp3, integers);
    }

    @Test
    void containsAll() {
        assertFalse(emptyLinkedInt.containsAll(arrListInt));
        assertTrue(integers.containsAll(emptyArrListI));
        emptyArrListI.add(null);
        assertTrue(integers.containsAll(emptyArrListI));
        emptyArrListI.add(3);
        assertTrue(integers.containsAll(emptyArrListI));
        emptyArrListI.add(55);
        assertFalse(integers.containsAll(emptyArrListI));
        emptyArrListS.add("xxx");
        assertFalse(integers.containsAll(emptyArrListS));
    }

    @Test
    void addAll() {
        Integer[] arrExp = {0, 1, 2, 3, null, 4, 5, 10, 20, 30, 40, 50, 60};
        assertTrue(integers.addAll(arrListInt));
        compareToArray(arrExp, integers);
    }

    @Test
    void addAllIndex() {
        Integer[] arrExp = {0, 1, 10, 20, 30, 40, 50, 60, 2, 3, null, 4, 5};
        Integer[] arrExp2 = {10, 20, 30, 40, 50, 60, 0, 1, 2, 3, null, 4, 5};
        assertTrue(integers.addAll(2, arrListInt));
        compareToArray(arrExp, integers);
        assertTrue(strings.addAll(1, emptyArrListS));
        compareToArray(arrExpString, strings);
        assertFalse(integers.addAll(4, null));
        assertFalse(integers.addAll(-1, arrListInt));
        integers.clear();
        assertEquals(0, integers.size());
        fillList(integers, this.arrExp);
        assertTrue(integers.addAll(0, arrListInt));
        compareToArray(arrExp2, integers);
        assertTrue(integers.addAll(0, emptyArrListI));
    }

    @Test
    void removeAll() {
        Integer[] arrExp = {2, 3, null, 4};
        Integer[] arrForDelete = {0, 1, 5, 11};
        fillList(emptyArrListI, arrForDelete);
        integers.add(0);
        integers.add(1);
        assertTrue(integers.removeAll(emptyArrListI));
        assertEquals(4, integers.size());
        assertFalse(integers.removeAll(emptyArrListI));
        compareToArray(arrExp, integers);
    }

    @Test
    void retainAll() {
        Integer[] arrExp1 = {2, null};
        emptyArrListI.add(2);
        emptyArrListI.add(null);
        assertTrue(integers.retainAll(emptyArrListI));
        compareToArray(arrExp1, integers);
        emptyArrListI.clear();
        assertTrue(integers.retainAll(emptyArrListI));
        compareToArray(new Integer[0], integers);
         integers.clear();
        fillList(integers, arrExp);
        fillList(emptyArrListI, arrExp);
        assertFalse(integers.retainAll(emptyArrListI));
        compareToArray(arrExp, integers);
    }



    @Test
    void clear() {
        integers.clear();
        strings.clear();
        assertEquals(0, integers.size());
        assertEquals(0, strings.size());
        assertNull(integers.get(0));
        assertNull(strings.get(0));
        integers.add(1);
        strings.add("a");
        assertEquals(1, (int) integers.get(0));
        assertEquals("a", strings.get(0));
    }

    @Test
    void get() {
        assertEquals((Integer) 0, integers.get(0));
        assertEquals((Integer) 1, integers.get(1));
        assertEquals((Integer) 2, integers.get(2));
        assertEquals((Integer) 3, integers.get(3));
        assertEquals((Integer) 4, integers.get(5));
        assertEquals((Integer) 5, integers.get(integers.size()-1));
        assertNull(integers.get(4));
        assertNull(integers.get(integers.size()));
        assertNull(integers.get(-1));
    }

    @Test
    void set() {
        Integer[] arrExp = {10, 1, 12, 3, 77, 4, 15};
        assertNull(integers.set(-1, 33));
        assertNull(integers.set(integers.size(), 33));
        assertEquals((Integer) 0, integers.set(0, 10));
        assertEquals((Integer) 2, integers.set(2, 12));
        assertNull(integers.set(4, 77));
        assertEquals((Integer) 5, integers.set(integers.size() - 1, 15));
        compareToArray(arrExp, integers);
    }


    @Test
    void removeObject() {
        Integer[] arrExp = {1, 2, 3, null, 4};
        String[] arrExpString = {"a", "c", null};
        integers.add(2, null);
        strings.add(0, null);
        strings.add(strings.size(), null);
        integers.remove(new Integer(0));
        integers.remove(null);
        integers.remove(new Integer(5));
        strings.remove("b");
        strings.remove(null);
        compareToArray(arrExp, integers);
        compareToArray(arrExpString, strings);
    }

    @Test
    void indexOf() {
        integers.add(2, null);
        integers.add(2);
        integers.add(3);
        assertEquals(2, integers.indexOf(null));
        assertEquals(3, integers.indexOf(2));
        assertEquals(-1, integers.lastIndexOf(30));
    }

    @Test
    void lastIndexOf() {
        integers.add(2, null);
        integers.add(2);
        integers.add(3);
        integers.add(null);
        assertEquals(10, integers.lastIndexOf(null));
        assertEquals(8, integers.lastIndexOf(2));
        assertEquals(9, integers.lastIndexOf(3));
        assertEquals(-1, integers.lastIndexOf(30));
    }

    @Test
    void listIterator() {
    }

    @Test
    void listIterator1() {
    }

    @Test
    void subList() {
        Integer[] arr1 = {0, 1, 2, 3, null, 4, 5};
        Integer[] arr2 = {0, 1, 2, 3};
        Integer[] arr3 = {3, null, 4, 5};
        Integer[] arr4 = {2};
        List<Integer> listExp1 = new LinkedList<>();
        List<Integer> listExp2 = new LinkedList<>();
        List<Integer> listExp3 = new LinkedList<>();
        List<Integer> listExp4 = new LinkedList<>();
        List<Integer> listExp5 = new LinkedList<>();
        fillList(listExp1, arr1);
        fillList(listExp2, arr2);
        fillList(listExp3, arr3);
        fillList(listExp4, arr4);
        assertArrayEquals(listExp1.toArray(), integers.subList(0, integers.size()).toArray());
        assertArrayEquals(listExp2.toArray(), integers.subList(0, 4).toArray());
        assertArrayEquals(listExp3.toArray(), integers.subList(3, integers.size()).toArray());
        assertArrayEquals(listExp4.toArray(), integers.subList(2, 3).toArray());
        assertArrayEquals(listExp5.toArray(), integers.subList(2, 2).toArray());
        assertNull(integers.subList(8, 8));
    }

   /* private void print(List<?> list) {
        for (int i = 0; i < list.size(); i++)
            System.out.print(list.get(i) + " ");
        System.out.println();
    }*/

}