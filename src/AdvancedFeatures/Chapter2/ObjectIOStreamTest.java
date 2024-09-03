package AdvancedFeatures.Chapter2;

import Fundamentals.Chapter5.InheritanceSuper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectIOStreamTest implements Externalizable, Cloneable {
    final static private String DataFile = "src\\AdvancedFeatures\\Chapter2\\resources\\TestInput.dat";

    private int information;
    transient private InheritanceSuper person;      // 标记为瞬态域禁止其被序列化

    // 实现Serializable，可自定义序列化方法
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();    // 写出对象描述符和域
        out.writeUTF(person.getName());
        out.writeDouble(person.getSalary());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();      // 写入对象描述符和域
        String name = in.readUTF();
        double salary = in.readDouble();
        this.person = new InheritanceSuper(name, salary);
    }

    // 或实现Externalizable自定义外部化方法
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(information);
        out.writeUTF(person.getName());
        out.writeDouble(person.getSalary());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        information = in.readInt();
        String name = in.readUTF();
        double salary = in.readDouble();
        this.person = new InheritanceSuper(name, salary);
    }

    // 对于遗留代码的旧枚举类型（单例设计模式）需要，见卷二p81
    @Serial
    protected Object readResolve() throws ObjectStreamException {
        // if (information == 1) return OldEnum.ONE;
        // ...
        throw new ObjectStreamException() {
        };
    }

    @Override
    public ObjectIOStreamTest clone() throws CloneNotSupportedException {
        // 可使用序列化方法实现对象的深拷贝
        try {
            var bout = new ByteArrayOutputStream();
            try (var objOut = new ObjectOutputStream(bout)) {
                objOut.writeObject(this);
            }
            try (var bin = new ByteArrayInputStream(bout.toByteArray())) {
                var objIn = new ObjectInputStream(bin);
                return (ObjectIOStreamTest) objIn.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            var e2 = new CloneNotSupportedException();
            e2.initCause(e);
            throw e2;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 序列化：必须有Serializable标记接口
        // 对于共享对象，将会通过序列号进行识别
        // 序列化会保存对象的所有非静态、非瞬态字段。

        // 外部化Externalizable接口方法: 完全自定义序列化方法，需要自己处理包括超类数据等（仅自动记录所属类信息）

        var out = new ObjectOutputStream(new FileOutputStream(DataFile));
        var obj = new ArrayList<String>(List.of("a"));
        out.writeObject(obj);
        out.close();

        var in = new ObjectInputStream(new FileInputStream(DataFile));
        @SuppressWarnings("unchecked")
        var objRead = (ArrayList<String>) in.readObject();
    }

    // 版本控制：使用serialver获取指纹控制版本，序列化时才不会报错
    @Serial
    private static final long serialVersionUID = -8054799038545579514L;
}
