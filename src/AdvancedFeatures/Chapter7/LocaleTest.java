package AdvancedFeatures.Chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocaleTest {
    public static void main(String[] args) {
        // locale //
        // 5个部分，详见卷二p301-302 or See: www.w3.org/International/articles/language-tags
        // 语言：由语言代码表示，如zh
        // 可选脚本：由首字母大写的四个字母表示，如Hant繁体中文
        // 可选国家/地区：由两个大写字母或三个数字表示，如CN
        // 可选变体：很少用
        // 可选拓展：描述日历(n-ca-)和数字(n-nu-)和其他(x-)本地偏好
        Locale usEnglish = Locale.forLanguageTag("en-US");  // 语言标签toLanguageTag()
        usEnglish = new Locale("en", "US");
        Locale china = Locale.CHINA;
        Locale chinese = Locale.CHINESE;    // 仅设定了语言的Locale
        Locale.getAvailableLocales();       // 所有Locale
        Locale.getISOCountries();           // 所有国家代码

        Locale.setDefault(Locale.ENGLISH);
        Locale displayLocale = Locale.getDefault(Locale.Category.DISPLAY);  // 显示信息
        Locale formatLocale = Locale.getDefault(Locale.Category.FORMAT);    // 格式化

        System.out.println(usEnglish.getDisplayName(Locale.TRADITIONAL_CHINESE));   // 输入输出也可指定Locale
        // ScannerObj.useLocale, String.format, PrintWriter.printf都可指定Locale
    }
}
