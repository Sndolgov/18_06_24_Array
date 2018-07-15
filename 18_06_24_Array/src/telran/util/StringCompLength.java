package telran.util;

import java.util.Comparator;

/**
 * Created by Сергей on 27.06.2018.
 */
public class StringCompLength implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return o1.length()-o2.length();
    }
}
