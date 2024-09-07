package Fundamentals.Chapter12;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentCollectionTest {
    public static void main(String[] args) {
        ConcurrentSkipListSet<String> concurrentSkipListSet;
        ConcurrentSkipListMap<String, Integer> concurrentSkipListMap;
        CopyOnWriteArrayList<String> copyOnWriteArrayList;  // 建立视图副本（可能过时）
        CopyOnWriteArraySet<String> copyOnWriteArraySet;

        // 老方法
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Integer oldVal;
        Integer newVal;
        do {
            oldVal = concurrentHashMap.get("ABC");
            newVal = oldVal == null ? 1 : oldVal + 1;
        } while (!concurrentHashMap.replace("ABC", oldVal, newVal));
        // 新方法
        concurrentHashMap.compute("abc", (k, v) -> v == null ? 1 : v + 1);
        concurrentHashMap.merge("abc", 1, Integer::sum);

        // 三种操作
        String result1 = concurrentHashMap.search(10, (k, v) -> v > 2 ? k : null);
        concurrentHashMap.forEach(10, (k, v) -> System.out.println(k + "->" + v));
        concurrentHashMap.forEach(10, (k, v) -> k + "->" + v, System.out::println); // 转换为null则跳过
        Integer sum = concurrentHashMap.reduceValues(10, Integer::sum);
        Integer maxLength = concurrentHashMap.reduceKeys(10, String::length, Integer::max);
        Set<String> concurrentHashSet = ConcurrentHashMap.<String>newKeySet();  // 包装为一个映射为集合

        // 并行数组算法
        String[] words = new String[]{"a", "b"};
        Arrays.parallelSort(words, 0, 1);
        Arrays.parallelSetAll(words, i -> i % 10);           // 按索引
        Arrays.parallelPrefix(words, (s1, s2) -> s1 + s2);   // 并行前缀和

        // 线程安全包装器
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        SortedSet<String> syncSortedSet = Collections.synchronizedSortedSet(new TreeSet<>());

        // Vector和HashTable类是线程安全的
    }
}
