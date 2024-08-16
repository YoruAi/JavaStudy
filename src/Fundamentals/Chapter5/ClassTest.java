package Fundamentals.Chapter5;

import java.util.Random;

public class ClassTest {
    public static void main(String[] args) throws ReflectiveOperationException {
        var obj = new InheritanceSon("Name", 1);
        Class cl;
        // 1
        cl = obj.getClass();
        // 2
        cl = Class.forName("Fundamentals.Chapter5.InheritanceABC");  // 寻找类
        // 3
        cl = int.class;
        cl = Double[].class;
        cl = InheritanceSon.class;

        // 唯一故比较直接==
        System.out.println(obj.getClass() == InheritanceSon.class);

        // 直接调用构造器
        Random o = Random.class.getConstructor(long.class).newInstance(0L);
        // cast
        InheritanceSuper superObj = InheritanceSon.class.cast(new InheritanceSon("name", 1));
        // Enum
        EnumTest[] values = EnumTest.class.getEnumConstants();

        cl.isArray();           // 查看是否为数组类型
        cl.getComponentType();  // 获取数组元素类型
        cl.isPrimitive();       // 判断是否为基础类型
    }
}
