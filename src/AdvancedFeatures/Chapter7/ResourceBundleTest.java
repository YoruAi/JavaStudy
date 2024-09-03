package AdvancedFeatures.Chapter7;

import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;

public class ResourceBundleTest {
    public static void main(String[] args) {
        // 资源包文件名(有层次结构)
        // baseName_language_country 国家相关
        // baseName_language 语言相关

        String resourceBaseName = "AdvancedFeatures.Chapter7.resources.LocaleResource";
        String stringBaseName = "AdvancedFeatures.Chapter7.resources.LocaleString";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBaseName, Locale.CHINA); // 加载资源包(优先类)
        ResourceBundle stringBundle = ResourceBundle.getBundle(stringBaseName, Locale.CHINA);
        // 查找尝试顺序：依次放弃国家、语言，使用默认Locale、查看baseName文件

        double[] LocalSize = (double[]) resourceBundle.getObject("size");
        String LocalString = stringBundle.getString("language");

        String LocalStringMessage = new MessageFormat(stringBundle.getString("comment"), Locale.CHINA)
                .format(new Object[]{1});

        System.out.println(LocalSize[1]);
        System.out.println(LocalString);
        System.out.println(LocalStringMessage);
    }
}
