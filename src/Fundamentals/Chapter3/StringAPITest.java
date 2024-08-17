package Fundamentals.Chapter3;

public class StringAPITest {
    public static void main(String[] args) {
        // 子串
        String greeting = "Hello";
        String s = greeting.substring(0, 3);
        System.out.println(s);
        s.indexOf("el");
        s.replace("el", "le");

        // 拼接
        System.out.println("The answer is " + 13);
        String all = String.join(" / ", "S", "M", "L");
        System.out.println(all);
        all = all.repeat(3);
        System.out.println(all);

        // 比较
        "hello".equals(greeting);
        "hello".equalsIgnoreCase(greeting);

        // 遍历
        s.length();                             // 代码单元(char - 两字节)
        s.codePointCount(0, s.length());        // 码点(一个或两个代码单元)
        char ch = s.charAt(1);                  // 代码单元
        int cp = s.codePointAt(1);        // 码点
        int index = s.offsetByCodePoints(0, 2); // 查找第n个码点的索引
        int[] cps = s.codePoints().toArray();   // 码点流
        String ss = new String(cps, 0, cps.length);
        /*
        不常用：
        // 正向
        int cp = s.codePointAt(i);
        if (Character.isSupplementaryCodePoint(cp)) i += 2;
        else i++;
        // 反向
        i--;
        if (Character.isSurrogate(s.charAt(i))) i--;
        int cp = s.codePointAt(i);
         */

        // StringBuilder
        StringBuilder builder = new StringBuilder();
        builder.append(ch);
        builder.appendCodePoint(cp);
        builder.setCharAt(1, ch);
        builder.insert(1, "aaa");
        builder.delete(1, 2);
        String sss = builder.toString();
        System.out.println(sss);
    }
}
