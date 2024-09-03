package AdvancedFeatures.Chapter7;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;
import java.util.Currency;
import java.util.Locale;

public class NumberFormatTest {
    public static void main(String[] args) throws ParseException {
        Locale loc = Locale.CHINA;
        NumberFormat numFormat = NumberFormat.getNumberInstance(loc);   // Number数字, Currency货币, Percent百分比
        double amt = 123.55;
        String result = numFormat.format(amt);

        Number input = numFormat.parse(result);   // 不能以空白字符开头，但是数字后有其他字符可以处理
        double r_amt = input.doubleValue();

        NumberFormat.getAvailableLocales();  // 获取可用的格式器的Locale
        numFormat.setParseIntegerOnly(true);   // 设置只对整数解析
        numFormat.setGroupingUsed(true);       // 设置是否会使用十进制分隔符
        numFormat.setMaximumIntegerDigits(10); // 设置整数部分所允许的最大位数

        // 格式化货币 //
        NumberFormat euroFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        euroFormatter.setCurrency(Currency.getInstance("EUR"));     // 仅设置货币标识符，详见卷二p311

        // 格式化日期时间 //
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.LONG)
                .withLocale(loc);
        DayOfWeek first = WeekFields.of(loc).getFirstDayOfWeek();       // 每周的第一天取决于locale
    }
}
