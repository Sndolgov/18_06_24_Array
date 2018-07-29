package telran.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Сергей on 22.07.2018.
 */
class TreeTest {
    Set<Integer> set;
    Integer[] expOriginal = {100, 80, 40, 30, 20, 50, 82, 90, 85, 95, 81};
    Integer[] expAdd = {100, 80, 40, 30, 20, 50, 82, 90, 85, 95, 1000, 81};
    Integer[] expNo100 = {80, 40, 30, 20, 50, 82, 90, 85, 95, 81};
    Integer[] expNo80 = {100, 40, 30, 20, 50, 82, 90, 85, 95, 81};
    Integer[] expNo30 = {100, 80, 40, 20, 50, 82, 90, 85, 95, 81};
    Integer[] expNo82 = {100, 80, 40, 30, 20, 50, 90, 85, 95, 81};
    Integer[] expNo20 = {100, 80, 40, 30, 50, 82, 90, 85, 95, 81};
    Integer[] expNo95 = {100, 80, 40, 30, 20, 50, 82, 90, 85, 81};
    Integer[] expMultiple20 = {30, 50, 82, 90, 85, 95, 81};
    Integer[] numbersMultiple20 = {100, 80, 40, 20};
    Integer[] arrEmpty = new Integer[0];
    Integer[] expAddNumbers = {100, 80, 40, 30, 20, 50, 82, 90, 85, 95, 1, 2, 3, 180, 900, 81};


    @BeforeEach
    void setUp() throws Exception {
        set = new Tree<>();
        createSet(set, expOriginal);
    }

    private void createSet(Set<Integer> set, Integer[] array) {
        for (Integer number : array) {
            set.add(number);
        }
    }

    @Test
    void contains() {
        assertTrue(set.contains(100));
        assertTrue(set.contains(20));
        assertTrue(set.contains(95));
        assertFalse(set.contains(1000));
    }

    @Test
    void testIterator() {
        testSetArray(set, expOriginal);
    }

    private void testSetArray(Set<Integer> set, Integer[] expected) {
        Integer[] actual = new Integer[expected.length];
        int ind = 0;
        for (Integer number : set) {
            actual[ind++] = number;
        }
        Arrays.sort(expected);
        //  Arrays.sort(actual);
        assertArrayEquals(expected, actual);

    }

    @Test
    void testAdd() {
        assertFalse(set.add(40));
        assertTrue(set.add(1000));
        testSetArray(set, expAdd);
    }

    @Test
    void remove100() {
        set.remove(100);
        testSetArray(set, expNo100);
    }

    @Test
    void remove80() {
        set.remove(80);
        testSetArray(set, expNo80);
    }

    @Test
    void remove30() {
        set.remove(30);
        testSetArray(set, expNo30);
    }

    @Test
    void remove82() {
        set.remove(82);
        testSetArray(set, expNo82);
    }

    @Test
    void remove20() {
        set.remove(20);
        testSetArray(set, expNo20);
    }

    @Test
    void remove95() {
        set.remove(95);
        testSetArray(set, expNo95);
    }

    @Test
    void removeif20() {
        set.removeIf(x -> x % 20 == 0);
        testSetArray(set, expMultiple20);
    }

    @Test
    void removeifAll() {
        set.removeIf(x -> true);
        testSetArray(set, arrEmpty);
    }

    @Test
    void removeif100() {
        set.removeIf(x -> x == 100);
        testSetArray(set, expNo100);
    }

    @Test
    void removeif80And82() {
        Integer[] exp = {100, 40, 30, 20, 50, 81, 90, 85, 95};
        set.add(81);
        set.removeIf(x -> x == 80 || x == 82);
        testSetArray(set, exp);
    }

    @Test
    void removeif80() {
        set.removeIf(x -> x == 80);
        testSetArray(set, expNo80);
    }

    @Test
    void containsAll() {
        List<Integer> list1 = Arrays.asList(expMultiple20);
        List<Integer> list2 = Arrays.asList(arrEmpty);
        List<Integer> list3 = Arrays.asList(expAdd);
        assertTrue(set.containsAll(list1));
        assertTrue(set.containsAll(list2));
        assertFalse(set.containsAll(list3));

    }

    @Test
    void addAll1() {
        List<Integer> list = Arrays.asList(expAdd);
        assertTrue(set.addAll(list));
        testSetArray(set, expAdd);
    }

    @Test
    void addAll2() {
        Integer[] addNumbers = {1, 2, 3, 180, 900, 80, 100};
        List<Integer> list = Arrays.asList(expMultiple20);
        List<Integer> list2 = Arrays.asList(addNumbers);
        assertFalse(set.addAll(list));
        testSetArray(set, expOriginal);
        assertTrue(set.addAll(list2));
        testSetArray(set, expAddNumbers);
    }

    @Test
    void retainAll20() {
        List<Integer> list1 = Arrays.asList(expOriginal);
        List<Integer> list2 = Arrays.asList(expMultiple20);
        assertFalse(set.retainAll(list1));
        assertTrue(set.retainAll(list2));
        testSetArray(set, expMultiple20);
    }

    @Test
    void retainAll() {
        List<Integer> list = Arrays.asList(arrEmpty);
        assertTrue(set.retainAll(list));
        testSetArray(set, arrEmpty);
    }

    @Test
    void removeAll() {
        Integer[] arr = {1, 2, 3, 180, 900};
        List<Integer> list1 = Arrays.asList(arr);
        List<Integer> list2 = Arrays.asList(numbersMultiple20);
        List<Integer> list3 = Arrays.asList(expOriginal);
        assertFalse(set.removeAll(list1));
        assertTrue(set.removeAll(list2));
        testSetArray(set, expMultiple20);
        assertTrue(set.removeAll(list3));
        testSetArray(set, arrEmpty);
    }

    @Test
    void clear() {
        set.clear();
        assertEquals(0, set.size());
        testSetArray(set, arrEmpty);
    }

}
