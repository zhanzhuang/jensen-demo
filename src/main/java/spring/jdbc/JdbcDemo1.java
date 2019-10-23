package spring.jdbc;

import java.sql.*;

/**
 * 程序的耦合
 * 耦合:
 *      程序之间的依赖关系,包括类之间的依赖关系/方法之间的依赖关系
 * 解耦:
 *      降低程序之间的依赖关系
 * 实际开发:
 *      应该做到编译器不依赖，程序运行时才依赖
 * 解耦思路：
 *      1.使用反射创建对象，避免使用new关键字
 *      2.通过读取配置文件来获取要创建的对象的全限定类名
 */
public class JdbcDemo1 {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); // 编译期依赖了
//        Class.forName("com.mysql.jdbc.Driver"); // 编译器没有依赖，只是字符串
        // 2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel?serverTimezone=UTC", "root", "123456");
        // 3.获取操作数据库的预处理对象
        PreparedStatement pstm = conn.prepareStatement("select * from account");
        // 4.执行SQL 得到结果集
        ResultSet rs = pstm.executeQuery();
        // 5.遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString( "NAME"));
        }
        // 6.释放资源
        rs.close();
        pstm.close();
        conn.close();
    }
}
