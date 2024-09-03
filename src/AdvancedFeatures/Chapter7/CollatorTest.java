package AdvancedFeatures.Chapter7;

import java.text.CollationKey;
import java.text.Collator;
import java.text.Normalizer;
import java.util.*;

public class CollatorTest {
    public static void main(String[] args) {
        // Locale敏感的比较器 - Collator排序器
        List<String> words = new ArrayList<>(List.of("Abc", "Z", "abc"));
        Locale locale = Locale.getDefault();
        Collator collator = Collator.getInstance(locale);
        words.sort(collator);

        // 排序器强度、规范化(默认D，详见卷二p319)
        // 区分强度(由弱到强)：PRIMARY、SECONDARY、TERTIARY、IDENTICAL(无差别，与分解模式一起使用)
        // 分解模式：规范化程度NO_DECOMPOSITION(不分解)、CANONICAL_DECOMPOSITION(D，分解重音)、FULL_DECOMPOSITION(KD，分解连字)
        CollationKey strKey = collator.getCollationKey("™");    // 可使用getCollationKey()方法提前分解提高效率

        String normalized = Normalizer.normalize("™", Normalizer.Form.NFD); // 在其他地方适用规范化则适用Normalizer
    }
}
