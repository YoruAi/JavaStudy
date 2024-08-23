package AdvancedFeatures.Chapter8;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CompileTest {
    public static void main(String[] args) throws ClassNotFoundException {
        // 简单编译 //
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, System.out, System.err,
                "src\\AdvancedFeatures\\Chapter8\\ScriptTest.java");


        // 更复杂的控制 //
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler
                .getStandardFileManager(collector, null, null);
        Iterable<? extends JavaFileObject> sources = fileManager
                .getJavaFileObjectsFromStrings(List.of("src\\AdvancedFeatures\\Chapter8\\ScriptTest.java"));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                collector,
                null,
                null,
                sources
        );
        Boolean success = task.call();
        for (Diagnostic<? extends JavaFileObject> d : collector.getDiagnostics()) {
            System.out.println("Diagnostic: " + d);
        }


        // 从内存中读取代码 //
        class StringSource extends SimpleJavaFileObject {
            private String code;

            StringSource(String name, String code) {
                super(URI.create("string:///" + name.replace('.', '/') + ".java"), Kind.SOURCE);
                this.code = code;
            }

            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
                // 源文件 - 产生代码
                return code;
            }
        }
        List<StringSource> stringSources = List.of(new StringSource("AdvancedFeatures.Chapter8.newClass",
                "package AdvancedFeatures.Chapter8; public class newClass { }"));
        task = compiler.getTask(null, fileManager, collector, List.of("-d", "src"), null, stringSources);
        task.call();


        // 将字节码写出到内存 //
        class ByteArrayClass extends SimpleJavaFileObject {     // 这个类用来持有字节码的字节
            private ByteArrayOutputStream out;

            public ByteArrayClass(String name) {
                super(URI.create("bytes:///" + name.replace('.', '/') + ".class"), Kind.CLASS);
            }

            public byte[] getCode() {
                return out.toByteArray();
            }

            @Override
            public OutputStream openOutputStream() throws IOException {
                // 类文件 - 字节码写入流
                out = new ByteArrayOutputStream();
                return out;
            }
        }
        List<ByteArrayClass> classes = new ArrayList<>();
        JavaFileManager fileManagerByte = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
            // 使用一个文件管理器代理，配置为使用ByteArrayClass类为输出
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                       JavaFileObject.Kind kind, FileObject sibling)
                    throws IOException {
                // 替换写出类文件的文件对象
                if (kind == JavaFileObject.Kind.CLASS) {
                    ByteArrayClass outfile = new ByteArrayClass(className);
                    classes.add(outfile);
                    return outfile;
                } else
                    return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        };
        class ByteArrayClassLoader extends ClassLoader {
            private Iterable<ByteArrayClass> classes;

            public ByteArrayClassLoader(Iterable<ByteArrayClass> classes) {
                this.classes = classes;
            }

            public Class<?> findClass(String name) throws ClassNotFoundException {
                for (ByteArrayClass cl : classes) {
                    if (cl.getName().equals("/" + name.replace('.', '/') + ".class")) {
                        byte[] bytes = cl.getCode();
                        return defineClass(name, bytes, 0, bytes.length);
                    }
                }
                throw new ClassNotFoundException();
            }
        }
        task = compiler.getTask(null, fileManagerByte, collector, null, null,
                List.of(new StringSource("x.newClass", "package x; public class newClass {}")));
        task.call();
        ByteArrayClassLoader loader = new ByteArrayClassLoader(classes);
        Class<?> cl = Class.forName("x.newClass", true, loader);
        // 或cl = loader.loadClass("x.newClass");
    }
}
