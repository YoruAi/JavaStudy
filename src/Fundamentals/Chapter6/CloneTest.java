package Fundamentals.Chapter6;

import java.util.Date;

public class CloneTest implements Cloneable {
    private Date date;

    @Override
    public CloneTest clone() throws CloneNotSupportedException {
        CloneTest cloned = (CloneTest) super.clone();
        cloned.date = (Date) date.clone();  // 深拷贝
        return cloned;
    }
}