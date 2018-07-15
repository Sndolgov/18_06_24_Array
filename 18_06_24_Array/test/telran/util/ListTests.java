package telran.util;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.*;

class ListTests {
    List<Integer> numbers;
    List<String> strings;
    Integer []arExp1= {3,-10,20,100,80,13,150,98,24};
    Integer[]arExpEvenOdd= {-10,20,24,80,98,100,150,13,3};
    Integer[]arNo3={-10,20,100,80,13,150,98,24};
    Integer[]arNo3_24={-10,20,100,80,13,150,98};
    Integer[]arExpOdd= {3,13};
    Integer[]arWith1000At0=
            {1000,3,-10,20,100,80,13,150,98,24};
    Integer[]arWith1000At0andEnd=
            {1000,3,-10,20,100,80,13,150,98,24,1000};
    String []arExpStr= {"abcd","bb","lmn","123456"};
    String[]arNoLmn={"abcd","bb","123456"};
    String[]arExpLenGT4= {"123456"};//string length greater than 4
    String[]arWith2LmnsAt2=
            {"abcd","bb","lmn","lmn","123456"};
    String[]arExpStrLength= {"bb","lmn","abcd","123456"};

    @BeforeEach
    public void setUp() throws Exception {
        numbers=new Array<Integer>();
        strings=new Array<String>();
        fillList(arExp1,numbers);
        fillList(arExpStr,strings);
    }

    private <T>void fillList(T[] array, List<T> list) {
        for(int i=0;i<array.length;i++)
            list.add(array[i]);

    }

    @Test
    void addGet() {
        Object actual[]=getArray(numbers);
        assertArrayEquals(arExp1, actual);
        Object actualStr[]=getArray(strings);
        assertArrayEquals(arExpStr, actualStr);
    }

    private <T> Object[] getArray(List<T> list) {
        Object[]res=new Object[list.size()];
        int ind=0;
//		for(Object obj:list) {
//			res[ind++]=obj;
//		}
        //java
        Iterator<T> it=list.iterator();
        while (it.hasNext()) {
            res[ind++]=it.next();
        }

        return res;
    }

    @Test
    public void removeAtIndex() {
        numbers.remove(0);
        Object []actual=getArray(numbers);
        assertArrayEquals(arNo3,actual);
        numbers.remove(numbers.size()-1);
        actual=getArray(numbers);
        assertArrayEquals(arNo3_24,actual);
        strings.remove(2);
        actual=getArray(strings);
        assertArrayEquals(arNoLmn,actual);
    }
    @Test
    public void addAtIndex() {
        numbers.add(0,1000);
        Object []actual=getArray(numbers);
        assertArrayEquals(arWith1000At0,actual);
        numbers.add(numbers.size(),1000);
        actual=getArray(numbers);
        assertArrayEquals(arWith1000At0andEnd,actual);
        strings.add(2, "lmn");
        actual=getArray(strings);
        assertArrayEquals(arWith2LmnsAt2,actual);
//		array.add("abcd");
//		strings.add(123);
    }
    @Test
    public void indexOf() {
        assertEquals(2,strings.indexOf(
                new String("lmn")));
        strings.add(2,"lmn");
        assertEquals(3,strings.lastIndexOf("lmn"));
        assertEquals(-1,strings.indexOf("aaaaa"));
        assertEquals(-1,strings.lastIndexOf("aaaaa"));
        assertEquals(0,numbers.indexOf(3));
        assertEquals(1,numbers.indexOf(-10));
        assertEquals(6,numbers.indexOf(150));
    }

    @Test
    public void sortComparator() {
        strings.sort((o1,o2)->o1.length()-o2.length());

        Object[]actual=getArray(strings);
        assertArrayEquals(arExpStrLength,actual);

        numbers.sort(this::compEvenOdd);
        actual=getArray(numbers);
        assertArrayEquals(arExpEvenOdd,actual);
    }
    int compEvenOdd(Integer x,Integer y) {
        int r1=x%2;
        int r2=y%2;
        int res=r1-r2;
        if(r1==0&&r2==0)
            res=x-y;
        else if(r1!=0&&r2!=0)
            res=y-x;
        return res;
    }
    @Test
    public void removeObject() {
        numbers.remove((Integer)3);
        Object []actual=getArray(numbers);
        assertArrayEquals(arNo3,actual);
        numbers.remove((Integer)24);
        actual=getArray(numbers);
        assertArrayEquals(arNo3_24,actual);
        strings.remove("lmn");
        actual=getArray(strings);
        assertArrayEquals(arNoLmn,actual);
    }
    @Test
    public void removeIf() {
        numbers.removeIf(x->x%2==0);
        strings.removeIf(x->x.length()<5);
        Object []actual=null;
        actual=getArray(numbers);
        assertArrayEquals(arExpOdd, actual);
        actual=getArray(strings);
        assertArrayEquals(arExpLenGT4,actual);
        numbers.removeIf(x->true);
        assertEquals(0,numbers.size());
    }
    @Test
    public void sort() {
        numbers.sort(Comparator.naturalOrder());
        strings.sort(Comparator.naturalOrder());
        Object[]actualI=getArray(numbers);
        Object[]actualS=getArray(strings);
        Arrays.sort(arExp1);
        Arrays.sort(arExpStr);
        assertArrayEquals(arExp1,actualI);
        assertArrayEquals(arExpStr,actualS);
    }
}

