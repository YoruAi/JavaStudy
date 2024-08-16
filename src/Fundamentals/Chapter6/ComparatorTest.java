package Fundamentals.Chapter6;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorTest {
    public static void main(String[] args) {
        String[] strings = {"abcd", "abcde", "abc"};
        Arrays.sort(strings, new LengthComparator());
        for (String s : strings) System.out.println(s);
    }
}

class LengthComparator implements Comparator<String> {
    @Override
    public int compare(String first, String second) {   // 注意不是静态方法，使用时需要构造实例
        return first.length() - second.length();
    }
}