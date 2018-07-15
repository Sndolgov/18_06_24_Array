package telran.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Сергей on 24.06.2018.
 */
public class ArrayTest {
    List<Integer> array;
    List<String> strings;
    Integer[] aeExpl = {3, -10, 20, 100, 80};
    Integer[] arForCompI = {3, -10, 20, 100, 80, 13, 150, 98, 24};
    Integer[] aeExpEvenOdd = {-10, 20, 24, 80, 98, 100, 150, 13, 3};
    String[] aeExpStr = {"abc", "ab", "lmn", "123"};
    String[] arForCompS = {"abc", "asdsab", "mn", "1"};
    String[] aeExpStrLenght = {"1", "mn", "abc", "asdsab"};


    @BeforeEach
    void setUp() {
        array = new Array<>();
        strings = new Array<>();
        fillarray(aeExpl, array);
        fillarray(aeExpStr, strings);
    }

    @Test
    void addGet() {

        Object[] actual = getArray(array);
        assertArrayEquals(aeExpl, actual);
        Object[] actualStr = getArray(strings);
        assertArrayEquals(aeExpStr, actualStr);
    }

    @Test
    void addIndex() {
        array.add(2, 11);
        Integer[] aeExpl = {3, -10, 11, 20, 100, 80};
        Object[] actual = getArray(array);
        assertArrayEquals(aeExpl, actual);
        strings.add(0, "hello");
        String[] aeExpStr = {"hello", "abc", "ab", "lmn", "123"};
        Object[] actualStr = getArray(strings);
        assertArrayEquals(aeExpStr, actualStr);
        strings.add(10, "hello");
    }

    @Test
    void removeIndex() {
        array.remove(2);
        Integer[] aeExpl = {3, -10, 100, 80};
        Object[] actual = getArray(array);
        assertArrayEquals(aeExpl, actual);
        strings.remove(1);
        String[] aeExpStr = {"abc", "lmn", "123"};
        Object[] actualStr = getArray(strings);
        assertArrayEquals(aeExpStr, actualStr);
        strings.remove(10);

    }

    @Test
    void removeAddIndex() {
        array.add(0, 1);
        array.add(3, 2);
        array.add(7, 3);
        array.remove(0);
        array.remove(2);
        array.remove(5);
        Object[] actual = getArray(array);
        assertArrayEquals(aeExpl, actual);
        strings.add(0, "a");
        strings.add(2, "b");
        strings.add(6, "c");
        strings.remove(0);
        strings.remove(1);
        strings.remove(4);
        Object[] actualStr = getArray(strings);
        assertArrayEquals(aeExpStr, actualStr);
    }

    @Test
    void shuffle() {
        if (array instanceof Array) {
            Array<Integer> array = (Array) this.array;
            Array<String> strings = (Array) this.strings;
            array.shuffle();
            strings.shuffle();
            Object[] actual = getArray(array);
            Object[] actualStr = getArray(strings);
            testAfterShuffle(aeExpl, actual);
            testAfterShuffle(aeExpStr, actualStr);
        }
    }

    @Test
    void indexOf() {
        assertEquals(1, strings.indexOf(new String("ab")));
        strings.add(1, "ab");
        assertEquals(2, strings.lastIndexOf("ab"));
        assertEquals(-1, strings.indexOf("xxx"));
        assertEquals(0, array.indexOf(3));
    }

    @Test
    void sort() {
        array.sort(Comparator.naturalOrder());
        strings.sort(Comparator.naturalOrder());
        Object[] actualI = getArray(array);
        Object[] actualS = getArray(strings);
        Arrays.sort(aeExpl);
        Arrays.sort(aeExpStr);
        assertArrayEquals(aeExpl, actualI);
        assertArrayEquals(aeExpStr, actualS);
    }

    @Test
    void sortComporator() {
        Array<String> stringsComp = new Array<>();
        fillarray(arForCompS, stringsComp);
        //   stringsComp.sort(new StringCompLength());

        //Lambda Expression
        stringsComp.sort((o1, o2) -> o1.length() - o2.length());

        Object[] actual = getArray(stringsComp);
        assertArrayEquals(aeExpStrLenght, actual);
        Array<Integer> intComp = new Array<>();
        fillarray(arForCompI, intComp);
        //  intComp.sort(new NumbersCompEvenOdd());

        //Function Clouser
        /*intComp.sort((o1, o2) -> {
            if (o1 % 2 != 0)
                return o2 % 2 == 0 ? 1 : o2 - o1;
            return o1 % 2 == 0 && o2 % 2 == 0 ? o1 - o2 : 0;
        });*/

        //Lambda Expression
        intComp.sort((o1, o2) -> compEvenOdd(o1, o2));

        //Method reference
        intComp.sort(this::compEvenOdd);

        actual = getArray(intComp);
        assertArrayEquals(aeExpEvenOdd, actual);
    }

    @Test
    void removeObject() {
        Integer[] arrExpDelete = {3, -10, 100, 80};
        assertTrue(array.remove(new Integer(20)));
        assertFalse(array.remove(new Integer(20)));
        Object[] actual = getArray(array);
        assertArrayEquals(arrExpDelete, actual);

        String[] aeExpStrDelete = {"abc", "lmn", "123"};
        assertTrue(strings.remove("ab"));
        assertFalse(strings.remove("ab"));
        actual = getArray(strings);
        assertArrayEquals(aeExpStrDelete, actual);
    }

    @Test
    void removeIf() {
        Integer[] arrExpDelete = {3, -10};
        Integer[] arrExpDelete2 = {};
        assertTrue(array.removeIf(o1 -> o1 % 20 == 0));
        assertFalse(array.removeIf(o1 -> o1 % 17 == 0));
        Object[] actual = getArray(array);
        assertArrayEquals(arrExpDelete, actual);
        assertEquals(2, array.size());
        assertTrue(array.removeIf(o1 -> o1 < 5));
        actual = getArray(array);
        assertArrayEquals(arrExpDelete2, actual);

        String[] aeExpStrDelete = {"lmn", "123"};
        assertTrue(strings.removeIf(o1 -> o1.contains("a")));
        assertFalse(strings.removeIf(o1 -> o1.length() > 10));
        actual = getArray(strings);
        assertArrayEquals(aeExpStrDelete, actual);
        assertEquals(2, strings.size());
    }

    @Test
    void indexOfPredicate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (array instanceof Array) {
            Array<Integer> array = (Array) this.array;
            Array<String> strings = (Array) this.strings;
            assertEquals(1, array.indexOf(o1 -> o1 < 0));
            assertEquals(0, array.indexOf(o1 -> o1 % 3 == 0));
            assertEquals(-1, array.indexOf(o1 -> o1 > 150));
            assertEquals(1, strings.indexOf(o1 -> o1.length() < 3));
            assertEquals(-1, strings.indexOf(o1 -> o1.length() < 2));
            assertEquals(3, strings.indexOf(o1 -> o1.contains("1")));
        }
    }


    @Test
    void indexOfLastPredicate() {
        if (array instanceof Array) {
            Array<Integer> array = (Array) this.array;
            Array<String> strings = (Array) this.strings;
            assertEquals(4, array.indexOfLast(o1 -> o1 % 10 == 0));
            assertEquals(2, array.indexOfLast(o1 -> o1 % 2 == 0 && o1 < 50));
            assertEquals(-1, array.indexOfLast(o1 -> o1 > 150));
            assertEquals(3, strings.indexOfLast(o1 -> o1.length() == 3));
            assertEquals(-1, strings.indexOfLast(o1 -> o1.length() < 2));
            assertEquals(1, strings.indexOfLast(o1 -> o1.contains("a")));
        }
    }

    int compEvenOdd(Integer o1, Integer o2) {
        if (o1 % 2 != 0)
            return o2 % 2 == 0 ? 1 : o2 - o1;
        return o1 % 2 == 0 && o2 % 2 == 0 ? o1 - o2 : 0;
    }

    private <T> T[] getArray(List<T> array) {
        Object[] res = new Object[array.size()];
        int index = 0;
        for (Object ob : array)
            res[index++] = ob;
        return (T[]) res;
    }

    private <T> void fillarray(T[] array, List<T> arrayOb) {
        for (int i = 0; i < array.length; i++)
            arrayOb.add(array[i]);
    }

    private void testAfterShuffle(Object[] objects, Object[] objectsSh) {
        testSameNumbers(objects, objectsSh);
        assertFalse(Arrays.equals(objects, objectsSh));
    }

    private void testSameNumbers(Object[] objects, Object[] objectsSh) {
        Object[] ar1Sorted = Arrays.copyOf(objects, objects.length);
        Object[] ar2Sorted = Arrays.copyOf(objectsSh, objectsSh.length);
        Arrays.sort(ar1Sorted);
        Arrays.sort(ar2Sorted);
        assertArrayEquals(ar1Sorted, ar2Sorted);
    }

    /*private void printAr(Object[] object) {
        System.out.print(object.getClass().getSimpleName() + ": ");
        for (Object o : object)
            System.out.print(o + " ");
        System.out.println();
    }*/
}

