package AdvancedFeatures.Chapter8;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

// 设备InstrumentationAPI提供了安装字节码转换器的挂钩
// JavaAgent是一个代理，在启动前被加载，不修改类文件，仅加载到虚拟机
// 使用清单文件JavaAgentTest.mf设置Premain-class: AdvancedFeatures.Chapter8.JavaAgentTest
// 打包为jar并使用java -javaagent:JavaAgentTest.jar=test.class test调用
public class JavaAgentTest {
    public static void premain(String arg, Instrumentation instr) {
        instr.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader,
                                    String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] data)
                    throws IllegalClassFormatException {
                if (!className.replace("/", ".").equals(arg)) return null;
                var reader = new ClassReader(data);
                var writer = new ClassWriter(
                        ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                var el = new AnnotationBytecodeProcessorTest(writer, className);
                reader.accept(el, ClassReader.EXPAND_FRAMES);
                return writer.toByteArray();
            }
        });
    }
}
