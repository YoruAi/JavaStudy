package Fundamentals.Chapter5;

import java.util.ArrayList;

public class ArrayListTest {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        // var arr = new ArrayList<Integer>(100);
        arr.ensureCapacity(10); // 保证空间，但并不存在
        arr.size();
        arr.add(2); // 添加元素
        arr.add(0, 1);  // 按位置添加元素
        var x = arr.remove(0);
        arr.trimToSize();   // 削减存储大小

        // 使用方法 1
        arr.get(0);
        arr.set(0, 3);
        for (var e : arr) {
            System.out.println(e);
        }
        // 使用方法 2
        var a = new Integer[arr.size()];
        arr.toArray(a);
    }
}
