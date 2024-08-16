package Fundamentals.Chapter5;

public class VarargsTest {
    public static void Print(Object... objs) {
        final int length = objs.length;
        for (Object obj : objs) {
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        VarargsTest.Print("arg1", "arg2");  // 自动装箱 - new Object[] {...}
    }
}
