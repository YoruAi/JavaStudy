package Fundamentals.Chapter12;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
    public static void main(String[] args) {
        // 线程
        Thread thread = new Thread(() -> {
            try {
                boolean haveMoreWork = true;
                // 检测中断状态
                while (!Thread.currentThread().isInterrupted() && haveMoreWork) {
                    // do more work...
                    Thread.sleep(200);      // 若设置了sleep等，不要检测中断状态，直接捕获异常
                    System.out.println("Good");
                }
            } catch (InterruptedException e) {    // 或使方法抛出异常
                // 处于sleep和wait状态被interrupt
                System.err.println("Bad");
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("End");
            }
        });
        thread.start();

        // 线程状态 //
        // New           新建
        // Runnable      可运行
        // Blocked       阻塞
        // Waiting       等待
        // Timed waiting 计时等待
        // Terminated    终止

        // 操作线程 //
        // Thread.yield();        // 使正在执行的线程交出运行权
        // thread.getState();     // 获取线程状态
        // thread.join(ms);       // 等待线程终止或时间ms

        // 线程属性 //
        // 中断状态属性
        thread.interrupt();     // 请求终止线程（若处于阻塞等待状态则异常中断）
        Thread.interrupted();   // 检测是否被中断，并清楚中断状态
        // 守护线程
        // thread.setDDaemon(true);     // 为其他线程提供服务
        // 线程名
        thread.setName("ThreadInMain");
        // 异常处理
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.err.println(e.getMessage());
            }
        });
        // 线程优先级(不常用)
        // 一般会继承
        thread.setPriority(Thread.MAX_PRIORITY);    // 1~10
    }

    static void ThreadStart() {
        Bank bank = new ThreadTest.Bank();
        Runnable r1 = () -> {
            for (int i = 0; i < 20; ++i) {
                bank.transfer(0, 1, (int) (3 * Math.random()));
                try {
                    Thread.sleep((int) (2000 * Math.random()));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Runnable r2 = () -> {
            for (int i = 0; i < 10; ++i) {
                bank.transfer(1, 2, (int) (3 * Math.random()));
                try {
                    Thread.sleep((int) (3000 * Math.random()));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }

    public static class Bank {
        ArrayList<Integer> money;

        public Bank() {
            money = new ArrayList<>(List.of(100, 50, 10));
        }

        public void transfer(int a, int b, int amount) {
            int ma = money.get(a);
            int mb = money.get(b);
            if (ma < amount) {
                System.err.println("Not Enough!");
                return;
            }
            System.out.println(Thread.currentThread());
            money.set(a, ma - amount);
            money.set(b, mb + amount);
            System.out.print("Transfer " + a + " to " + b + " $" + amount + ", then ");
            System.out.print("0: " + money.get(0) + ";");
            System.out.print("1: " + money.get(1) + ";");
            System.out.print("2: " + money.get(2) + ";");
            System.out.println("\n'''");
        }
    }
}
