package Fundamentals.Chapter6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

public class CallbackTest {
    public static void main(String[] args) {
        var listener = new TimerPrint();
        Timer t = new Timer(1000, listener);    // 定时器，delay毫秒通知listener一次
        t.start();  // 启用后得以调用actionPerformed

        JOptionPane.showMessageDialog(null, "Quit?");   // *OK对话框
        t.stop();
        System.exit(0);
    }
}

// 实现了ActionListener接口
class TimerPrint implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        // ActionEvent对象提供事件信息(如发生时间getWhen()等)
        System.out.println("The time is " + Instant.ofEpochMilli(event.getWhen())); // *1970以来的ms数
        Toolkit.getDefaultToolkit().beep(); // *提示音
    }
}
