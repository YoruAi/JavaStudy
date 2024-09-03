package AdvancedFeatures.Chapter2;

import java.io.*;

// 二进制输入输出
public class DataIOTest {
    final static private String InputFile = "src\\AdvancedFeatures\\Chapter2\\resources\\TestInput.dat";

    public static void main(String[] args) throws IOException {
        // DataInput、DataInput接口
        // write/read(Int|Chars|Byte|Char|...)
        // skipBytes(n) 跳过n字节
        // readFully(byte[])    读入数组
        var in = new DataInputStream(new FileInputStream(InputFile));
    }
}
