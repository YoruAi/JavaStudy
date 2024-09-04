package AdvancedFeatures.Chapter4;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class URLAndURITest {
    public static void main(String[] args) throws IOException {
        // URL 统一资源定位符 //
        // 可打开连接到资源的流，处理模式包括http,https,ftp,file,jar
        URL url = new URL("https://www.baidu.com/");
        InputStream inputStream = url.openStream(); // 访问资源

        // URI 统一资源标识符 //
        // 仅仅是一个语法结构，URI类仅解析，关于组成部分规则见卷二p200
        // 可解析相对标识符relativize和resolve

        // 使用URLConnection //
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);   // 设置请求属性
        connection.connect();           // 连接资源
        connection.getContent();        // 可查询消息头，getHeaderFields()返回所有字段的Map
        var in = connection.getInputStream();    // 访问资源
        var scanner = new Scanner(in, StandardCharsets.UTF_8);
        while (scanner.hasNextLine())
            System.out.println(scanner.nextLine());

    }
}
