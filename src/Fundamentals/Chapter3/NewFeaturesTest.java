package Fundamentals.Chapter3;

import java.lang.reflect.Array;

// 本文件将描述未提到的java新特性
public class NewFeaturesTest {
    public static void main(String[] args) {
        // jdk17新特性：模式匹配
        String[] strings = {"a"};
        Object obj = Array.get(strings, 0);
        if (obj instanceof String s) {  // 模式匹配
            System.out.println(s);
        }
        // switch的模式匹配 - jdk21
        // String s = switch (obj) {
        //     case null -> "";
        //     case Integer i -> String.format("int %d", i);
        //     case Long l -> String.format("long %d", l);
        //     case Double d -> String.format("double %f", d);
        //     case String s -> String.format("String %s", s);
        //     case int[] arr -> "arr";
        //     default -> obj.toString();
        // };

        // jdk15新特性：密封类
        // sealed public class Shape permits Circle, Square, Rectangle {}   只允许...子类
    }
}
