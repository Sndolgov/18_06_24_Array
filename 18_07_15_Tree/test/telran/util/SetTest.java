package telran.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Сергей on 22.07.2018.
 */
class SetTest {
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
    Integer[] expOdd = {85, 95, 81};
    Integer[] expEven = {100, 80, 40, 30, 20, 50, 82, 90};

    @BeforeEach
    void setUp() throws Exception {
        set = new HashTable<>();
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
        Arrays.sort(actual);
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
    void removeIf20() {
        set.removeIf(x -> x % 20 == 0);
        testSetArray(set, expMultiple20);
    }

    @Test
    void removeifAll() {
        set.removeIf(x -> true);
        testSetArray(set, arrEmpty);
    }

    @Test
    void removeIf100() {
        set.removeIf(x -> x == 100);
        testSetArray(set, expNo100);
    }

    @Test
    void removeIf80And82() {
        Integer[] exp = {100, 40, 30, 20, 50, 81, 90, 85, 95};
        set.add(81);
        set.removeIf(x -> x == 80 || x == 82);
        testSetArray(set, exp);
    }

    @Test
    void removeIf80() {
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
    void retainAllU() {
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
    void clearU() {
        set.clear();
        assertEquals(0, set.size());
        testSetArray(set, arrEmpty);
    }

    @Test
    void retainAll() {
        Set<Integer> setOdd = new Tree<>();
        createSet(setOdd, expOdd);
        assertTrue(set.retainAll(setOdd));
        testSetArray(setOdd, expOdd);
        assertFalse(set.retainAll(setOdd));
        set.retainAll(new Tree<>());
        assertEquals(0, set.size());
        testSetArray(set, new Integer[0]);

    }

    @Test
    void removeAllU() {
        Set<Integer> setOdd = new Tree<>();
        createSet(setOdd, expOdd);
        assertTrue(set.removeAll(setOdd));
        testSetArray(set, expEven);
        assertFalse(set.removeAll(setOdd));
    }

    @Test
    void clear() {
        set.clear();
        assertEquals(0, set.size());
        testSetArray(set, new Integer[0]);
    }

    @Test
    void removeIfU() {
        set.removeIf(x -> x % 2 == 0);
        testSetArray(set, expOdd);
        set.removeIf(x -> true);
        assertEquals(0, set.size());
        testSetArray(set, new Integer[0]);
    }

    @Test
    void containsU() {
        assertTrue(set.contains(100));
        assertTrue(set.contains(20));
        assertTrue(set.contains(95));
        assertFalse(set.contains(1000));
    }

    @Test
    void testIteratorU() {
        testSetArray(set, expOriginal);
    }


    @Test
    void testAddU() {
        assertFalse(set.add(40));
        assertTrue(set.add(1000));
        testSetArray(set, expAdd);

    }

    @Test
    void remove80U() {
        set.remove(80);
        testSetArray(set, expNo80);
    }

    @Test
    void remove30U() {
        set.remove(30);
        testSetArray(set, expNo30);
    }

    @Test
    void remove82U() {
        set.remove(82);
        testSetArray(set, expNo82);
    }

    @Test
    void remove20U() {
        set.remove(20);
        testSetArray(set, expNo20);
    }

    @Test
    void remove95U() {
        set.remove(95);
        testSetArray(set, expNo95);
    }

    @Test
    void remove100U() {

        set.remove(100);
        testSetArray(set, expNo100);
    }

    @Test
    void addAll() {
        Set<Integer> setAll = new Tree<>();
        assertTrue(setAll.addAll(set));
        testSetArray(set, expOriginal);
        assertFalse(setAll.addAll(set));
    }
    /*@Test
    void toArray() {
        Object[]actual=set.toArray();
        Arrays.sort(expOriginal);
        assertArrayEquals(expOriginal,actual);
    }*/


}
