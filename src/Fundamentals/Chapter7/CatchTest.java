package Fundamentals.Chapter7;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class CatchTest {
    public static void test(boolean isWrong) throws ThrowableTest, FileNotFoundException, EOFException {
        // 堆栈轨迹 (Java 9之后)
        StackWalker walker = StackWalker.getInstance();
        walker.forEach(stackFrame -> {
            System.out.println("StackFrame:" + stackFrame);
            System.out.println("File: " + stackFrame.getFileName());
            System.out.println("Line: " + stackFrame.getLineNumber());
            System.out.println("Class: " + stackFrame.getClassName());
            System.out.println("Method: " + stackFrame.getMethodName());
        });

        if (isWrong) throw new ThrowableTest("ThrowGripe.");
        else throw new FileNotFoundException("FileWrongGripe.");
    }

    public static void main(String[] args) throws Throwable {
        try {
            test(false);
        } catch (ThrowableTest original) {
            // 包装异常
            var e = new IOException("ThrowTest error");
            e.initCause(original);
            throw e;
            // 通过e.getCause()获取初始异常
        } catch (FileNotFoundException | EOFException e) {  // 捕获多个异常类型
            System.out.println(e.getClass().getName() + " " + e.getMessage());
            e.printStackTrace(System.out);  // 默认是System.err
        } finally {
            // 总会最后执行，用于清理资源
            System.out.println("Finally.");
        }

        // try-with-Resources语句 - 代替原本两层try块 try{ try{}finally{} }catch(){}
        // 需实现AutoCloseable接口（throws Exception）
        // 也可提供事实最终变量
        try (var in = new Scanner(Path.of("file.txt"), StandardCharsets.UTF_8);
             var out = System.out) {
            while (in.hasNext()) {
                out.println(in.next());
            }
        }
        // 结束后自动调用obj.close()方法，该方法可能抛出的异常将会被抑制并addSuppressed添加到原异常
        // 可对异常使用getSuppressed()方法得到被抑制的异常数组
    }
}