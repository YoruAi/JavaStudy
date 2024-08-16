package Fundamentals.Chapter5;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResourceTest {
    public static void main(String[] args) throws IOException {
        Class cl = ResourceTest.class;  // 通过类文件相对位置查询资源

        URL aboutURL = cl.getResource("resource/about.png");    // 可接受URL
        var icon = new ImageIcon(aboutURL);

        InputStream stream = cl.getResourceAsStream("resource/about.txt");  // 其他
        var about = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
