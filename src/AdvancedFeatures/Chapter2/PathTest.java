package AdvancedFeatures.Chapter2;

import java.nio.file.*;

public class PathTest {
    public static void main(String[] args) {
        // 路径
        Path basePath = Paths.get(System.getProperty("user.dir"));
        Path path = Paths.get("src", "AdvancedFeatures", "Chapter2", "resources"); // 使用默认的文件系统分隔符
        Path resPath = basePath.resolve(path);                   // 组合或解析路径
        Path siblingPath = resPath.resolveSibling("temp"); // 产生兄弟路径
        Path relativePath = siblingPath.relativize(basePath);    // 解析相对路径
        Path normalizedPath = relativePath.normalize();          // 移除冗余.和..等
        Path absolutePath = normalizedPath.toAbsolutePath();     // 转为绝对路径
        Path aPath = absolutePath.getFileName();    // （相对路径，无返回null）获得文件名、父目录getParent、根部件getRoot

    }
}
