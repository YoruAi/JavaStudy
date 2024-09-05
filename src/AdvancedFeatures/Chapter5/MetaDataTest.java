package AdvancedFeatures.Chapter5;

import java.sql.*;

// 元数据（描述数据库及其组成部分）
public class MetaDataTest {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db01",
                "root", "111111")) {
            // 获取数据库相关信息（详见卷二p273）
            DatabaseMetaData databaseMeta = conn.getMetaData();
            ResultSet mrs = databaseMeta.getTables(null, null, null,
                    new String[]{"TABLE"}); // 包含数据库表信息的结果集
            while (mrs.next()) {
                mrs.getString(3);   // 第三列-表的名称
            }

            // 获取结果集的相关信息
            ResultSetMetaData rsMeta = conn.createStatement().executeQuery("SELECT * FROM tb_student;")
                    .getMetaData();
            for (int i = 1; i <= rsMeta.getColumnCount(); ++i) {
                String columnName = rsMeta.getColumnLabel(i);       // 建议名称
                int columnWidth = rsMeta.getColumnDisplaySize(i);   // 最大宽度
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
