package Fundamentals.Chapter5;

public enum EnumTest {  // 为Enum类的子类
    SMALL("S"), MEDIUM("M"), LARGE("L");    // 类的实例

    private String abbr;

    EnumTest(String abbr) {    // 必须私有
        this.abbr = abbr;
    }

    public String getAbbr() {
        return abbr;
    }

    public static void main(String[] args) {
        System.out.println(EnumTest.SMALL.toString());  // "SMALL"
        EnumTest s = Enum.valueOf(EnumTest.class, "SMALL"); // 由枚举值得到枚举类型
        EnumTest[] values = EnumTest.values();  // 枚举值数组
        System.out.println(EnumTest.SMALL.ordinal());   // 枚举索引
        System.out.println(EnumTest.MEDIUM == s);   // 唯一故比较直接 ==
        System.out.println(EnumTest.MEDIUM.compareTo(s));   // 比较顺序大小
    }
}
