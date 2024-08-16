package Fundamentals.Chapter7;

import java.io.IOException;

public class ThrowableTest extends IOException {
    // 异常层次结构
    /*
    [Throwable]
    - Error         // 运行时系统内部错误/资源耗尽，无能为力
    - Exception     // 重点关注
        - RuntimeException      // 编程错误导致
            - 强制类型转换
            - 数组越界
            - null访问
        - IOException, 其他      // IO或其他错误
            - 文件末尾
            - 打开文件错误
            - 查找Class对象不存在
    - 其他（检查型异常）
     */

    // throws异常规范
    // 检查型异常
    // 子类不能抛出比超类更大更多的异常

    public ThrowableTest() {
    }

    public ThrowableTest(String gripe) {
        super(gripe);
    }
}