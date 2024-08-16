package Fundamentals.Chapter9;

import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
    public static void main(String[] args) throws IOException {
        // 属性映射
        var defaultSettings = new Properties();
        defaultSettings.setProperty("height", "20px");
        var settings = new Properties(defaultSettings);     // 可提供默认属性映射
        settings.setProperty("width", "60px");
        var out = System.out;   // 或者FileOutPutStream("path");
        settings.list(out);
        settings.store(out, "comments");    // 使用load(InputStream)从文件加载
        settings.getProperty("width", "");  // 无则返回默认值

        // 需要复杂的配置信息可见Preferences
    }
}
