package AdvancedFeatures.Chapter10;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderTest extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // byte[] classBytes = Files.readAllBytes(?.class);
        byte[] classBytes = null;
        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);  // 加载字节码
        if (cl == null) throw new ClassNotFoundException(name);
        return cl;
    }

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        String.class.getClassLoader();  // 获取该类的类加载器（如StringBuilder为引导类加载器无法获取）
        ClassLoader.getSystemClassLoader();     // 获取系统类加载器
        ClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file://lib/asm-9.7.jar")}); // 或目录/
        Thread.currentThread().getContextClassLoader();     // 上下文类加载器(一般为系统类加载器,可通过set...设置)

        ClassLoader classLoader = new ClassLoaderTest();
        Class<?> cl = classLoader.loadClass("AdvancedFeatures.Chapter9.ModuleTest"); // 已加载>父加载器>findClass
        System.out.println(cl.getCanonicalName());
    }
}
