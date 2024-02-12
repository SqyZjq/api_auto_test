package com.test.day07.util;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        //2-1、查询单个字段
        /*String sql = "SELECT mobile_code FROM tz_sms_log WHERE user_phone='13323234433' ORDER BY rec_date DESC LIMIT 1;";
        //query方法需要三个参数：第一个参数数据库连接对象 第二个参数是要执行的sql语句
        //第三个参数：设定你的查询结果集是什么类型
        int result = queryRunner.query(conn,sql,new ScalarHandler<>());
        System.out.println(result);*/
        //2-2、查询一条记录
        /*String sql = "SELECT * FROM tz_sms_log WHERE user_phone='13323234444';";
        //Map存储键值对,Object可以接收任意类型
        Map<String,Object> map = queryRunner.query(conn,sql,new MapHandler());
        System.out.println(map.get("content"));*/
        //2-3、查询多条记录
        /*String sql = " SELECT * FROM tz_sms_log;";
        List<Map<String,Object>> allDatas = queryRunner.query(conn,sql,new MapListHandler());
        System.out.println(allDatas.get(1).get("content" ));*/
        System.out.println(querySingleData("select count(*) from tz_sms_log"));
    }

    /**
     * todo:查询单个字段的方法封装--工具类都要用static修饰,无需创建对象,直接调用
     * @param sql 要执行的sql语句
     * @return 查询的结果
     */
    //todo:查询单个字段的方法封装
    //Object可以接收任意类型的数据
    public static Object querySingleData(String sql)  {
        Connection conn = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        Object result = null;
        try {
            result = queryRunner.query(conn,sql,new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭数据库连接
            try {
                DbUtils.close(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return result;
    }


    /**
     * todo:查询一条记录的方法封装
     * @param sql 要执行的sql语句
     * @return 查询的结果
     */
    //Map<String, Object> map是键值对存储,key是String类型，value是Object类型
    public static Map<String, Object> queryOneData(String sql)  {
        Connection conn = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        Map<String,Object> result = null;
        try {
            result = queryRunner.query(conn,sql,new MapHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭数据库连接
            try {
                DbUtils.close(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return result;
    }

    /**
     * todo:查询多条记录的方法封装
     * @param sql 要执行的sql语句
     * @return 查询的结果
     */
    //List<Map<String,Object>>是一个List集合，里面存储的是Map类型的数据,eg:[{"A":1},{},{}]
    public static List<Map<String,Object>> queryMultiDatas(String sql)  {
        Connection conn = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        List<Map<String,Object>> result = null;
        try {
            result = queryRunner.query(conn,sql,new MapListHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("查询多条记录出现异常");
        } finally {
            //关闭数据库连接
            try {
                DbUtils.close(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("关闭数据库连接出现异常");
            }
        }
        return result;
    }

    public static Connection getConnection() {
        //定义数据库连接
        //Oracle：jdbc:oracle:thin:@localhost:1521:DBName
        //SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
        //MySql：jdbc:mysql://localhost:3306/DBName
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
