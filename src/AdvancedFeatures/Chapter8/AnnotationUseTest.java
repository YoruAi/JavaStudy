package AdvancedFeatures.Chapter8;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

import javax.annotation.*;
import javax.annotation.processing.Generated;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class AnnotationUseTest {
    @Override                       // 用于编译的注解：注解覆盖方法
    @Deprecated                     // 用于编译的注解：已过时标签
    @SuppressWarnings("unchecked")  // 用于编译的注解：阻止特定的警告信息
    @Generated("Unknown")           // 用于区分工具生成的源代码
    @PostConstruct              // 用于管理资源的注解：对象构建后被调用
    @PreDestroy                 // 用于管理资源的注解：对象销毁前被调用
    @Resource(name = "mydb")    // 用于管理资源的注解：注入资源
    public boolean equals(Object obj) {
        return (List<@NonNull String>) obj == null;
    }

    @Test(timeout = 10000)
    public void check() {
        System.out.println("checking...");
    }

    @AnnotationTest(severity = {1, 2})   // 若是单值注解可以省略元素名
    public static void main(String[] args) throws ReflectiveOperationException {
        Method m = AnnotationUseTest.class.getDeclaredMethod("check");
        Annotation[] annotations = m.getDeclaredAnnotations();
        Test annotation = m.getAnnotation(Test.class);  // 如果无则返回null
        if (annotation != null) {
            System.out.println(annotation.timeout());
        }
        Arrays.stream(AnnotationUseTest.class
                        .getDeclaredMethod("equals", Object.class)
                        .getDeclaredAnnotations()
                )
                .map(Annotation::annotationType)
                .map(Class::getName)
                .forEach(System.out::println);
    }
}
