package Fundamentals.Chapter5;

import java.util.Objects;

public class ObjectTest {
    private String name;
    private int id;

    ObjectTest(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null) return false;

        // if (!(otherObject instanceof ObjectTest)) return false; // 类型 - 用于子类有相同的相等性语义
        if (getClass() != otherObject.getClass()) return false; // 类型 - 用于子类有自己的相等性概念

        ObjectTest other = (ObjectTest) otherObject;
        return id == other.id
                && Objects.equals(name, other.name);    // 防止null情况
    }

    @Override
    public int hashCode() {
        // return Objects.hashCode(name) + Integer.hashCode(id);
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return super.toString() // getClass().getName()
                + "[name=" + name
                + ",id=" + id
                + "]";
    }

    public static void main(String[] args) {
        Object obj;
        obj = new int[10];  // 数组
        obj = new InheritanceSuper("obj", 0);

        ObjectTest test = new ObjectTest("NAME", 1);
        System.out.println(test);
        System.out.println(test.getClass().getSuperclass());    // 获取超类信息
    }
}
