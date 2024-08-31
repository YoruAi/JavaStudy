package AdvancedFeatures.Chapter6;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.Locale;
import java.util.stream.Stream;

public class TimeTest {
    public static void main(String[] args) {
        // 绝对时间 - 时刻Instant & 时间量Duration：不可修改、精确到纳秒 //
        // Instant实现了Temporal
        Instant time = Instant.now();   // 获取当前时刻
        Instant time2 = time.plusSeconds(3);    // plus/minus(Nanos|...) plus(TemporalAmount)
        // Duration实现了TemporalAmount
        Duration.ofSeconds(3);          // 创建时间间隔
        Duration duration = Duration.between(time, time2);  // 计算时间间隔
        long millis = duration.toMillis();     // toNanos, toMillis, getSeconds, toMinutes...
        duration.negated();     // 产生*-1
        duration.multipliedBy(2);   // dividedBy...
        duration.isZero();      // isNegative
        duration.plusSeconds(5);

        // 人类时间1 - 本地日期时间LocalDate|LocalTime & 时间量Period //
        // LocalDate实现了ChronoLocalDate
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(2000, 1, 1);  // 月份也可使用枚举值Month.January
        long days = birthday.until(today, ChronoUnit.DAYS);         // 计算日期间隔
        birthday = birthday.plusYears(1);
        LocalDate FirstDayOfThisMonth = today.withDayOfMonth(1);    // 替换为给定值
        today.getDayOfMonth();              // getDayOfYear, getMonth(Value), getYear
        today.getDayOfWeek().getValue();    // 获取星期枚举值(1=Monday)
        today.isLeapYear();                 // isBefore|After
        Stream<LocalDate> allDays = birthday.datesUntil(today);
        Stream<LocalDate> allFirstDayInMonths = birthday.datesUntil(today, Period.ofMonths(1)); // 根据步长产生日期流
        // Period实现了TemporalAmount
        Period.of(1, 0, 0);
        // 日期调整器TemporalAdjusters
        LocalDate nextOrSameMon = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)); // 其他调整器详见卷二p289
        today.with(temporal -> {                // 可用lambda实现自己调整器
            var result = (LocalDate) temporal;  // 可使用TemporalAdjusters.ofDateAdjuster创建调整器避免强转
            do {
                result.plusDays(1);
            } while (result.getDayOfWeek().getValue() > 6);
            return result;
        });
        // LocalTime实现了Temporal
        LocalTime rightNow = LocalTime.now();
        LocalTime bedTime = LocalTime.of(23, 30);   // 24小时
        // LocalDateTime结合了两者

        // 人类时间2 - 时区时间ZonedDateTime //
        // 注意夏令时
        ZonedDateTime apollo11Launch = ZonedDateTime.of(1969, 7, 16,
                9, 32, 0, 0,
                ZoneId.of("America/New_York")); // 可查询IANA的time-zones
        apollo11Launch.withZoneSameInstant(ZoneId.of("UTC"));   // 位于同一时刻，Local同一本地时间
        apollo11Launch.toLocalDateTime();
        apollo11Launch.getOffset();                    // 获得与UTC时间的差距
        apollo11Launch.toInstant();                    // 时区时间获得时刻
        time.atZone(ZoneId.of("UTC"));          // 时刻获得时区时间

        // 格式化 //
        String formatted;
        DateTimeFormatter formatter;
        // 预定义格式器(详见卷二p295)
        formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        // locale相关格式器
        formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.US);
        for (DayOfWeek w : DayOfWeek.values()) { // DayOfWeek和Month枚举使用getDisplayName
            System.out.print(w.getDisplayName(TextStyle.SHORT, Locale.CHINA) + " ");
        }
        // 定制格式器(详见卷二p296)
        formatter = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm");
        formatted = formatter.format(apollo11Launch);   // 大部分都实现了的TemporalAccessor接口

        // 解析 //
        // 默认为ISO_预定义标准格式器
        ZonedDateTime.parse("1969-07-16 03:32:00-0400", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxx"));
    }
}
