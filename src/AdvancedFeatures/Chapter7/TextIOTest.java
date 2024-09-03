package AdvancedFeatures.Chapter7;

import java.nio.charset.Charset;

public class TextIOTest {
    public static void main(String[] args) {
        // 1 编码
        Charset platformEncoding = Charset.defaultCharset();

        // 2 行结束符
        // println写入的换行都没有问题，仅对于文本，可使用printf与%n产生平台相关行结束符
        System.out.printf("Hello world!%n");

        // 3 控制台编码
        // 可使用chcp 65001命令行并java -Dfile.encoding=UTF-8 ...

        // 4 日志
        // 可在日志的配置文件中修改logging.FileHandler.encoding=UTF-8

        // 5 UTF-8开头字节

        // 6 编译
        // 使用javac -encoding UTF-8 ...设定java源文件编码
    }
}
