package Fundamentals.Chapter6;

public class InnerClassTest {
    public class InnerClass {   // 可private（包不可见）
        // 静态字段需是final
        // 不能有静态方法
        public void test() {
            // outerClass.this -- 外围类引用
            // 此处可省略为flag
            if (InnerClassTest.this.flag) System.out.println("flag");
        }

        // 默认构造器格式为InnerClass(outerClass outerObject);
    }

    private boolean flag;

    public void start() {
        // outerObj.new InnerClass() 指定该对象的外围类引用为outerObj -- 隐式参数InnerClass(outerObj)
        this.new InnerClass();  // this.默认可省略

        // 外部的公共内部类引用
        // outerClass.InnerClass
        InnerClassTest.InnerClass innerObj;

        // 局部内部类 - 对外部完全隐藏
        class InnerMethodClass {
            public void test() {
                System.out.println("test");
            }
        }

        // 匿名内部类
        // new superType(args) { ... }  -- superType可以是接口或一个(超)类
        var unnamedObj = new Person() {
            // 无构造器但可以使用初始化块 { ... } - 不常用的小技巧“双括号初始化”
            // 作为Person的子类
            private int money;

            public int getMoney() {
                return money;
            }
        };
    }

    // 在静态方法中需要使用静态内部类，无外围类引用；外部可使用InnerClassTest.Flag（嵌套类）
    public static class Flag {
        // 可有静态字段与方法
        private boolean flag;
    }

    public static Flag getPair() {
        return new Flag();
    }


    public static void main(String[] args) {
        // 静态方法获取类名 - getEnclosingClass()得到外围类
        System.out.println(new Object() {
        }.getClass().getEnclosingClass());
    }
}
