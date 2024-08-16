package Fundamentals.Chapter6;

import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;

public class LambdaTest {
    public static void main(String[] args) {
        // 使用方法 - 使用函数式接口
        Comparator<String> cmp = (first, second) -> first.length() - second.length();
        ActionListener listener = event -> System.out.println("Time: " + event);
        String[] strings = new String[]{"abc"};
        Arrays.sort(strings, (String first, String second) -> {
            return first.length() - second.length();
        });

        // Predicate接口
        var list = new ArrayList<Integer>(10);
        list.removeIf(e -> e == null);  // 或Objects::isNull

        // Supplier接口
        Objects.requireNonNullElseGet(strings, () -> new String[10]);
        // 区别于 Objects.requireNonNullElse(strings, new String[10]); 仅在需要时调用构造对象

        // 方法引用
        var Timer = new Timer(1000, System.out::println);
        Arrays.sort(strings, String::compareToIgnoreCase);
        // obj::Method          -- (...)->obj.Method(...)
        // Class::Method        -- (first, ...)->first.Method(...)
        // Class::staticMethod  -- (...)->Class.staticMethod(...)

        // 构造器引用 
        // String::new      -- 自动选择构造器
        // String[]::new    -- 等价于 x->new String[x]

        // 再谈Comparator - 如何创建比较器？（可推广至其他函数式接口中的非抽象函数）
        Person[] people = new Person[]{new Person(), new Person()};
        Arrays.sort(people, Comparator.comparing(Person::getName)); // 键提取器函数
        Arrays.sort(people, Comparator.comparing(Person::getFirstName).thenComparing(Person::getLastName));
        Arrays.sort(people, Comparator.comparing(Person::getName,
                (s1, s2) -> Integer.compare(s1.length(), s2.length()))); // 指定比较方法
        Arrays.sort(people, Comparator.comparingInt(p -> p.getName().length()));
        Arrays.sort(people, Comparator.comparing(Person::getMiddleName,
                Comparator.nullsFirst(Comparator.naturalOrder())));
    }
}

class Person {
    private String firstName = "First";
    private String middleName = null;
    private String lastName = "Last";

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
