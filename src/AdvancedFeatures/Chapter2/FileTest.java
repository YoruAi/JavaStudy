package AdvancedFeatures.Chapter2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.stream.Stream;

// 关于文件操作标准选项可查看卷二p91-92
public class FileTest {
    public static void main(String[] args) throws IOException {
        // 注意关于各种流与打开目录请使用try-with-resource以自动关闭
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


        // 获取文件信息 //
        Files.size(newPath);
        Files.exists(newPath);
        Files.isHidden(newPath);    // is(Readable|Writable|isExecutable|isRegularFile|isDirectory|isSymbolicLink)
        BasicFileAttributes attributes = Files.readAttributes(newPath, BasicFileAttributes.class);  // 获取文件属性
        FileTime fileTime = attributes.creationTime();  // 文件时间信息。lastAccessTime/lastModifiedTime


        // 访问目录 //
        Stream<Path> entries = Files.list(DirectoryPath);   // 不递归子目录
        Files.walk(DirectoryPath, 3);   // 递归子目录，可限制深度
        Files.find(DirectoryPath, 3, (path, attr) -> path.getFileName().toString().endsWith(".txt"));
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(DirectoryPath);    // Iterable接口
        // 可使用glob模式筛选文件，如*.java, **.java, ???.java, test[0-9A-F].java, *.{java,class}
        Files.walkFileTree(DirectoryPath, new SimpleFileVisitor<>() {
            // 默认继续访问或出错跳过，可重载
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // 访问文件
                System.out.println("File-" + DirectoryPath.getParent().relativize(file) + " Deleted.");
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // 访问目录前
                System.out.println("Into " + DirectoryPath.getParent().relativize(dir) + ":");
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                // 访问目录后
                if (exc != null) throw exc;
                System.out.println("Dir-" + DirectoryPath.getParent().relativize(dir) + " Deleted.");
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // 访问失败后
                System.err.println("ERROR when access to " + DirectoryPath.getParent().relativize(file));
                return FileVisitResult.SKIP_SUBTREE;    // 跳过子目录或SKIP_SIBLINGS跳过剩下的兄弟文件
            }
        });


        // ZIP文件系统 //
        FileSystem fs = FileSystems.newFileSystem(
                Paths.get("src/AdvancedFeatures/Chapter2/resource/TestZip.zip"));
        fs.getPath("/TestInput.txt");   // 如同Paths.get。至此，即可和之前一样操作了
    }
}
