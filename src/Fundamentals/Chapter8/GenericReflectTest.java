package Fundamentals.Chapter8;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Scanner;

public class GenericReflectTest {
    // Class类、TypeVariable、WildcardType、ParameterizedType、GenericArrayType都是Type接口的子类型
    public static void learning() throws ReflectiveOperationException {
        // 对于Class对象
        Class<?> cl = GenericTest.class;
        // 获得泛型类型变量
        TypeVariable<?>[] typeParametersClass = cl.getTypeParameters();
        // 获得所声明超类的泛型类型
        Type genericSuperclass = cl.getGenericSuperclass();
        // 获得所声明接口的泛型类型
        Type[] genericInterfaces = cl.getGenericInterfaces();

        // 对于Method对象
        Method method = cl.getMethod("accept", Comparable[].class);
        // 获得泛型类型变量
        TypeVariable<?>[] typeParametersMethod = method.getTypeParameters();
        // 获得泛型 返回类型
        Type genericReturnType = method.getGenericReturnType();
        // 获得泛型 参数类型
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        // 获得泛型 异常类型
        Type[] genericExceptionTypes = method.getGenericExceptionTypes();

        // 对于TypeVariable(描述类型变量)
        for (TypeVariable<?> typeVariable : typeParametersMethod) {
            // 获取类型变量名字
            String name = typeVariable.getName();
            // 获取类型变量的子类限定
            Type[] bounds = typeVariable.getBounds();
        }

        // 对于WildcardType(描述通配符)
        if (genericReturnType instanceof WildcardType) {
            var tmpType = (WildcardType) genericReturnType;
            // 获得子类限定 extends
            Type[] lowerBounds = tmpType.getLowerBounds();
            // 获得超类限定 super
            Type[] upperBounds = tmpType.getUpperBounds();
        }

        // 对于ParameterizedType(描述泛型类或接口类型)
        if (genericSuperclass instanceof ParameterizedType) {
            var tmpType = (ParameterizedType) genericSuperclass;
            // 获得原始类型
            Type rawType = tmpType.getRawType();
            // 获得声明的类型参数
            Type[] actualTypeArguments = tmpType.getActualTypeArguments();
            // 获得外部类
            Type ownerType = tmpType.getOwnerType();
        }

        // 对于GenericArrayType(描述泛型数组)
        if (genericReturnType instanceof GenericArrayType) {
            var tmpType = (GenericArrayType) genericReturnType;
            // 获得泛型 元素类型
            Type genericComponentType = tmpType.getGenericComponentType();
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter class name: ");
        String name = in.next();
        try {
            Class<?> cl = Class.forName(name);
            printClass(cl);             // cl.toGenericString();
            System.out.println(" {");
            System.out.println("\t// Fields");
            for (Field f : cl.getDeclaredFields()) {
                System.out.print("\t");
                printField(f);          // f.toGenericString();
                System.out.println(";");
            }
            System.out.println();
            System.out.println("\t// Constructors");
            for (Constructor<?> c : cl.getDeclaredConstructors()) {
                System.out.print("\t");
                printConstructor(c);    // c.toGenericString();
                System.out.println(";");
            }
            System.out.println();
            System.out.println("\t// Methods");
            for (Method m : cl.getDeclaredMethods()) {
                if (m.getName().contains("lambda")) continue;
                System.out.print("\t");
                printMethod(m);         // m.toGenericString();
                System.out.println(";");
            }
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            System.err.println("Can't find Class " + e.getMessage());
        }
    }

    // 打印类描述
    // fmt: Modifiers class name<T, U extends sc1 & sc2> extends sc implements imp1, imp2
    private static void printClass(Class<?> cl) {
        int modifiers = cl.getModifiers() & Modifier.classModifiers();
        if (modifiers != 0)
            System.out.print(Modifier.toString(modifiers) + " ");

        if (cl.isAnnotation()) {
            System.out.print("@");
        }
        if (cl.isInterface()) {
            System.out.print("interface");
        } else {
            if (cl.isEnum())
                System.out.print("enum");
            else if (cl.isRecord())
                System.out.print("record");
            else
                System.out.print("class");
        }
        System.out.print(" " + cl.getName());

        printTypes(cl.getTypeParameters(), "<", ", ", ">", true);
        Type sc = cl.getGenericSuperclass();
        if (sc != null) {
            System.out.print(" extends ");
            printType(sc, false);
        }
        printTypes(cl.getGenericInterfaces(), " implements ", ", ", "", false);
    }

    // 打印字段描述
    // fmt: Modifiers Type name
    private static void printField(Field f) {
        String Modifiers = Modifier.toString(f.getModifiers());
        if (!Modifiers.isEmpty()) System.out.print(Modifiers + " ");
        printType(f.getGenericType(), false);
        System.out.print(" " + f.getName());
    }

    // 打印构造器描述
    // fmt: Modifiers <Type1, Type2> name(argsType) throws Exception
    private static void printConstructor(Constructor<?> c) {
        String Modifiers = Modifier.toString(c.getModifiers());
        if (!Modifiers.isEmpty()) System.out.print(Modifiers + " ");
        printTypes(c.getTypeParameters(), "<", ", ", "> ", true);
        System.out.print(c.getName() + "(");
        printTypes(c.getGenericParameterTypes(), "", ", ", "", false);
        System.out.print(")");
        printTypes(c.getGenericExceptionTypes(), " throws ", ", ", "", false);
    }

    // 打印方法描述
    // fmt: Modifiers <Type1, Type2> ReturnType name(argsType) throws Exception
    private static void printMethod(Method m) {
        String Modifiers = Modifier.toString(m.getModifiers());
        if (!Modifiers.isEmpty()) System.out.print(Modifiers + " ");
        printTypes(m.getTypeParameters(), "<", ", ", "> ", true);
        printType(m.getGenericReturnType(), false);
        System.out.print(" " + m.getName() + "(");
        printTypes(m.getGenericParameterTypes(), "", ", ", "", false);
        System.out.print(")");
        printTypes(m.getGenericExceptionTypes(), " throws ", ", ", "", false);
    }

    private static void printTypes(Type[] types, String pre, String sep, String suf, boolean isDefinition) {
        // 对extends Object的优化
        if (pre.equals(" extends ") && Arrays.equals(types, new Type[]{Object.class})) return;

        if (types.length > 0) {
            System.out.print(pre);
            for (int i = 0; i < types.length; i++) {
                if (i > 0) System.out.print(sep);
                printType(types[i], isDefinition);
            }
            System.out.print(suf);
        }
    }

    private static void printType(Type type, boolean isDefinition) {
        if (type == null) return;

        if (type instanceof Class) {
            Class<?> componentType = (Class<?>) type;
            int arrayDepth = 0;
            if (componentType.isArray()) {
                do {
                    arrayDepth++;
                    componentType = componentType.getComponentType();
                } while (componentType.isArray());
            }
            System.out.print(componentType.getCanonicalName());
            System.out.print("[]".repeat(arrayDepth));
        } else if (type instanceof TypeVariable) {
            TypeVariable<?> t = (TypeVariable<?>) type;
            System.out.print(t.getName());
            if (isDefinition)
                printTypes(t.getBounds(), " extends ", " & ", "", false);
        } else if (type instanceof WildcardType) {
            WildcardType t = (WildcardType) type;
            System.out.print("?");
            printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
            printTypes(t.getLowerBounds(), " super ", " & ", "", false);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType t = (ParameterizedType) type;
            // Type owner = t.getOwnerType();
            // if (owner != null) {
            //     printType(owner, false);
            //     System.out.print(".");
            // }
            printType(t.getRawType(), false);
            printTypes(t.getActualTypeArguments(), "<", ", ", ">", false);
        } else if (type instanceof GenericArrayType) {
            GenericArrayType t = (GenericArrayType) type;
            printType(t.getGenericComponentType(), isDefinition);
            System.out.print("[]");
        }
    }
}
