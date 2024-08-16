package Fundamentals.Chapter7;

public class AssertTest {
    public static void main(String[] args) {
        // 虚拟机选项-ea启用断言
        // assert condition;
        // assert condition: expression;
        // 结果false则抛出AssertionError异常，表达式将传入形成消息字符串
        int x = 10;
        assert x < 0 : x;
    }
}
