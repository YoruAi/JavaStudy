package AdvancedFeatures.Chapter5;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 数据库URL：形如jdbc:mysql://localhost:3306/db01
        // 驱动程序：运行时包括到类路径即可
        Class.forName("com.mysql.cj.jdbc.Driver");  // 注册驱动器类（mysql驱动可以自动注册），或-Djdbc.drivers=...
        DriverManager.setLogWriter(new PrintWriter(System.out));    // 获得跟踪信息到控制台

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db01",
                    "root", "111111");  // 最好使用try-with-resource

            // 使用数据库语句 //
            Statement statement = conn.createStatement();
            // 查询
            String command = "SELECT id 编号, name 姓名, gender 性别, BinaryLargeObject FROM tb_student ORDER BY id;";
            ResultSet result = statement.executeQuery(command);
            System.out.println(result.getMetaData().getColumnLabel(2)); // 列描述
            result.findColumn("编号");    // 列序号
            while (result.next()) {
                result.getObject(2, String.class);
                result.getInt("编号");    // 或者1，表示第一列
                result.getString("姓名");
                result.getInt("性别");
                // 二进制大对象BLOB, 字符型大对象CLOB...
                Blob blob = result.getBlob(4);
                if (blob != null)
                    System.out.println(new String(blob.getBytes(1L, (int) blob.length())));
            }
            result.close();
            // 修改
            command = "INSERT INTO tb_student VALUES (null, 'Wang Wu', 1, 'abc')";
            int updateLineCount = statement.executeUpdate(command);     // 返回更新条数
            // 预备语句(涉及变量的查询，可防止注入攻击)
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT id FROM tb_student WHERE gender=?;"); // ?为宿主变量
            preparedStatement.clearParameters();    // 清理参数
            preparedStatement.setInt(1, 0);
            ResultSet result1 = preparedStatement.executeQuery();
            result1.close();
            // 大对象LOB的插入
            Blob BlobWriter = conn.createBlob();
            OutputStream out = BlobWriter.setBinaryStream(1L);
            out.write("long string".getBytes(StandardCharsets.UTF_8));
            PreparedStatement ps = conn.prepareStatement("INSERT INTO tb_student VALUES (null,'n',1,?)");
            ps.setBlob(1, BlobWriter);
            ps.executeUpdate();
            // SQL转义
            // like %!_% {escape '!'}   将!定义为转义字符
            // {d '2008-08-08'}         转义日期时间
            // 其他如外连接，标量函数，存储过程见卷二p254
            // 多结果集
            // 使用statement.getMoreResults();
            // 获取自动生成键
            statement.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int key = rs.getInt(1);
                System.out.println(key);
            }

            // 获取警告
            SQLWarning warning = statement.getWarnings();
            while (warning != null) {
                System.out.println(warning.getMessage());
                warning = warning.getNextWarning();
            }

            statement.close();
            conn.close();   // 或closeOnCompletion所有结果集关闭才关闭
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
            // SQL异常链
            for (Throwable t : e) {
                System.out.println(t.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
