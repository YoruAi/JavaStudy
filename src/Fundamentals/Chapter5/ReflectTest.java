package Fundamentals.Chapter5;

import java.util.*;
import java.lang.reflect.*;

// 有缺陷的实现：泛型与多层数组
public class ReflectTest {
    public static void main(String[] args) throws ReflectiveOperationException {
        /*
        getField(String name)           // 指定名字段
        getDeclaredField(String name)   // 指定名字段
        getFields()                     // 返回公共字段(包括超类)
        getDeclaredFields()             // 返回全部字段(不包括超类)
        getMethod(String name, Class... parameterTypes)         // 指定方法
        getDeclaredMethod(String name, Class... parameterTypes) // 指定方法
        getMethods()
        getDeclaredMethods()
        getConstructor(Class... parameterTypes)         // 指定构造器
        getDeclaredConstructor(Class... parameterTypes) // 指定构造器
        getConstructors()
        getDeclaredConstructors()
        getPackageName()    // 返回包名
        
        Field       // 字段
        Method      // 方法
        Constructor // 构造器
        
        getDeclaringClass() // 返回其所在类
        getType()           // 字段类型
        getExceptionTypes() // 返回函数抛出异常类型数组
        getName()           // 名
        getModifiers()      // 返回函数修饰符(Modifiers值，可通过Modifier.toString(m)或isPublic(m)等查看)
        getParameterTypes() // 返回参数类型数组
        getReturnType()     // 返回返回类型
        
        // 对字段与方法的访问、调用
        field.get(Object obj)                 // 获取字段值
        field.set(Object obj, Object newVal)  // 设定字段值
        AccessibleObject    // 超类，field.setAccessible(flag)获取可访问权限
        method.invoke(Object obj, Object... args)   // 若为静态方法参数一可设为null；返回Object(基本类型为包装器)
        constructor.newInstance(Object... args)     // 构造器，返回Object
         */

        String name;
        var in = new Scanner(System.in);
        System.out.print("Enter Class Name: ");
        name = in.next();

        Class cl = null;
        try {
            cl = Class.forName(name);
        } catch (ClassNotFoundException e) {
            System.err.println("No such class!");
            System.exit(-1);
        }
        Class superclass = cl.getSuperclass();
        String modifiers = Modifier.toString(cl.getModifiers());
        if (!modifiers.isEmpty())
            System.out.print(modifiers + " ");
        System.out.print("class " + name);
        if (superclass != null && superclass != Object.class)
            System.out.print(" extends " + superclass.getName());

        System.out.println("\n{");
        printFields(cl);
        System.out.println();
        printConstructors(cl);
        System.out.println();
        printMethods(cl);
        System.out.println("}");
    }

    private static void printFields(Class cl) {
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();
            String name = field.getName();
            String modifiers = Modifier.toString(field.getModifiers());
            System.out.print("\t");
            if (!modifiers.isEmpty())
                System.out.print(modifiers + " ");
            if (type.isArray()) {
                type = type.getComponentType();
                System.out.print(type.getName() + "[] ");
            } else {
                System.out.print(type.getName() + " ");
            }
            System.out.println(name + ";");
        }
    }

    private static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            String name = constructor.getName();
            String modifiers = Modifier.toString(constructor.getModifiers());
            System.out.print("\t");
            if (!modifiers.isEmpty())
                System.out.print(modifiers + " ");
            System.out.print(name + "(");

            Class[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; ++i) {
                if (i > 0) System.out.print(", ");

                Class type = paramTypes[i];
                if (type.isArray()) {
                    type = type.getComponentType();
                    System.out.print(type.getName() + "[]");
                } else {
                    System.out.print(type.getName());
                }
            }
            System.out.print(")");

            Class[] exceptionTypes = constructor.getExceptionTypes();
            for (int i = 0; i < exceptionTypes.length; ++i) {
                if (i > 0) System.out.print(", ");
                else System.out.print(" throws ");
                System.out.print(exceptionTypes[i].getName());
            }

            System.out.println(";");
        }
    }

    private static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            Class returnType = method.getReturnType();
            String name = method.getName();
            String modifiers = Modifier.toString(method.getModifiers());
            System.out.print("\t");
            if (!modifiers.isEmpty())
                System.out.print(modifiers + " ");
            if (returnType.isArray()) {
                returnType = returnType.getComponentType();
                System.out.print(returnType.getName() + "[] ");
            } else {
                System.out.print(returnType.getName() + " ");
            }
            System.out.print(name + "(");

            Class[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; ++i) {
                if (i > 0) System.out.print(", ");

                Class type = paramTypes[i];
                if (type.isArray()) {
                    type = type.getComponentType();
                    System.out.print(type.getName() + "[]");
                } else {
                    System.out.print(type.getName());
                }
            }
            System.out.print(")");

            Class[] exceptionTypes = method.getExceptionTypes();
            for (int i = 0; i < exceptionTypes.length; ++i) {
                if (i > 0) System.out.print(", ");
                else System.out.print(" throws ");
                System.out.print(exceptionTypes[i].getName());
            }

            System.out.println(";");
        }
    }
}
