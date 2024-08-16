package Fundamentals.Chapter4;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class LocalDateTest {
    public static void main(String[] args) throws DateTimeException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input the Year: ");
        int yy = in.nextInt();
        System.out.print("Input the Month: ");
        int mm = in.nextInt();
        System.out.print("Input the Day: ");
        int dd = in.nextInt();
        LocalDate date = LocalDate.of(yy, mm, dd);
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();
        date = date.minusDays(today - 1);

        System.out.print(date.getYear() + " ");
        System.out.println(date.getMonth() + ":");
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for (int i = 1; i < date.getDayOfWeek().getValue(); i++)
            System.out.print("    ");
        while (date.getMonthValue() == month) {
            System.out.printf("%3d", date.getDayOfMonth());
            if (date.getDayOfMonth() == today) {
                System.out.print("*");
            } else {
                System.out.print(" ");
            }
            date = date.plusDays(1);
            if (date.getDayOfWeek().getValue() == 1)
                System.out.println();
        }
        if (date.getDayOfWeek().getValue() != 1)
            System.out.println();

    }
}
