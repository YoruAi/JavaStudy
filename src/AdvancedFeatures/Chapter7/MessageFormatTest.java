package AdvancedFeatures.Chapter7;

import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Locale;

public class MessageFormatTest {
    public static void main(String[] args) {
        // 支持Locale的格式化字符串
        // 默认locale
        String message = MessageFormat.format("On {0,date,long}, {1} pigs. {2,number,currency} money.",
                Date.valueOf(LocalDate.now()), 9, 10.0e8);
        System.out.println(message);

        // 指定locale
        var mf = new MessageFormat("On {0,date,long}, {1} pigs. {2,number,currency} money.", Locale.US);
        String msg = mf.format(new Object[]{Date.valueOf(LocalDate.now()), 9, 10.0e8});
        System.out.println(msg);

        // 高级格式 - choice格式化选项
        String s = MessageFormat.format("{0,choice,0#no pigs|1#one pig|2#{0} pigs}. {1,number,currency} money.",
                1, 10.0e8); // 下限，也可使用{-\u221E<no pigs|0<one pig|2\u2264{0} pigs}
        System.out.println(s);
    }
}
