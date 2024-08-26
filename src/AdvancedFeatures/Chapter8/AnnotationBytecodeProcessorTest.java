package AdvancedFeatures.Chapter8;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

import java.io.IOException;
import java.nio.file.*;

// 在[字节码]级别上处理注解
// 通过运行并将要修改的类文件作为参数调用
// 使用asm工具，具体如何实现不考虑
public class AnnotationBytecodeProcessorTest extends ClassVisitor {
    private String className;

    public AnnotationBytecodeProcessorTest(ClassWriter writer, String className) {
        super(Opcodes.ASM5, writer);
        this.className = className;
    }

    public MethodVisitor visitMethod(int access, String methodName, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
        return new AdviceAdapter(Opcodes.ASM5, mv, access, methodName, desc) {
            private String loggerName;

            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return new AnnotationVisitor(Opcodes.ASM5) {
                    public void visit(String name, Object value) {
                        if (desc.equals("LAdvancedFeatures/Chapter8/AnnotationTest;")
                                && name.equals("logger"))
                            loggerName = value.toString();
                    }
                };
            }

            public void onMethodEnter() {
                if (loggerName != null) {
                    visitLdcInsn(loggerName);
                    visitMethodInsn(INVOKESTATIC, "java/util/logging/Logger", "getLogger",
                            "(Ljava/lang/String;)Ljava/util/logging/Logger;", false);
                    visitLdcInsn(className);
                    visitLdcInsn(methodName);
                    visitMethodInsn(INVOKEVIRTUAL, "java/util/logging/Logger", "entering",
                            "(Ljava/lang/String;Ljava/lang/String;)V", false);
                    loggerName = null;
                }
            }
        };
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("USAGE: java AdvancedFeatures.Chapter8.AnnotationBytecodeProcessorTest classFile");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);
        var reader = new ClassReader(Files.newInputStream(path));
        var writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        var bytecodeProcessor = new AnnotationBytecodeProcessorTest(writer, path.toString()
                .replace(".class", "")
                .replaceAll("[/\\\\]", "."));
        reader.accept(bytecodeProcessor, ClassReader.EXPAND_FRAMES);
        Files.write(path, writer.toByteArray());
    }
}