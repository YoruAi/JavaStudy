package Fundamentals.Chapter6;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

// 高阶内容
public class ProxyTest {
    public static void main(String[] args) {
        String s = "abc" + "ABC";

        var handler = new TraceHandler(s);  // 调用处理器
        var interfaces = new Class[]{Comparable.class};    // 接口

        // 自动创建一个代理类，实现对Object类方法和提供接口中方法的代理
        // 使用该代理类创建代理对象（类加载器，接口数组，调用处理器）
        Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, handler);

        System.out.println(((Comparable) proxy).compareTo("abcABC") + "\t" + proxy.toString());
    }
}

// 调用处理器类
class TraceHandler implements InvocationHandler {
    private Object target;

    public TraceHandler(Object t) {
        target = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 代理工作
        System.out.println(target + "." + method + "(" + Arrays.toString(args) + ")");

        return method.invoke(target, args);
    }
}