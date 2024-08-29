package AdvancedFeatures.Chapter9;

public class ModuleTest {
    public static void main(String[] args) {
        // 模块是包的集合
        // 模块间无层次关系
        // 在代码基目录添加module-info.java文件
        // 内容为
        /*
        module 模块名(一般是基目录名) {
            require java.desktop;   // 依赖模块（不传递 使用transitive传递）（java.base模块默认包含）
            
            exports 包;  // 导出包
            exports/opens 包 to ...;   // 限定导出/开放
            
            opens 包;    // 打开包，允许对包的反射式访问
            
            provides 接口 with    // 服务接口(服务加载的模块化)
                impl1,           // 使用这个接口的实现模块中声明uses 接口;
                impl2;
        }
         */

        // 编译：javac -p 依赖模块路径 基目录/module-info.java 基目录/.../*.java
        // 运行：java --module-path|-p 依赖模块路径:基目录 --module|-m 模块名/类名

        String.class.getModule();   // 通过反射获得模块

        // 自动模块
        // 在模块路径中导入Jar包，若非模块则成为自动模块，若清单中有Automatic-Module-Name则使用，否则自动解析
        // 特征：所有包导出，隐式包含所有模块依赖

        // 可使用@开头的文件保存命令行

        // 聚焦模块，如java.se可导入所有SE的模块

        // jdeps工具可分析jar文件的依赖关系，使用--generate-module-info \path可自动生成依赖信息

        // dot工具可通过jdeps的-dotoutput生成的文件dot -Tpng *.dot > ?.png得到png图

        // jmod工具可构建jmod文件(zip格式)，只在链接时使用
    }
}
