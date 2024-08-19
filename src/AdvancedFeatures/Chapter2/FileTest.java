package AdvancedFeatures.Chapter2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

// 关于文件操作标准选项可查看卷二p91-92
public class FileTest {
    public static void main(String[] args) throws IOException {
        Path FilePath = Paths.get("src", "AdvancedFeatures", "Chapter2", "resource", "TestInput.txt");
        // Files读写文件方法 //
        byte[] readAllBytes = Files.readAllBytes(FilePath);
        String readString = Files.readString(FilePath);
        List<String> lines = Files.readAllLines(FilePath, StandardCharsets.UTF_8);

        Files.writeString(FilePath, readString, StandardCharsets.UTF_8);    // 字符串
        Files.write(FilePath, readAllBytes, StandardOpenOption.APPEND);     // 可添加写入格式为追加
        Files.write(FilePath, lines);     // 行字符串集合

        // 对于大型文件可使用IO流
        InputStream in = Files.newInputStream(FilePath);
        OutputStream out = Files.newOutputStream(FilePath, StandardOpenOption.APPEND);
        Reader reader = Files.newBufferedReader(FilePath, StandardCharsets.UTF_8);
        Writer writer = Files.newBufferedWriter(FilePath, StandardCharsets.UTF_8, StandardOpenOption.APPEND);


        // 创建文件和目录(指定文件或目录属性不展示) //
        Path DirectoryPath = Paths.get("src", "AdvancedFeatures", "Chapter2", "resource", "create");
        Files.createDirectory(DirectoryPath);   // 创建目录，需保证中间路径存在(createDirectories可自动创建中间路径)
        Files.createFile(DirectoryPath.resolveSibling("temp.txt"));  // 创建文件，若已有则抛出异常
        Path tempDirectoryPath = Files.createTempDirectory(DirectoryPath, null);      // 创建临时目录
        Path tempFilePath = Files.createTempFile(DirectoryPath, null, ".txt");  // 创建临时文件


        // 操作文件 //
        Path newPath = DirectoryPath.resolve(FilePath.getFileName());
        Files.copy(FilePath, newPath,
                StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES); // 可指定为复制文件属性、覆盖
        Files.move(tempFilePath, tempDirectoryPath.resolve(tempFilePath.getFileName()),   // 移动
                StandardCopyOption.ATOMIC_MOVE);          // 指定原子性
        Files.copy(new FileInputStream(FilePath.toFile()), newPath, // 输入流到文件
                StandardCopyOption.REPLACE_EXISTING);
        Files.copy(newPath, System.out);    // 文件到输出流
        Files.deleteIfExists(DirectoryPath.resolveSibling("temp.txt")); // 删除文件
    }
}
