package Fundamentals.Chapter12;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.DoublePredicate;

// 任务与线程池
public class ThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Callable有返回值，FutureTask任务 //
        Callable<Integer> task = () -> 3 + 4;   // 使用call()运行任务
        FutureTask<Integer> futureTask = new FutureTask<>(task);    // 实现了Future和Runnable接口
        Thread thread = new Thread(futureTask);
        thread.start();
        // ...
        Integer result = futureTask.get();      // 阻塞直至可获得结果或超过了时间
        futureTask.cancel(true);    // 请求中断任务
        futureTask.isCancelled();
        futureTask.isDone();


        // Executors执行器 //
        // 线程池静态工厂 ThreadPoolExecutor对象（实现了ExecutorService接口）
        ExecutorService threadPool = Executors.newCachedThreadPool();     // 必要时创建新线程
        Executors.newFixedThreadPool(12);        // 固定大小线程池
        Executors.newSingleThreadExecutor();              // 单线程
        Executors.newScheduledThreadPool(12); // 用于可调度执行的固定线程池（schedule, scheduleAtFixedRate...）
        Executors.newWorkStealingPool();                  // 适合fork-join任务的线程池
        if (threadPool instanceof ThreadPoolExecutor threadPoolExecutor) {
            threadPoolExecutor.getLargestPoolSize();
        }

        Future<Integer> future = threadPool.submit(task); // 提交一个Runnable或Callable
        threadPool.shutdown();                            // 需关闭，使线程池不再接受新的任务。shutdownNow()强制关闭


        // 控制任务组 //
        threadPool = Executors.newCachedThreadPool();
        List<Callable<Integer>> tasks = List.of(task, task, task);
        Integer anResult = threadPool.invokeAny(tasks);                 // 只计算得一个答案即可
        List<Future<Integer>> results = threadPool.invokeAll(tasks);    // 阻塞至所有任务完成
        for (Future<Integer> r : results) {
            Integer i = r.get();    // 阻塞至可获得结果
        }
        // 使用阻塞队列管理任务组
        var completionService = new ExecutorCompletionService<Integer>(threadPool);
        for (Callable<Integer> t : tasks) completionService.submit(t);
        for (int i = 0; i < tasks.size(); ++i) completionService.take().get();

        threadPool.shutdown();


        // fork-join框架 //
        // 处理可分解的计算密集型任务
        // 实现一个ForkJoinTask
        class Counter extends RecursiveTask<Integer> {  // RecursiveAction不返回结果
            public static final int THRESHOLD = 1000;

            private double[] values;
            private int from;
            private int to;
            private DoublePredicate filter;

            public Counter(double[] values, int from, int to, DoublePredicate filter) {
                this.values = values;
                this.from = from;
                this.to = to;
                this.filter = filter;
            }

            @Override
            protected Integer compute() {
                if (to - from < THRESHOLD) {
                    int count = 0;
                    for (int i = from; i < to; ++i) {
                        if (filter.test(values[i])) count++;
                    }
                    return count;
                } else {
                    int mid = (from + to) / 2;
                    var first = new Counter(values, from, mid, filter);
                    var second = new Counter(values, mid, to, filter);
                    invokeAll(first, second);
                    return first.join() + second.join();
                }
            }
        }
        int size = 10000000;
        var numbers = new double[size];
        for (int i = 0; i < size; ++i) numbers[i] = Math.random();
        RecursiveTask<Integer> counter = new Counter(numbers, 0, numbers.length, x -> x > 0.5);
        ForkJoinPool Pool = new ForkJoinPool();
        Pool.invoke(counter);
        System.out.println(counter.join() / (double) size);
    }
}
