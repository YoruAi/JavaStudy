package Fundamentals.Chapter12;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ProcessTest {
    public static Path OutputPath = Path.of("src/Fundamentals/Chapter12/resources/output.txt");
    public static Path ErrorPath = Path.of("src/Fundamentals/Chapter12/resources/errorLog.txt");

    public static void main(String[] args) throws IOException, InterruptedException {
        // 进程 //
        var builder = new ProcessBuilder("cmd.exe", "/C", "dir")
                .directory(Path.of("F:\\Programs\\Java Program\\Practice\\JavaStudy").toFile())  // 改变工作目录
                .redirectOutput(OutputPath.toFile())      // 重定向IO
                .redirectError(ProcessBuilder.Redirect.appendTo(ErrorPath.toFile())); // 设置追加
        // 也可通过.redirectErrorStream(true)合并到输出
        // 修改环境变量
        Map<String, String> env = builder.environment();
        env.put("LANG", "zh_CN");
        env.remove("JAVA_HOME");
        Process process = builder.start();

        // 利用管道（前者的输出作为后者的输入）
        List<Process> processes = ProcessBuilder.startPipeline(List.of(builder));
        System.out.println(new String(processes.get(processes.size() - 1).getInputStream().readAllBytes()));

        // 等待
        if (process.waitFor(1000L, TimeUnit.MILLISECONDS)) {
            int resultCode = process.exitValue();
        } else {
            process.destroyForcibly();  // 杀死进程
        }
        CompletableFuture<Process> exitValue = process.onExit();
        exitValue.thenAccept(p -> System.out.println(p.exitValue()));

        // 进程句柄
        Stream<ProcessHandle> SystemProcesses = ProcessHandle.allProcesses();   // 返回当前进程可见的所有系统进程
        ProcessHandle processHandle = process.toHandle();
        ProcessHandle.current();        // 当前运行java虚拟机的句柄
        long pid = processHandle.pid(); // 获得进程ID
        ProcessHandle.of(pid);          // 根据进程ID获得进程句柄
        Optional<ProcessHandle> parentProcess = processHandle.parent(); // 父进程（快照）
        Stream<ProcessHandle> childProcess = processHandle.children();  // descendants()获取后代进程
        processHandle.isAlive();        // 是否存活

        ProcessHandle.Info info = processHandle.info();     // 获取Info信息（返回值都是Optional对象）
        info.commandLine();     // 返回命令行
        info.user();            // 返回进程的用户
        info.startInstant();    // 开始时刻
        // ...
    }
}
