package Fundamentals.Chapter9;

import java.util.*;

// 视图
public class ViewTest {
    static private List<Integer> list;
    static private Set<Integer> set;
    static private Map<String, Integer> map;

    static {
        list = new ArrayList<>(List.of(1, 2, 3));
        set = new TreeSet<>(Set.of(1, 2, 3));
        map = new TreeMap<>(Map.ofEntries(Map.entry("a", 1), Map.entry("b", 2)));
    }

    public static void main(String[] args) {
        // 小集合(构造不可修改集合)
        List.of("a", "b");
        Set.of(1, 2, 3);
        Map.of("a", 1, "b", 2);
        Map.ofEntries(Map.entry("a", 1), Map.entry("b", 2));    // 可变参数版本
        Collections.nCopies(100, "default");    // 返回n个obj副本的List（错觉，实际只存储了一次）
        Arrays.asList("a", "b");    // 返回一个大小不可变但元素可set修改的集合

        // 子范围视图(左闭右开)
        List<Integer> subList = list.subList(0, 2); // 返回一个子区间视图，操作映射到原list
        SortedSet<Integer> subSet = ((SortedSet<Integer>) set)
                .subSet(Integer.valueOf(2), Integer.valueOf(5)); // SortedMap同理。headSet(to),tailSet(from)
        NavigableMap<String, Integer> subMap = ((NavigableMap<String, Integer>) map)
                .subMap("a", true, "c", false);

        // 不可修改视图
        Collections.unmodifiableCollection(list);   // 注意该方法得到的Collection的equals方法只检查是否为同一对象
        List<Integer> unmodifiableList = Collections.unmodifiableList(list);    // 其他方法的接口equals方法正常
        // 同步视图(保证线程安全)
        var synchronizedList = Collections.synchronizedList(list);
        // 检查型视图(运行时检查类型安全，在插入错误类型时即异常)
        var checkedList = Collections.checkedList(list, Integer.class);
    }
}
