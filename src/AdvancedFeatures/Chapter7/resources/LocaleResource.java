package AdvancedFeatures.Chapter7.resources;

import java.util.ListResourceBundle;

public class LocaleResource extends ListResourceBundle {
    private static final Object[][] contents = {
            {"size", new double[]{3.14, 1.414}}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
