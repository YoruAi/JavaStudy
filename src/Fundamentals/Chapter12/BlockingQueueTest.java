package Fundamentals.Chapter12;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 阻塞队列
public class BlockingQueueTest {
    private static BlockingQueue<Path> blockingQueue = new ArrayBlockingQueue<Path>(10);
    private static final Path DUMMY = Path.of("");

    public static void main(String[] args) {
        // 方法 //
        // add, offer       添加（前者异常）
        // element, peek    返回队头（前者异常）
        // remove, poll     移除并返回队头（前者异常）
        // put              添加（阻塞）
        // take             移除并返回（阻塞）

        // 阻塞队列 //
        LinkedBlockingQueue linkedBlockingQueue;
        LinkedBlockingDeque linkedBlockingDeque;
        ArrayBlockingQueue arrayBlockingQueue;
        PriorityBlockingQueue priorityBlockingQueue;
        DelayQueue delayQueue;
        LinkedTransferQueue linkedTransferQueue;

        // 并行搜索工具
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter directory: ");
            String directory = in.nextLine();
            System.out.print("Enter Keyword: ");
            String keyword = in.nextLine();

            // 生产者线程
            Runnable enumerator = () -> {
                try {
                    enumerate(Path.of(directory));
                    blockingQueue.put(DUMMY);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {

                }
            };
            new Thread(enumerator).start();

            // 消费者线程
            for (int i = 0; i < 100; ++i) {
                Runnable searcher = () -> {
                    try {
                        var done = false;
                        while (!done) {
                            Path file = blockingQueue.take();   // 会阻塞直至拿到
                            if (file == DUMMY) {
                                blockingQueue.put(file);
                                done = true;
                            } else {
                                search(file, keyword);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {

                    }
                };
                new Thread(searcher).start();
            }
        }
    }

    private static void search(Path file, String keyword) throws IOException {
        try (var in = new Scanner(file, StandardCharsets.UTF_8)) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    System.out.println(file + ": " + lineNumber + "line: " + line.trim());
                }
            }
        }
    }

    private static void enumerate(Path directory) throws IOException, InterruptedException {
        try (Stream<Path> children = Files.list(directory)) {
            for (Path child : children.collect(Collectors.toList())) {
                if (Files.isDirectory(child))
                    enumerate(child);
                else
                    blockingQueue.put(child);
            }
        }
    }
}
