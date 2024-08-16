package Fundamentals.Chapter5;

import java.lang.reflect.Array;

import static java.lang.Math.min;

public class ArrayCopyOfTest {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int[] newArr = (int[]) CopyOf(arr, 10);

        // 用于数组类型为Object未知时使用
        Array.getInt(newArr, 0);    // get返回对象，getXxx返回基本类型
        Array.setInt(newArr, 5, 6);
    }

    public static Object CopyOf(Object arr, int newLength) {
        Class cl = arr.getClass();
        if (!cl.isArray()) return null;

        Class componentType = cl.getComponentType();
        int length = Array.getLength(arr);

        Object newArr = Array.newInstance(componentType, newLength);    // 由原元素类型构造一个新数组对象
        System.arraycopy(arr, 0, newArr, 0, min(length, newLength));   // 复制
        return newArr;
    }
}
