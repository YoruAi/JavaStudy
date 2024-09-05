package AdvancedFeatures.Chapter5;

import java.io.*;
import java.sql.*;

public class ResultSetTest {
    public static void main(String[] args) {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306",
                "root", "111111")) {
            // 默认结果集是不可滚动不可更新的，使用如下语句查询可获得可滚动(对数据库更新不敏感)可更新结果集
            conn.getMetaData()  // 检查数据库是否支持该模式
                    .supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("SELECT * FROM db01.tb_student;");
            // 最好在滚动与更新结果集前使用getType()和getConcurrency()检查是否确实可以操作
            // 滚动结果集
            if (rs.getType() == ResultSet.TYPE_SCROLL_INSENSITIVE) {
                rs.previous();          // 第一行则返回false
                rs.relative(5);    // 滚动多行，若超过范围则返回false并不移动
                rs.absolute(3);     // 将游标设置到指定行号（特殊first,last,beforeFirst,afterLast）
                rs.getRow();            // 返回游标所在行号
                rs.isBeforeFirst();     // 检测游标是否在特殊位置（first,last,afterLast）
            }
            // 更新结果集
            if (rs.getConcurrency() == ResultSet.CONCUR_UPDATABLE) {
                // 修改
                rs.updateString(2, "Lu Liu");
                rs.updateRow();         // 最后更新这一行
                // 插入
                rs.moveToInsertRow();   // 移动到插入行
                rs.updateString(2, "Insert Test");
                rs.insertRow();         // 最后插入这一行
                rs.moveToCurrentRow();  // 移回插入前的位置
                // 删除
                rs.deleteRow();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
