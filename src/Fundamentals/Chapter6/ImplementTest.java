package Fundamentals.Chapter6;

public class ImplementTest implements InterfaceTest, Comparable<ImplementTest> {
    // 可用instanceof检测

    private double value;

    @Override
    public final int compareTo(ImplementTest other) {
        // 继承中若子类有自己的比较含义则先getClass()比较抛出ClassCastException
        // 若比较含义相同则在超类中定义final比较方法
        InterfaceTest.super.p();    // 若多接口默认方法冲突则需要手动定义方法并选择
        return Double.compare(this.value, other.value);
    }

    @Override
    public double size() {
        return 0.0;
    }
}
