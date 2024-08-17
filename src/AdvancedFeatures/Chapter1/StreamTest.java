package AdvancedFeatures.Chapter1;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// 流：不存储、不修改(若操作时原集合被修改则行为未定义)、尽可能惰性
public class StreamTest {
    public static void main(String[] args) throws IOException {
        // 创建流 //
        final String[] arr = {"aaa", "bbb", "ccc", "dd", "e", "abc"};
        Stream<String> stringStream;
        // Collection接口的stream()方法
        List<String> strings = List.of(arr);
        strings.stream().filter(w -> w.length() > 5).count(); // 将stream换parallelStream为并行流
        // 对于数组使用Stream静态方法
        stringStream = Arrays.stream(arr, 0, 2);   // 使用数组的一部分创建流
        stringStream = Stream.of(arr); // 使用数组或可变参数列表
        stringStream = Stream.empty(); // 空流
        stringStream = Stream.ofNullable("Obj");    // 如果对象为null则长度为0，否则为1 
        stringStream = Stream.generate(() -> "Echo");   // 无限流：常量值"Echo"流
        Stream.generate(Math::random);  // 无限流：随机数流
        Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));    // 无限流：将函数不断应用于种子上
        Stream.iterate(0, n -> n < 100, n -> n + 1);    // 有限流：含存在条件
        // 其他方式
        stringStream = Pattern.compile("\\PL+").splitAsStream("a b c"); // 使用正则表达式得到单词流
        stringStream = new Scanner("a b c").tokens();   // 得到单词流(next)
        stringStream = Files.lines(Path.of("README.md"), StandardCharsets.UTF_8);    // 获得文件每一行的流
        Iterable<String> iterable = List.of("");                // 若是Iterable但不是集合
        StreamSupport.stream(iterable.spliterator(), false);    // 得到一个顺序流
        Iterator<String> iterator = List.of("").iterator();     // 若是Iterator
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);


        // 流的转换 - 中间操作 //
        // filter方法 - 筛选
        stringStream = stringStream.filter(s -> s.length() > 3);
        // map方法 - 转化值
        stringStream = stringStream.map(String::toLowerCase);   // 应用函数产生新流
        Stream<Stream<String>> streamStream = stringStream.map(s -> {
            return new Scanner(s).tokens();
        });                     // 包含流的流，如果想要将返回值流的内容直接加入流中，可使用flatMap
        // 抽取子流和组合流
        stringStream = Stream.of(arr).limit(10); // 返回截断的新流
        stringStream = stringStream.skip(3);    // 跳过n前个元素
        stringStream = stringStream.takeWhile(s -> s.length() > 2);    // 读取直到不满足
        stringStream = stringStream.dropWhile(s -> s.length() <= 2);   // 删除直到不满足，返回剩余元素
        stringStream = Stream.concat(stringStream, Stream.of(arr));    // 合并两个流
        // 其他流的转换
        stringStream = stringStream.distinct();     // 剔除重复元素
        stringStream = stringStream.sorted();       // 排序(可接收比较器参数)
        stringStream = stringStream.peek(System.out::println);   // 每到一个元素执行函数


        // 获取流结果 - 终结操作 //
        // 简单约简
        Stream.of(arr).count();
        Optional<String> optionalString = Stream.of(arr).max(String::compareTo);    // 传入比较器
        Stream.of(arr).findFirst(); // findAny()返回任意元素
        Stream.of(arr).anyMatch(s -> s.startsWith("a"));  // 接受一个断言，返回是否有匹配项。allMatch/noneMatch同
        // Optional包装器
        optionalString.isPresent(); // 判断是否不为空
        String result = optionalString.get();   // 获取值(不安全) == orElseThrow()
        result = optionalString.orElse("");  // 若值不存在则用other替代
        result = optionalString.orElseGet(() -> "a" + "b");      // 默认值需要计算
        result = optionalString.orElseThrow(IOException::new);   // 没有值时抛出异常
        optionalString.ifPresent(System.out::println);  // 存在时执行
        optionalString.ifPresentOrElse(System.out::println, () -> System.err.println("None!"));  // 存在时执行
        // Optional - 管道化值
        Optional<String> transform = optionalString.map(String::toLowerCase);   // 存在则通过函数产生新Optional，否则空
        transform = optionalString.filter(s -> s.length() < 3); // 若满足则不变，不满足则空
        transform = optionalString.or(() -> Stream.of(arr).findFirst());    // 空则计算另一个Optional
        // Optional - 创建
        Optional.empty();
        Optional.of("Can't be null");
        Optional.ofNullable("Can be null"); // 若为null返回空Optional
        // Optional - flatMap()方法
        Optional<Integer> value = Stream.of(arr).findFirst()
                .flatMap(s -> Optional.of(s.length()))
                .flatMap(i -> Optional.of(i + 1));   // 对可能的Optional值执行函数返回Optional，可继续调用形成步骤管道
        // Optional - 转化为流
        value.stream(); // 若无值则返回空流
        stringStream = Stream.of(arr).map(Optional::ofNullable)
                .flatMap(Optional::stream);  // 对于一个Optional流，使用扁平化流解包Optional
        stringStream = stringStream.filter(Objects::nonNull);   // 若不是Optional流，注意对null的特判 - 方法一
        stringStream = stringStream.flatMap(Stream::ofNullable);    // 方法二
        // 流结果收集 - 遍历
        Stream.of(arr).iterator();  // 使用迭代器
        Stream.of(arr).forEach(System.out::println);  // 对并行流顺序无法确定
        Stream.of(arr).forEachOrdered(System.out::println);   // 非并行实现，顺序执行
        String[] stringsResult = Stream.of(arr).toArray(String[]::new);
        // 流结果收集 - 收集器
        String joinString = Stream.of(arr)
                .map(Objects::toString)     // 若存在非String
                .collect(Collectors.joining(",", "pre", "suf")); // 字符串拼接收集器
        List<String> stringList = Stream.of(arr).collect(Collectors.toList());  // 产生收集器Collector并使用，收集到集合
        TreeSet<String> stringTreeSet = Stream.of(arr).collect(Collectors.toCollection(TreeSet::new));  // 指定类型
        Map<String, Integer> stringMap = Stream.of(arr)
                .collect(Collectors.toMap(      // 收集到映射表(默认有相同键抛出异常，可提供第三参数)
                        s -> s,                 // 或Function.identity()来返回输入值
                        String::length,
                        (existValue, newValue) -> existValue));   // 解决冲突
        Map<Integer, Set<String>> setMap = Stream.of(arr)
                .collect(Collectors.toMap(      // toConcurrentMap并发映射表;toUnmodifiableMap不可修改映射表
                        String::length,
                        s -> new HashSet<>(Set.of(s)),
                        (existSet, newSet) -> {
                            existSet.addAll(newSet);
                            return existSet;
                        },
                        TreeMap::new)           // 可指定返回的Map类型
                );
        Map<Integer, List<String>> listMap = Stream.of(arr)         // 使用Function参数产生键
                .collect(Collectors.groupingBy(String::length));    // 使用groupingByConcurrent方法处理并发流
        Map<Boolean, List<String>> booleanListMap = Stream.of(arr)
                .collect(Collectors.partitioningBy(s -> s.length() < 5));   // 使用partitioningBy分为真假两组
        Map<Integer, Set<String>> integerSetMap = Stream.of(arr)
                .collect(Collectors.groupingBy(String::length, Collectors.toSet())); // 下游收集器（对每一组元素进行收集）
        // 流结果收集 - 其他收集器（tip:尽量只在groupingBy的下游处理器中使用，否则应处理流，都有对应流方法）
        Collectors.counting();
        IntSummaryStatistics summary // 对该对象使用get(Count|Max|Average|Sum);若无则返回Integer.MAX_VALUE...
                = Stream.of(arr).collect(Collectors.summarizingInt(String::length)); // 产生统计收集器
        Collectors.summingInt(String::length);  // 可由数字流方法替代
        Collectors.maxBy(Comparator.comparingInt(String::length));      // 通过比较器产生(下游)元素中的最大值
        Collectors.mapping(String::length, Collectors.toSet()); // 函数应用于收集的元素后使用下游收集器(flatMapping可处理流)
        Collectors.filtering((String s) -> s.length() < 10, Collectors.toSet());    // 组内筛选后下游收集器收集
        Collectors.reducing("", String::concat);    // 组内合并
        Collectors.collectingAndThen(Collectors.toSet(), Set::size);    // (下游)收集后产生最后的值
        // 约简操作
        Optional<String> stringOptional = Stream.of(arr).reduce(String::concat);    // 两个元素向后应用（并行流无顺序）
        String reduced = Stream.of(arr).reduce("", String::concat);         // 可提供幺元
        Stream.of(arr).reduce(0, (total, s) -> total + s.length(), Integer::sum); // 返回值不同需提供结果合并函数
        BitSet bitSet = Stream.of(1, 2).collect(BitSet::new, BitSet::set, BitSet::or); // 对于线程不安全的处理

        // 其他 //
        // 基本类型流 - 优化掉包装器
        IntStream intStream;
        intStream = IntStream.of(1, 2, 3);
        intStream = Arrays.stream(new int[]{1, 2, 3}, 0, 2);
        intStream = IntStream.range(0, 100);    // generate, iterate方法也有，rangeClosed包含上界
        intStream = stringStream.mapToInt(String::length);  // 由对象流转化为基本类型流
        Stream<Integer> integerStream = intStream.boxed();  // 由基本类型转化为对象流
        int[] intArr = IntStream.range(0, 10).toArray();
        OptionalInt optionalInt = IntStream.range(0, 10).max(); // OptionalInt使用getAsInt()方法
        int sum = IntStream.range(0, 10).sum();             // 含有sum,average,max等方法
        IntSummaryStatistics intSummaryStatistics = IntStream.range(0, 10).summaryStatistics();
        intStream = "string".codePoints();      // 码点流，chars()返回代码单元流
        intStream = new Random().ints(10, 0, 5);// 随机数流(不可分割，故涉及并行应使用SplittableRandom)
        // 并行流
        Stream<String> parallelStream;
        parallelStream = strings.parallelStream();   // 从集合中获取并行流
        parallelStream = stringStream.parallel();    // 顺序流转化为并行流
        Stream<String> unordered = strings.parallelStream().unordered();   // 标记为无序流，提高某些方法的性能
        Map<Integer, List<String>> integerListMap = strings.parallelStream().collect(
                Collectors.groupingByConcurrent(String::length)
        );  // 因合并映射表代价高昂，故使用并行化共享同一映射表（注意需要不关注顺序）
    }
}