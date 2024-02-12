package com.test.day06;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Desc: JDBC操作练习
 */
public class JDBCUtil {
    public static void main(String[] args) throws SQLException {
        //练习使用DBUtils库来操作数据库-增、删、查询、修改
        //1、通过代码方式与数据库建立连接-数据库地址、账号、密码、端口信息
        Connection conn = getConnection();
        //2、进行查询操作
        QueryRunner queryRunner = new QueryRunner();
        String sql = "SELECT type FROM tz_sms_log WHERE user_phone='13323234433' ORDER BY rec_date DESC LIMIT 1;";
        //query方法需要三个参数：第一个参数数据库连接对象 第二个参数是要执行的sql语句
        //第三个参数：设定你的查询结果集是什么类型--ScalarHandler表示单值查询
        int result = queryRunner.query(conn,sql,new ScalarHandler<>());
        System.out.println(result);
    }

    public static Connection getConnection() {
        //定义数据库连接
        //Oracle：jdbc:oracle:thin:@localhost:1521:DBName
        //SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
        //MySql：jdbc:mysql://localhost:3306/DBName
        //mall.lemonban.com/yami_shops--地址和数据库名字
        String url="jdbc:mysql://mall.lemonban.com/yami_shops?useUnicode=true&characterEncoding=utf-8&useSSL=true";
        String user="lemon";
        String password="lemon123";
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user,password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
