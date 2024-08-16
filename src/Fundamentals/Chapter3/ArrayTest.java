package Fundamentals.Chapter3;

import java.util.Arrays;

public class ArrayTest {
    public static void main(String[] args) {
        // 初始化
        int[] arr = new int[100];
        arr = new int[]{2, 3, 5, 7};
        System.out.println(arr.length);

        // 数组API
        int[] copy = Arrays.copyOf(arr, 2 * arr.length);
        Arrays.sort(arr);
        int index = Arrays.binarySearch(arr, 0, arr.length, 3);
        Arrays.toString(arr);
        Arrays.fill(arr, 4);
        Arrays.equals(arr, new int[]{4, 4, 4, 4});

        // 多维数组
        int[][] deepArr = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        int[][] odds = new int[4][];
        for (int i = 0; i < 4; ++i) {
            odds[i] = new int[i + 1];
        }
        Arrays.deepToString(deepArr);
        Arrays.deepEquals(deepArr, odds);
    }
}
