package AdvancedFeatures.Chapter7.resources;

import java.util.ListResourceBundle;

public class LocaleResource_zh_CN extends ListResourceBundle {
    private static final Object[][] contents = {
            {"size", new double[]{3.14, 5.21}}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}