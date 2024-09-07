package Fundamentals.Chapter12;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedTest {
    public static void main(String[] args) {
        // 同步 //
        // 竞态条件
        // 解决方法1-锁
        class LockBank {
            private ReentrantLock lock = new ReentrantLock();   // 重入锁
            private Condition sufficientFunds;                  // 条件对象
            double[] money = {50.0, 100.0, 10.0};

            public LockBank() {
                sufficientFunds = lock.newCondition();  // 一个锁可以有多个关联的条件对象
            }

            public void transfer(int a, int b, double amount) {
                lock.lock();    // 第二个线程遇到时会被阻塞；可多次锁（持有计数）
                try {
                    while (money[a] < amount) {
                        sufficientFunds.await();    // 进入该条件的等待集，
                    }
                    money[a] -= amount;
                    money[b] += amount;
                    System.out.println(a + "to" + b + "-" + amount);
                    sufficientFunds.signalAll();    // 通知等待线程再次检查条件，激活等待的线程
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    // 注意要在finally区释放防止异常导致永远阻塞
                    lock.unlock();
                }
            }
        }
        // 解决方法2-synchronized关键字
        class SyncBank {
            double[] money = {50.0, 100.0, 10.0};
            // 对于每一个线程要创造非独立的实例，则可以使用ThreadLocal，使用时通过get得到属于当前线程的实例
            public static final ThreadLocal<Random> value = ThreadLocal.withInitial(Random::new);

            public SyncBank() {
            }

            // 使用此关键字声明同步自动使用内部对象锁
            public synchronized void transfer(int a, int b, double amount) {
                try {
                    while (money[a] < amount) {
                        wait(); // 使用关联条件（只有一个）
                    }
                    money[a] -= amount;
                    money[b] += amount;
                    System.out.println(a + "to" + b + "-" + amount);
                    notifyAll(); // 等价于signalAll
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        SyncBank syncBank = new SyncBank();
        synchronized (syncBank) {
            // 进入一个同步块，获取该对象的锁
        }
        // volatile字段声明：表明该字段可能被其他线程修改（但不提供原子性，如done = !done;无法保证）
        
        // 限制与建议：见卷一p577
    }
}


