package Fundamentals.Chapter12;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsynchronousTest {
    public static void main(String[] args) {
        // 异步 //
        // 可完成Future
        // 方法一
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletableFuture<Integer> result1 = CompletableFuture.supplyAsync(() -> 3, threadPool); // 不能抛出检查型异常
        result1.thenAccept(System.out::println);     // 注册一个回调，结果可用时调用
        result1.whenComplete((i, t) -> {             // 或可处理异常的方法
            if (t == null) System.out.println(i);
            else System.err.println(t.getMessage());
        });
        // 方法二
        // 对同一个结果的计算
        CompletableFuture<Integer> result2 = new CompletableFuture<>();
        result2.isDone();
        threadPool.execute(() -> result2.complete(1));
        threadPool.execute(() -> result2.complete(2));
        threadPool.execute(() -> result2.completeExceptionally(new Throwable("wrong")));
        result2.thenAccept(System.out::println);

        threadPool.shutdown();

        // 组合可完成Future 详见卷一p618-619
        CompletableFuture<String> resultStr = result1.thenApply(Integer::toBinaryString);    // 该方法不阻塞
        // T->CompletableFuture<U>, U->CompletableFuture<V> == T->CompletableFuture<V>
        CompletableFuture<String> resultStr2 = result1.thenCompose(i -> CompletableFuture.supplyAsync(i::toString));
        result1.exceptionally(e -> 0);  // 处理异常，获得假值
        result1.completeOnTimeout(0, 30, TimeUnit.SECONDS); // 处理超时
        CompletableFuture.allOf(result1, result2, resultStr).thenRun(() -> System.out.println("AllDone"));

        // 应用于图形化界面：SwingWorker详见卷一p622-628
    }
}
