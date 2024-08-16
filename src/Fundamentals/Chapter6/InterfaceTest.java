package Fundamentals.Chapter6;

public interface InterfaceTest {
    // 默认public方法，public static final字段
    // 无实例
    double size();

    // 超类优先
    // 多接口冲突需要解决二义性冲突
    default double p() {
        return size();
    }
}
