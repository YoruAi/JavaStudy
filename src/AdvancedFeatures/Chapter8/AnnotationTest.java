package AdvancedFeatures.Chapter8;

import java.lang.annotation.*;

// 隐式拓展自Annotation接口: annotationType()方法返回Class描述注解对象
@Target({ElementType.METHOD, ElementType.TYPE})  // 元注解：限制应用项
@Retention(RetentionPolicy.RUNTIME)                     // 元注解：注解保留时长(默认CLASS类文件，SOURCE源代码，RUNTIME运行时)
@Documented                                             // 元注解：指示归档工具收录该注解
@Inherited                                              // 元注解：对类的注解，标识注解会被继承
@Repeatable(AnnotationTests.class)                      // 元注解：可重复注解，需提供一个容器注解。注意需要getAnnotationsByType
public @interface AnnotationTest {
    int[] severity() default 0;

    boolean includeName() default true;

    String assignedTo() default "[none]";

    Class<?> testClass() default Void.class;    // 不允许为null

    enum Status {FINE, BUG}

    Status status() default Status.FINE;

    String logger() default "Fundamentals.Chapter7.LoggerTest";
}

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@interface AnnotationTests {
    AnnotationTest[] value();
}