package AdvancedFeatures.Chapter2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegexTest {
    public static void main(String[] args) throws IOException {
        // 具体可见卷二p109-111
        // .任何字符
        // [0-9A-Z]
        // [^a-z]补集
        // \x{1D546}十六进制Unicode码点，\u0000码元
        // \p{...}某个预定义字符集（大写为补集），如pL匹配Unicode字母
        // \d数字, \w数字字母, \s空格符号
        // X|Y字符串
        // ?可选表达式, *可选字符, +多个字符
        // X{n,m} n到m位X
        // Q?最短匹配, Q+最长匹配
        // ^X$边界
        // (X)捕获X的匹配组, (?<id>[a-z])组名, \1或\k<id>可使用组
        // (?:X)使用括号但不捕获

        Pattern pattern = Pattern.compile("([Jj]ava\\w+)",
                Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);   // 使用选项设置对Unicode的大小写不敏感
        pattern.asPredicate();      // 转化为谓词

        // 匹配 //
        Matcher matcher = pattern.matcher("java123");
        if (matcher.matches()) {    // 是否匹配全部输入
            matcher.groupCount();   // 正则表达式中捕获组数
            String matchSting = matcher.group(1);   // 0代表整个输入(按照前括号排序), 也可使用指定的组名。
            int start = matcher.start(1);
            int end = matcher.end(1);
        }

        matcher = pattern.matcher("java1 java2java3");
        while (matcher.find()) {    // 寻找下一个匹配子串
            String matchSting = matcher.group();    // 可通过start/end找到字符索引
            System.out.println(matchSting);
        }

        matcher = pattern.matcher("java1 java2java3");
        Stream<MatchResult> matchResultStream = matcher.results();
        List<String> matches = matchResultStream.map(MatchResult::group).collect(Collectors.toList());

        Scanner in = new Scanner(Paths.get("src/AdvancedFeatures/Chapter2/resources/TestOutput.txt"));
        Stream<String> words = in.findAll("\\d").map(MatchResult::group);
        words.forEach(System.out::println);


        // 分割 //
        Pattern splitPatten = Pattern.compile("\\s*,\\s*");
        String[] tokens = splitPatten.split("1, 2 , 3", 0); // 可使用limit参数指定最大分割数
        Stream<String> tokenStream = splitPatten.splitAsStream("1, 2,3"); // 使用splitAsStream惰性处理
        // 对于文件可以使用扫描器，in.useDelimiter(regex)切换分隔符，使用in.tokens()得到String流


        // 替换 //
        Matcher splitMatcher = splitPatten.matcher("1, 2 , 3");
        String output = splitMatcher.replaceAll(" ");
        output = splitMatcher.replaceAll(Matcher.quoteReplacement("$\\0"));    // 若字符中含有$或\，使用该方法不会转义
        output = splitMatcher.replaceFirst(matchResult -> matchResult.group().toUpperCase());
    }
}
