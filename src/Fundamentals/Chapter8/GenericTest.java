package Fundamentals.Chapter8;

import Fundamentals.Chapter5.InheritanceSon;
import Fundamentals.Chapter5.InheritanceSuper;

import java.io.Serializable;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class GenericTest<U> {
    // 泛型类不可拓展Throwable类// 适用读取
    // 不能在静态字段和方法中使用类型变量
    private U first;
    private U second;

    public U getFirst() {
        return first;
    }

    public void setFirst(U first) {
        this.first = first;
    }

    public GenericTest(U first, U second) {
        // 不能使用类型变量new U()构造
        this.first = first;
        this.second = second;
    }

    // 通配符
    // GenericTest<? extends InheritanceSuper>是GenericTest<InheritanceSuper>真正的子类型
    // GenericTest<? super InheritanceSon>是GenericTest<InheritanceSon>真正的超类型
    // 注意该T格式
    public static <T extends Comparable<? super T>> void accept(T[] arr) {
        // 假设一个含有子类的Pair
        GenericTest<InheritanceSon> pair1 = new GenericTest<>(
                new InheritanceSon("name1", 1),
                new InheritanceSon("name2", 2));
        GenericTest<? extends InheritanceSuper> sonPair = pair1;    // 适用读取
        /* 注意：不可提供类型参数，？无法匹配。
           Error - 需要的类型: ? extends InheritanceSuper; 提供的类型: InheritanceSon */
        // sonPair.setFirst(new InheritanceSon("name1", 1));
        /* 但是可以返回，可知超类类型则可以接收 */
        InheritanceSuper resultSon = sonPair.getFirst();

        // 假设一个含有超类的Pair
        GenericTest<InheritanceSuper> pair2 = new GenericTest<>(
                new InheritanceSuper("name1", 1),
                new InheritanceSuper("name2", 2));
        GenericTest<? super InheritanceSon> superPair = pair2;      // 适用写入
        /* 但是可以提供参数，一个超类类型可以接收该子类 */
        superPair.setFirst(new InheritanceSon("name1", 1));
        /* 注意不可返回，？无法匹配未知超类类型，仅可使用Object接收。
           Error - 需要的类型: ? super InheritanceSon; 提供的类型: InheritanceSuper */
        Object resultSuper = superPair.getFirst();
    }

    // 异常规范可使用类型变量
    public static <T extends Throwable> void CatchException(T t) throws T {
        try {
            String string = "temp";
        } catch (Throwable throwable) { // 注意捕获不可使用类型变量
            t.initCause(throwable);
            throw t;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAs(Throwable t) throws T {
        // 使用GenericTest.<RuntimeException>throwAs(e)方法可以将检查型转换为非检查型异常
        // 该方法可用于对Runnable接口中run()的改造
        throw (T) t;
    }

    // 泛型方法，调用方法：GenericTest.<String>getFirstTwo(...); 大多情况可省略类型参数。
    // 若想使用super，需要用通配符，并用<T>捕获通配符实现（通配符捕获必须保证?可被分析为确定的类型）
    @SafeVarargs
    public static <T extends Comparable<?> & Serializable> T[] getFirstTwo(IntFunction<T[]> constr, T... args) {
        if (args.length < 2) return null;
        T[] result = constr.apply(2);
        result[0] = args[0];
        result[1] = args[1];
        return result;
    }

    public static <T> GenericTest<T> makePair(Supplier<T> constr) {
        return new GenericTest<>(constr.get(), constr.get());
    }

    public static void main(String[] args) {
        // 想要在构造器内使用类型构造，需要调用者提供构造器表达式（Java8之后）
        // Java8之前可以传String.class并使用反射提取构造器
        GenericTest<String> test = GenericTest.makePair(String::new);
        // Java8之前可以传String[]并使用反射Array.newInstance(args.getClass().getComponentType(), newLength);
        String[] result = GenericTest.getFirstTwo(String[]::new, "a", "b", "c");
    }
}