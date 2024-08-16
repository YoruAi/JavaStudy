package Fundamentals.Chapter4;

import java.util.Scanner;

public class ObjectsNullTest {
    public static void main(String[] args) throws NullPointerException {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        if (str.isEmpty()) str = null;

        // null处理
        str = java.util.Objects.requireNonNullElse(str, "unknown");
        System.out.println(str);
        java.util.Objects.requireNonNull(str, "Cannot be null!");
        System.out.println(str);
    }
}
