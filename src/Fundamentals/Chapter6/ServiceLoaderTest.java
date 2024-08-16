package Fundamentals.Chapter6;

// 高阶内容
public class ServiceLoaderTest {
    public static void main(String[] args) {
        // 接口的多种实现服务方法选择器

        // 在classpath下的META-INF/services目录下创建文本文件
        // 文件名为接口类名Interface，内容为各种实现类名

        // 初始化服务加载器
        /*
        ServiceLoader<Interface> loader = ServiceLoader.load(Interface.class);
        for (Interface i: loader) { // 懒加载
            if ( ... ) choose i;    // 得到一个服务实例
        }
         */

        // 服务提供者
        // ServiceLoader.Provider::get
        // ServiceLoader.Provider::type
    }
}
