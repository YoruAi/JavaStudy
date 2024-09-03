package AdvancedFeatures.Chapter2;

import java.io.*;

public class RandomAccessFileTest {
    final static private String InputFile = "src\\AdvancedFeatures\\Chapter2\\resources\\TestInput.dat";

    public static void main(String[] args) {
        try {
            var in = new RandomAccessFile(InputFile, "r");  // rw
            in.seek(1L);
            in.length();        // 文件字节总数
            in.getFilePointer();    // 获得当前所在位置
            // 读写方法实现DataIO接口
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
