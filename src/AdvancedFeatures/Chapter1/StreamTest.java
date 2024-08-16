package AdvancedFeatures.Chapter1;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
        Stream.iterate(0, n -> n > 100, n -> n + 1);    // 有限流：含终止条件
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
        // 约简
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
        // 管道化Optional值
        Optional<String> transform = optionalString.map(String::toLowerCase);   // 存在则通过函数产生新Optional，否则空
        transform = optionalString.filter(s -> s.length() < 3); // 若满足则不变，不满足则空
        transform = optionalString.or(() -> Stream.of(arr).findFirst());    // 空则计算另一个Optional
        // 创建Optional值
        Optional.empty();
        Optional.of("Can't be null");
        Optional.ofNullable("Can be null"); // 若为null返回空Optional
        // Optional的flatMap()方法
        Optional<Integer> value = Stream.of(arr).findFirst()
                .flatMap(s -> Optional.of(s.length()))
                .flatMap(i -> Optional.of(i + 1));   // 对可能的Optional值执行函数返回Optional，可继续调用形成步骤管道
        // 将Optional转化为流
        value.stream(); // 若无值则返回空流
        stringStream = Stream.of(arr).map(Optional::ofNullable)
                .flatMap(Optional::stream);  // 对于一个Optional流，使用扁平化流解包Optional
        stringStream = stringStream.filter(Objects::nonNull);   // 若不是Optional流，注意对null的特判 - 方法一
        stringStream = stringStream.flatMap(Stream::ofNullable);    // 方法二
        // 流结果收集
        Stream.of(arr).iterator();  // 使用迭代器
        Stream.of(arr).forEach(System.out::println);  // 对并行流顺序无法确定
        Stream.of(arr).forEachOrdered(System.out::println);   // 非并行实现，顺序执行
        String[] stringsResult = Stream.of(arr).toArray(String[]::new);
        List<String> stringList = Stream.of(arr).collect(Collectors.toList());  // 产生收集器Collector并使用，收集到集合
        TreeSet<String> stringTreeSet = Stream.of(arr).collect(Collectors.toCollection(TreeSet::new));  // 指定类型
        String joinString = Stream.of(arr)
                .map(Objects::toString)     // 若存在非String
                .collect(Collectors.joining(",", "pre", "suf")); // 字符串拼接收集器
        IntSummaryStatistics summary // 对该对象使用get(Count|Max|Average|Sum);若无则返回Integer.MAX_VALUE...
                = Stream.of(arr)
                .collect(Collectors.summarizingInt(String::length)); // 产生总和收集器
        Map<String, Integer> stringMap = Stream.of(arr)     // 收集到映射表(默认有相同键抛出异常，可提供第三参数)
                .collect(Collectors.toMap(s -> s, String::length    // 或Function.identity()来返回输入值
                        , (existValue, newValue) -> existValue));   // 解决冲突
        Map<Integer, Set<String>> setMap = Stream.of(arr)
                .collect(Collectors.toMap(String::length  // toConcurrentMap并发映射表;toUnmodifiableMap不可修改映射表
                        , s -> new HashSet<>(Set.of(s))
                        , (existSet, newSet) -> {
                            existSet.addAll(newSet);
                            return existSet;
                        }
                        , TreeMap::new)     // 可指定Map类型
                );
    }
}