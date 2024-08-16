package Fundamentals.Chapter5;

public class WrapperTest {
    public static void main(String[] args) {
        // 包装器类是final的
        var x = Integer.valueOf(3); // 自动装箱 <- Integer x = 3;
        var y = x.intValue();   // 自动拆箱 <- int y = x;

        System.out.println(x.toString());
        System.out.println(Integer.toString(y));
        int z1 = Integer.parseInt("123");
        Integer z2 = Integer.valueOf("123");
    }
}
