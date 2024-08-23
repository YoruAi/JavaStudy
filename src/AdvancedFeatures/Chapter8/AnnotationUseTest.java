package AdvancedFeatures.Chapter8;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUseTest {
    @Test(timeout = 10000)
    public void check() {
        System.out.println("checking...");
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        Method m = AnnotationUseTest.class.getDeclaredMethod("check");
        Annotation[] annotations = m.getDeclaredAnnotations();
        Test annotation = m.getAnnotation(Test.class);  // 如果无则返回null
        if (annotation != null) {
            System.out.println(annotation.timeout());
        }
    }
}
