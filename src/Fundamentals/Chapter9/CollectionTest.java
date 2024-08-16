package Fundamentals.Chapter9;

import java.util.*;

public class CollectionTest {
    public static void main(String[] args) {
        // 四个重要的接口 //
        /* 
        // 随机访问标记接口RandomAccess
        // 通过instanceof来判断是否支持随机访问

        // 迭代器接口Iterator<E>
        // 想象为越过元素后在元素中间的指针
        // forEach(Consumer)循环可处理实现了Iterable<E>接口的对象：要求实现Iterator<E> iterator();方法。
        // 方法
        // E next();        // 到了末尾则抛出异常NoSuchElementException
        // boolean hasNext();
        // void remove();   // 删除上次next()查看的元素，若无则抛出异常IllegalStateException
        // void forEachRemaining(Consumer<? super E>);

        // 基本集合接口Collection<E> extends Iterable<E>
        // AbstractCollection<E>类：只保留size()和iterator()为抽象方法，其他提供了默认实现
        // 方法
        // Iterator<E> iterator();  // 获得迭代器
        // size();isEmpty();clear();
        // contains(Object);containsAll(Collection<?>);    // 判断包含
        // add(E);addAll(Collection<? extends E>);         // 添加元素(一般是尾部)，如果改变了则返回true
        // removeIf(Predicate<? super E>);
        // remove(Object);removeAll(Collection<?>);        // 删除所有equals，如果改变了则返回true
        // retainAll(Collection<?>);                // 仅保留相同的（交集），若改变了返回true
        // Object[] toArray(); T[] toArray(T[]);    // 若空间足够大则填入参数数组(其余为null)，否则使用T返回新数组

        // 基本映射接口Map<K, V>
        // AbstractMap<E>类：只保留entrySet()为抽象方法，其他提供了默认实现
        // 映射视图：关于键、值与条目的集合，可以删除但不能添加。
        // 方法
        // size();isEmpty();clear();remove(Object[,Object]);
        // containsKey(Object);containsValue(Object);
        // V put(K, V);putIfAbsent(K, V);               // 返回之前的旧值，无则返回null
        // void putAll(Map<? extends K, ? extends V>);  // 添加条目
        // V get(Object);getOrDefault(Object, V);       // 获取值，无则返回null或设定默认值
        // boolean replace(K, V old, V new);V replace(K, V);  // 仅当有旧值时替换
        // V merge(K, V, BiFunction);   // 若不存在则关联value，若存在则将函数应用于value与该值并关联，若结果为null则删除该键
        // V compute[IfPresent](K, BiFunction);     // [若存在，]将函数应用于key与该值并关联，若结果为null则删除该键
        // V computeIfAbsent(K, Function)           // 若不存在，将函数应用于key并关联，若结果为null则删除该键
        // void replaceAll(BiFunction);             // 对所有条目，将函数应用于键与值并关联，若结果为null则删除该键
        // forEach(BiConsumer<? super K, ? super V>);
        // Set<K> keySet();
        // Collection<V> values();
        // Set<Map.Entry<K, V>> entrySet();// 返回映射条目集。元素为Map.Entry接口对象(getKey();getValue();setValue(V))
        // static Map.Entry entry(K, V);   // 创建entry对象
        
         */


        // 集合框架 //
        /* 接口 (基本接口 - Collection, Map)
        Iterable->Collection->(List), (Set->SortedSet->NavigableSet), (Queue->Deque)
        Map->SortedMap->NavigableMap
        Iterator->ListIterator
        (RandomAccess)
        
        接口介绍
        // 双向迭代器接口ListIterator<E> extends Iterator<E>
        // 支持双向迭代
        // 方法
        // previous();hasPrevious();
        // nextIndex();previousIndex();
        // add(E);set(E);
        
        // 有序集合接口List<E> extends Collection<E>
        // 支持随机访问
        // 方法
        // listIterator();      // 获得列表迭代器
        // void add(int index, E);addAll(int index, Collection<? super E>);
        // void replaceAll(UnaryOperator);      // 所有元素替换为对元素应用函数的结果
        // remove(int index);
        // E get(int index);
        // E set(int index, E);                 // 返回原来元素
        // indexOf(Object);lastIndexOf(Object); // 返回索引，无则返回-1
        // sort(Comparator);    // 排序，传入null则使用Comparable接口方法
        
        // 集接口Set<E> extends Collection<E>
        // 无重复元素集
        // 方法
        // 同Collection<E>
        
        // 有序集接口SortedSet<E> extends Set<E>
        // 有序集，可看作默认最小(first)在前
        // 方法
        // comparator();    // 返回比较器，若使用Comparable接口则返回null
        // first();last();  // 返回最小或最大元素
        
        // 搜索集接口NavigableSet<E> extends SortedSet<E>
        // 提供搜索与导航方法
        // 方法
        // higher(E);lower(E);ceiling(E);floor(E);  // 返回大于/小于/大于等于/小于等于的最元素
        // E pollFirst();pollLast();                // 删除并返回最小或最大元素
        // descendingIterator();                    // 返回反向迭代器
        
        // 队列接口Queue<E> extends Collection<E>
        // 队列
        // 方法
        // peek();  element();    // 查看队头(前null后异常)
        // poll();  remove();     // 弹出队头(前null后异常)
        // offer(); add()         // 压入队尾(前false后异常)
        
        // 双端队列接口Deque<E> extends Queue<E>
        // 双端队列
        // 方法
        // {peek/get}{First/Last}();
        // {poll/remove}{First/Last}();
        // {offer/add}{First/Last}();
        // push();pop();            // 队头操作
        // remove{First/Last}Occurrence(Object);
        // descendingIterator();    // 返回反向迭代器
        
        // 有序映射接口SortedMap<E> extends Map<E>
        // 有序映射，可看作默认最小键(firstKey)在前
        // 方法
        // comparator();    // 返回比较器，若使用Comparable接口则返回null
        // K firstKey();lastKey();  // 返回最小或最大键
        
         */


        // 具体类 //
        /*
        AbstractCollection:
        ArrayDeque[由循环数组(默认大小16，构造时可指定)实现的双端队列 impl Deque<E>]
        AbstractQueue - PriorityQueue[构造器(int initCapacity, Comparator)]
        AbstractList - ArrayList, LinkedList[双向链表 impl Deque<E>]
        AbstractSet - HashSet, TreeSet, EnumSet, LinkedHashSet
        
        AbstractMap:
        HashMap, TreeMap, EnumMap, LinkedHashMap, WeakHashMap, IdentityHashMap


        // 散列集HashSet [散列映射HashMap]
        // 拉链式，哈希冲突多优化为平衡二叉树；含再散列机制，根据装填因子(默认75%)对桶数翻倍再散列。
        // 方法
        // HashSet(int initCapacity[, float loadFactor]);   // 设置初始桶数(默认16)[，设置装填因子(默认0.75)]
        
        // 树集TreeSet(impl NavigableSet) [树映射TreeMap]
        // 有序集合，由红黑树实现；元素需Comparable或提供Comparator。
        // 方法
        // TreeSet(Comparator<? super E>);
        // TreeSet(Collection<? extends E>/SortedSet<E>);   // 构造(与SortedSet同样的顺序)
        
        // 枚举集EnumSet [枚举映射EnumMap]
        // 使用位序列实现，只有有限个枚举类型实例
        // 方法
        // EnumSet.{all/none}Of(Enum.class);
        // EnumSet.range(Enum.enum1, Enum.enum2);
        // EnumSet.of(Enum.enum1, ...);
        // EnumMap<Enum, V>(Enum.class);
            
        // 链接散列集LinkedHashSet [链接散列映射LinkedHashMap]
        // 记住插入顺序，遍历视图时将按插入顺序(从旧到新)遍历
        // 链接散列映射构造时可传入true参数使记住访问顺序，访问时放于项链表尾部。
        // 因此可根据访问频率自动删除元素：创建子类覆盖removeEldestEntry(Map.Entry)方法
        // var cache = new LinkedHashMap<String, Integer>(128, 0.75F, true) {
        //            @Override
        //            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        //                return size() > 100;
        //            }
        //        };
            
        // 弱散列映射WeakHashMap
        // 使用弱引用WeakReference保存键，自动回收外部不再引用的键。

        // 标识散列映射IdentityHashMap
        // 哈希值由System.IdentityHashCode(Object)计算(包含内存地址)，对象比较时使用==
        // 方法
        // IdentityHashMap(int expectedMaxSize);    // 默认为21，将构造大小大于1.5倍该值的2的最小幂次容量

         */
        Learning();
    }

    private static void Learning() {
        String[] a = {"a", "b", "c"};
        List<String> arr = Arrays.asList(a);
        // LinkedList //
        // 用列表迭代器访问链表
        LinkedList<Object> linkedlist = new LinkedList<>(arr);
        var iter = linkedlist.listIterator();
        System.out.println(iter.next());
        iter.remove();
        iter.add("x");

        // Map //
        Map<String, Integer> map = new HashMap<>();
        // 对于第一次映射的特判
        // 1
        map.merge("words", 1, Integer::sum);
        // 2
        map.put("words", map.getOrDefault("words", 0) + 1);
        // 3
        map.putIfAbsent("words", 0);
        map.put("words", map.get("words") + 1);
        // 显示所有条目
        // 1
        map.forEach((k, v) -> System.out.println("key=" + k + ",value=" + v));
        // 2 (实际上是1的实际实现)
        for (var entry : map.entrySet()) {
            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
        }
    }
}