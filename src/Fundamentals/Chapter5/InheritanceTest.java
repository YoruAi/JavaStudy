package Fundamentals.Chapter5;

public class InheritanceTest {
    public static void main(String[] args) {
        InheritanceSuper[] arr = new InheritanceSuper[5];
        arr[0] = new InheritanceSon("Son", 1000);
        arr[1] = new InheritanceSuper("Super", 100);

        if (arr[1] instanceof InheritanceSon) {    // 超类转化为子类时的检查 - 前者是后者的子类实例(或相同类型实例)
            InheritanceSon tmp = (InheritanceSon) arr[1];
        }

    }
}
