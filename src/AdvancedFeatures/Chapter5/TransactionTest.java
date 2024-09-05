package AdvancedFeatures.Chapter5;

import java.sql.*;

// 事务
public class TransactionTest {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db01",
                "root", "111111")) {
            // 提交与回滚
            {
                boolean autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);      // 取消自动提交

                Statement statement = conn.createStatement();
                statement.executeUpdate("INSERT INTO tb_student VALUES (null, 'Zhao Qi', 0, null)");
                Savepoint savepoint = conn.setSavepoint();
                statement.executeUpdate("INSERT INTO tb_student VALUES (null, 'Zhao Qi', 1, null)");

                // conn.rollback();                 // 普通回滚
                conn.rollback(savepoint);           // 返回保存点
                conn.releaseSavepoint(savepoint);   // 及时释放保存点
                conn.commit();                      // 提交

                conn.setAutoCommit(autoCommit);
            }
            // 批量处理(使用事务进行)
            {
                // 可使用DatabaseMetaData对象的supportsBatchUpdate()检查是否支持
                boolean autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);

                Statement statement = conn.createStatement();
                String command = "INSERT INTO tb_student VALUES (null, 'Zhao Qi', 1, null)";
                statement.addBatch(command);
                statement.addBatch(command);
                int[] counts = statement.executeBatch(); // 返回记录数的数组(非负:影响行数;SUCCESS_NO_INFO,EXECUTE_FAILED)
                conn.commit();

                conn.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
