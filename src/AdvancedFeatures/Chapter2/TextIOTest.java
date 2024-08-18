package AdvancedFeatures.Chapter2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

// 文本输入输出
public class TextIOTest {
    final static private String InputFile = "src\\AdvancedFeatures\\Chapter2\\resource\\TestInput.txt";
    final static private String OutputFile = "src\\AdvancedFeatures\\Chapter2\\resource\\TestOutput.txt";

    public static void main(String[] args) throws IOException {
        var reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);  // 只能读入字符，功能少

        // 输出 //
        var out = new PrintWriter(OutputFile, StandardCharsets.UTF_8);  // 指定字符集
        out.println(520);
        out.checkError();   // 错误检查

        // 输入 //
        List<String> lines = Files.readAllLines(Path.of(InputFile), StandardCharsets.UTF_8);
        Stream<String> linesSteam = Files.lines(Path.of(InputFile), StandardCharsets.UTF_8);

        Scanner in = new Scanner("s\nName|520|2024\n");    // 使用扫描器读入token(使用分隔符分隔的字符串)
        in.useDelimiter("\\PL+");       // 修改默认分隔符为一个正则表达式
        String line = in.next();
        in.nextLine();
        String next = in.nextLine();
        String[] tokens = next.split("\\|");
        Arrays.stream(tokens).forEachOrdered(System.out::println);

        InputStream inputStream = new FileInputStream(InputFile); // 使用Reader
        var bufferedIn = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)); // 指定字符集
        next = bufferedIn.readLine();
        linesSteam = bufferedIn.lines();
    }
}
