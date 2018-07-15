package telran.util;

import java.util.Comparator;

/**
 * Created by Сергей on 27.06.2018.
 */
public class NumbersCompEvenOdd implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 % 2 != 0)
            return o2 % 2 == 0 ? 1 : o2 - o1;
        return o1 % 2 == 0 && o2 % 2 == 0 ? o1 - o2 : 0;
    }
}
