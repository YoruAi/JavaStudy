package Fundamentals.Chapter9;

import java.util.*;

public class AlgorithmTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "", "");
        Collections.fill(list, "x");
        list.addAll(1, List.of(new String[]{"aaa", "c", "bb", "c"}));

        Collections.max(list);  // 最大最小值
        Collections.sort(list); // 排序（使用List接口 可set修改）
        Collections.frequency(list, "c");
        Collections.lastIndexOfSubList(list, List.of("x", "x"));    // 子列表索引/indexOfSubList,不存在返回-1
        Collections.replaceAll(list, "c", "d");
        Collections.swap(list, 0, 1);
        Collections.shuffle(list);
        Collections.copy(list.subList(0, 2), List.of("y", "z"));    // 将后者复制到前者对应位置
        Collections.reverse(list);
        Collections.rotate(list, 2);    // 向后循环移动distance位

        list.sort(null);        // 排序（List接口内） - 非原地排序
        list.sort(Collections.reverseOrder());    // 使用compareTo的逆序 = Comparator.reverseOrder()
        list.sort(Comparator.comparingInt(String::length).reversed());

        Collections.binarySearch(list, "d"); // 如果不存在则返回负值i,且应在-i-1的位置。提供非随机访问则退化为线性查找。

        // 集合转化为数组
        String[] strings;
        strings = list.toArray(new String[0]);
        strings = list.toArray(String[]::new);


        // 其他 //
        Enumeration<String> enumeration = Collections.enumeration(list);    // 枚举集合
        ArrayList<String> arr = Collections.list(enumeration);
        Iterator<String> iterator = enumeration.asIterator();

        Stack<Integer> stack = new Stack<>();   // 栈
        stack.push(2);
        stack.peek();   // 查看
        stack.pop();

        BitSet bitSet = new BitSet(10);     // 位集
        bitSet.length();
        bitSet.get(0);
        bitSet.clear(1);
        bitSet.set(1);
        // {and, or, xor, andNot}(BitSet);  // 与另一个位集操作
    }
}