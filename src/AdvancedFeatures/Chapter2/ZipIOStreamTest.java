package AdvancedFeatures.Chapter2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.*;

public class ZipIOStreamTest {
    final static private String zipFile = "src\\AdvancedFeatures\\Chapter2\\resource\\TestZip.zip";

    public static void main(String[] args) throws IOException {
        // IN
        var zipIn = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry;
        while ((entry = zipIn.getNextEntry()) != null) {
            // 读
            entry.getName();    // 名字
            entry.getSize();        // 大小
            entry.isDirectory();    // 是否目录
            zipIn.read();

            zipIn.closeEntry();
        }
        zipIn.close();


        // OUT
        var zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);  // 压缩级别
        zipOut.setMethod(ZipOutputStream.DEFLATED);     // 默认压缩方法
        {
            var ze = new ZipEntry("TestInput.txt");
            zipOut.putNextEntry(ze);
            // 写
            zipOut.write("abc".getBytes());

            zipOut.closeEntry();
        }
        zipOut.close();

        // ZipFile方法
        var file = new ZipFile(zipFile);
        ArrayList<? extends ZipEntry> list = Collections.list(file.entries());
        ZipEntry ze = file.getEntry("TestInput.txt");
        InputStream in = file.getInputStream(ze);
        file.getName();     // 返回zip文件路径
    }
}
