package AdvancedFeatures.Chapter2;

import java.io.*;

public class CombineFilterIOStreamTest {
    // 在java.io的类都以用户工作目录user.dir开始
    final static private String InputFile = "src\\AdvancedFeatures\\Chapter2\\resource\\TestInput.dat";
    final static private String OutputFile = "src\\AdvancedFeatures\\Chapter2\\resource\\TestOutput.txt";

    public static void main(String[] args) throws IOException {
        // 组合IO流过滤器FilterInputStream
        try (var fin = new FileInputStream(InputFile);
             var fout = new FileOutputStream(OutputFile, true);   // append追加
        ) {
            // 组合输入流
            PushbackInputStream pushbackIn;
            var din = new DataInputStream(pushbackIn = new PushbackInputStream(new BufferedInputStream(fin), 64));
            // (使用缓冲机制和数据传输)
            int next = pushbackIn.read();               // 使用PushbackInputStream查看下一个字节
            if (next != '0') pushbackIn.unread(next);   // 若不符合可重新推回流中
            System.out.println(din.readInt());
        }
    }
}
