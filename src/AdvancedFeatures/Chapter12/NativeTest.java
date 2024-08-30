package AdvancedFeatures.Chapter12;

public class NativeTest {
    // 使用javac -h . *.java自动在目录内生成头文件，根据提供函数编写c程序
    // 编译：
    // gcc -fPIC -I "jdk\include" -I "jdk\include\win32" -shared
    // -o AdvancedFeatures_Chapter12_NativeTest.dll/lib?.so AdvancedFeatures_Chapter12_NativeTest.c
    // 运行：
    // 将动态库添加至库路径中：java -Djava.library.path=. ...
    /* 
    java -Dfile.encoding=UTF-8 -classpath "F:\Programs\Java Program\Practice\JavaStudy\out\production\JavaStudy" 
    -Djava.library.path="F:\Programs\Java Program\Practice\JavaStudy\out\production\JavaStudy\AdvancedFeatures
    \Chapter12\resources" AdvancedFeatures.Chapter12.NativeTest
     */

    static {
        System.loadLibrary("AdvancedFeatures_Chapter12_NativeTest");
    }

    public static native void greeting();
    // 注意：应使用jint、jdouble、jstring(代码中使用(*env)->NewStringUTF(env, char[]);转换)等数据类型。详见卷二p641
    // 注意：关于非静态方法或字段或静态字段访问等详见卷二p648
    // 注意：关于类型和方法的编码签名规则详见卷二p649
    // 注意：关于代码中调用java方法详见卷二p655
    // 注意：关于操作java数组详见卷二p658
    // 注意：关于异常处理详见卷二p660-663

    public static void main(String[] args) {
        greeting();
    }
}
