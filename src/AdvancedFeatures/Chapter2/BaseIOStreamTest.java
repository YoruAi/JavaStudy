package AdvancedFeatures.Chapter2;

import java.io.*;

// IO流家族详情见卷二p46-46
public class BaseIOStreamTest {
    final static private String InputFile = "src\\AdvancedFeatures\\Chapter2\\resource\\TestInput.txt";
    final static private String OutputFile = "src\\AdvancedFeatures\\Chapter2\\resource\\TestOutput.txt";

    public static void main(String[] args) throws IOException {
        // 这四个抽象类都实现了Closeable接口，可使用try-with-resource
        // 抽象类InputStream、OutputStream - 处理字节 //
        InputStream in = new FileInputStream(InputFile);
        OutputStream out = new FileOutputStream(OutputFile);
        // InputStream类方法
        byte[] bytes = new byte[10];
        int aByte = in.read();       // 读入一个字节（抽象方法），阻塞
        int count = in.read(bytes);  // 读入进字节数组并返回实际读入字节数，可传入开始索引和读取最大长度
        bytes = in.readAllBytes();   // 读入所有字节
        in.skip(1);               // 跳过字节，返回实际跳过的字节数
        in.available();              // 返回当前可读入的字符数
        if (in.markSupported()) {    // 返回这个流是否支持打标记
            in.mark(10);     // 在当前位置打上标记，之后若又读入字节多于readLimit则忽略
            in.reset();              // 返回标记
        }
        in.transferTo(out);          // 将所有字节从输入流传递到输出流，返回字节数
        in.close();                  // 关闭
        // OutputStream类方法
        out.write(aByte);            // 写入一个字节（抽象方法），阻塞
        out.write(bytes);            // 写入一个字符数组，可传入开始索引和最大长度
        out.flush();                 // 刷新缓冲区Flushable接口
        out.close();                 // 关闭

        // 抽象类Reader、Writer - 处理字符 //
        Reader reader = new StringReader("\uD83C\uDF7A");
        Writer writer = new FileWriter(OutputFile);
        int aUnicode = reader.read();   // 读入一个Unicode码元(16位代码单元)（抽象方法）;read(CharBuffer)为Readable接口方法
        writer.write(aUnicode);         // 写入一个Unicode码元(16位代码单元)（抽象方法）
        writer.append("abc");           // 追加码元char或CharSequence（返回this）,Appendable接口方法。
        // CharSequence方法: charAt(i);length();subSequence(i,j);toString();compare(cs1,cs2);chars();codePoints();
        reader.close();
        writer.flush();
        writer.close();
    }
}
