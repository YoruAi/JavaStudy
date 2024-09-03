package AdvancedFeatures.Chapter2;

import java.io.*;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MemoryMapFileTest {
    public static void main(String[] args) throws IOException {
        Path file = Paths.get("src/AdvancedFeatures/Chapter2/resources/TestInput.dat");
        FileChannel channel = FileChannel.open(file);
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        // 支持随机访问
        ByteOrder order = buffer.order();   // 高或低位在前
        buffer.getInt();    // 二进制数字读取
        buffer.putInt(0, 321);    // 二进制数字写入
        for (int p = 0; p < buffer.limit(); p++) {     // or: while(buffer.hasRemaining) { buffer.get(); }
            byte c = buffer.get(p);
        }

        // 想要新建一个缓冲区可以使用ByteBuffer.allocate(n)方法，然后使用channel.read(buffer)读取，channel.write(buffer)写出
        // 使用buffer.flip()使界限设置到位置并复位到0。其他还有clear, rewind, mark, reset, remaining, position, capacity方法
        CharBuffer charBuffer = buffer.asCharBuffer();  // 将字节缓冲区包装成一个xx缓冲区


        // 文件加锁机制 //
        // 推荐在try-with-resource中使用自动释放锁
        FileLock lock = channel.lock();     // 阻塞直至获得锁，或tryLock()会返回null
        channel.lock(0, 10, true);  // 部分锁，shared指示共享锁，仅允许读入，禁止独占锁(不一定可行)
        lock.close();
    }
}
