package Fundamentals.Chapter7;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.*;

public class LoggerTest {
    // 日志记录器（注意名字层次结构）
    // static防止被回收
    // 配置文件logging.properties位于jdk的conf/或（java 9前）jre/lib/
    // 或java -Djava.util.logging.config.file=... ClassName修改配置文件位置
    public static final Logger logger = Logger.getLogger("Fundamentals.Chapter7.LoggerTest");

    static {
        // 默认只记录前三个级别: SEVERE WARNING INFO
        // 其他: CONFIG FINE FINER FINEST - 用于记录对用户意义不大的调试信息
        // Level.ALL开启所有级别记录
        // Level.OFF关闭所有级别记录
        logger.setLevel(Level.FINER);    // 开启FINE以上级别的记录

        // 处理器Handler
        // 默认ConsoleHandler, 发送到System.err
        // StreamHandler, FileHandler, SocketHandler
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        FileHandler handler;
        try {
            // %h为用户根目录，%u不重复序列号，%t为用户目录\AppData\Local\Temp
            handler = new FileHandler(System.getProperty("user.dir") + "/java%u.log", 50000, 3, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 过滤器(在日志记录器和处理器中都有)
        Filter filter = handler.getFilter();    // 可通过实现该接口的isLoggable(LogRecord record)方法自定义过滤器

        // 格式化器
        // SimpleFormatter使用字符串格式、XMLFormatter使用XML格式（默认）
        handler.setFormatter(new XMLFormatter());

        // 添加绑定处理器
        handler.setLevel(Level.FINER);
        logger.addHandler(handler);
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        // Logger.getGlobal().setLevel(Level.OFF); // 关闭全局日志记录
        Logger.getGlobal().info("GlobalLogger->Main Start");    // 全局日志记录器

        // 日志记录
        logger.log(Level.INFO, "Logger->log");
        logger.fine("logger->Fine");
        logger.logp(Level.FINER, "Fundamentals.Chapter7.LoggerTest", "main", "Enter Method");
        logger.entering("Fundamentals.Chapter7.LoggerTest", "main", new Object[]{Arrays.toString(args)});
        logger.exiting("Fundamentals.Chapter7.LoggerTest", "main");
        try {
            var e = new IOException("IOWrong");
            logger.throwing("Fundamentals.Chapter7.LoggerTest", "main", e);   // 1
            logger.log(Level.WARNING, "IOWrong", e);                                              // 2
            throw e;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
