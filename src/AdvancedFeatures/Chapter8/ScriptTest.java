package AdvancedFeatures.Chapter8;

import javax.script.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.List;

// 仅供演示 //
public class ScriptTest {
    public static void main(String[] args) throws ScriptException, IOException, NoSuchMethodException {
        var manager = new ScriptEngineManager();
        List<ScriptEngineFactory> engineList = manager.getEngineFactories();    // 使用引擎工厂方法
        for (ScriptEngineFactory factory : engineList) {
            System.out.println(factory.getEngineName());
        }
        ScriptEngine engine = manager.getEngineByName("nashorn");     // 直接获取(注意java11后已废弃)

        if (engine != null) {
            engine.eval("let x = 1");
            engine.put("k", 2);
            Reader reader = new FileReader("src/AdvancedFeatures/Chapter8/resources/Script.js");
            engine.eval(reader);
            Object result = engine.eval("x");
            result = engine.get("k");
            engine.getContext().setWriter(new OutputStreamWriter(System.out));
            result = ((Invocable) engine).invokeFunction("greet", "1");

            CompiledScript script = null;
            if (engine instanceof Compilable) {
                script = ((Compilable) engine).compile(reader);
            }
        } else {
            System.out.println("Can't find nashorn engine.");
        }
    }
}
