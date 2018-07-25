package telran.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Сергей on 22.07.2018.
 */
class TreeTest {
    Set<Integer> set;
    Integer[]expOriginal= {100,80,40,30,20,50,82,90,85,95};
    Integer[]expAdd={100,80,40,30,20,50,82,90,85,95,1000};
    @BeforeEach
    void setUp() throws Exception {
        set=new Tree<>();
        createSet(set,expOriginal);
    }

    private void createSet(Set<Integer> set, Integer[] array) {
        for(Integer number:array) {
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
        testSetArray(set,expOriginal);
    }

    private void testSetArray(Set<Integer> set, Integer[] expected) {
        Integer[]actual=new Integer[expected.length];
        int ind=0;
        for(Integer number:set) {
            actual[ind++]=number;
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
}
