package AdvancedFeatures.Chapter2;

import java.nio.charset.*;

public class CharsetTest {
    public static void main(String[] args) {
        Charset charset = StandardCharsets.UTF_8;
        charset = Charset.forName("UTF-8");
        var str = new String("abc".getBytes(), charset);
    }
}