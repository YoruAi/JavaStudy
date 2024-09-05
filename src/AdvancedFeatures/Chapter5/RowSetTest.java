package AdvancedFeatures.Chapter5;

import javax.sql.rowset.*;
import java.sql.*;

public class RowSetTest {
    public static void main(String[] args) throws SQLException {
        // 解决了ResultSetTest占用数据库连接的问题，也可将查询结果移动至其他地方
        // 构建行集 //
        // CachedRowSet被缓存行集-允许在断开状态下操作
        // WebRowSet-继承自被缓存行集，可移动保存至xml文件中
        // FilterRowSet, JoinRowSet-操作行集进行SELECT和JOIN操作
        // JdbcRowSet-对接口的瘦包装器，接口

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db01",
                "root", "111111");
        conn.setAutoCommit(false);  // MySQL实现了自动提交，关闭
        RowSetFactory factory = RowSetProvider.newFactory();
        // 被缓存的行集 //
        // 可在断开连接的情况下操作，只有显式请求数据库更改才会再次连接
        // 可通过结果集得到行集
        try (CachedRowSet crs = factory.createCachedRowSet()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tb_student;");
            crs.populate(rs);
            rs.close();
            crs.setTableName("tb_student"); // 指出要写入的表的名称
            crs.next();
            crs.deleteRow();

            crs.acceptChanges(conn);    // 显式将修改写回数据库
        }

        // 或者可以自动创建连接
        try (CachedRowSet crs = factory.createCachedRowSet()) {
            crs.setUrl("jdbc:mysql://localhost:3306/db01");
            crs.setUsername("root");
            crs.setPassword("111111");
            crs.setTableName("tb_student");
            crs.setPageSize(15);            // 若数据集过大则可设定每一页的尺寸
            crs.setCommand("SELECT * FROM tb_student WHERE gender=?");
            crs.setInt(1, 1);
            crs.execute();
            crs.nextPage();                 // 下一页（previous上一页）
            crs.next();
            crs.deleteRow();

            crs.acceptChanges(conn);        // 显式将修改写回数据库（因报错原因这里使用Connection）
        }
    }
}
