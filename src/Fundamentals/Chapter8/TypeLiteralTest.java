package Fundamentals.Chapter8;

import java.lang.reflect.*;
import java.util.ArrayList;

// 类型字面量，控制泛型类型的不同"实例"
public class TypeLiteralTest<T> {
    private final Type type;

    public Type getType() {
        return type;
    }

    public TypeLiteralTest() {
        // 得到超类泛型
        Type parentType = getClass().getGenericSuperclass();
        if (parentType instanceof ParameterizedType) {
            // TypeLiteralTest<T>得到T
            type = ((ParameterizedType) parentType).getActualTypeArguments()[0];
        } else {
            throw new UnsupportedOperationException("Construct as new TypeLiteralTest<...>(){};");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof TypeLiteralTest)) return false;
        return type.equals(((TypeLiteralTest<?>) obj).type);
    }

    public static void main(String[] args) {
        // 通过匿名子类
        TypeLiteralTest<?> listType = new TypeLiteralTest<ArrayList<Integer>>() {
        };
        TypeLiteralTest<?> listType2 = new TypeLiteralTest<ArrayList<String>>() {
        };
        //noinspection EqualsBetweenInconvertibleTypes
        System.out.println(listType.equals(listType2));
    }
}
