package Fundamentals.Chapter3;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class IOTest {
    public static void main(String[] args) throws IOException {
        // 输入
        Scanner in = new Scanner(System.in);
        if (!in.hasNext()) System.exit(-1);
        String s = in.nextLine();   // 有分隔符
        String next = in.next();    // 无分隔符
        int num = in.nextInt();
        double dnum = in.nextDouble();
        // 密码输入
        Console cons = System.console();
        if (cons != null) {
            String vis = cons.readLine("name: ");
            char[] unvis = cons.readPassword("pwd: ");
        }

        // 格式化输入输出
        System.out.printf("%-10s - The name\n", s);
        System.out.printf("%(,.2f\n", dnum);
        String fmt = String.format("%,d %<+d %< d %1$#x", num);

        // 文件IO
        Scanner fin = new Scanner(Path.of("file.txt"), StandardCharsets.UTF_8); // 文件输入
        PrintWriter fout = new PrintWriter("file.txt", StandardCharsets.UTF_8); // 文件输出
        String dir = System.getProperty("user.dir");        // 获取启动目录位置
        String cp = System.getProperty("java.class.path");  // 获取classpath目录位置
        System.out.println(dir);
        System.out.println(cp);
    }
}
