package com.test.day07.util;

import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * @Desc: 生成随机数据的工具类
 */
public class RandomDataUtil {
    /**
     * 生成一个没有被注册的手机号码
     * @return 结果
     */
    public static String getUnregisterPhone(){
        //1、得到随机的手机号码
        Faker faker = new Faker(Locale.CHINA);
        String phone = faker.phoneNumber().cellPhone();
        //2、查询数据判断该手机号有没有被注册
        String sql = "SELECT COUNT(*) FROM tz_user WHERE user_mobile = '"+phone+"';";
        Object result = JDBCUtil.querySingleData(sql);
        while(true){
            if((Long)result == 0){
                //如果是没有被注册，那说明符合我们的需求
                //直接结束掉循环，并且方法会将结果返回给调用者
                return phone;
            }else {
                //那说明已经被注册了，需要再重新生成随机的数据->查询数据库
                phone = faker.phoneNumber().cellPhone();
                sql = "SELECT COUNT(*) FROM tz_user WHERE user_mobile = '"+phone+"';";
                result = JDBCUtil.querySingleData(sql);
            }
        }
    }

    /**
     * 生成一个没有被注册的用户名
     * @return 结果
     */
    public static String getUnregisterName(){
        //1、得到随机的用户名
        Faker faker = new Faker();
        String userName = faker.name().firstName();
        //2、查询数据判断该手机号有没有被注册
        String sql = "SELECT COUNT(*) FROM tz_user WHERE nick_name = '"+userName+"';";
        Object result = JDBCUtil.querySingleData(sql);
        while(true){
            if((Long)result == 0){
                //如果是没有被注册，那说明符合我们的需求
                //直接结束掉循环，并且方法会将结果返回给调用者
                return userName;
            }else {
                //那说明已经被注册了，需要再重新生成随机的数据->查询数据库
                userName = faker.name().firstName();
                sql = "SELECT COUNT(*) FROM tz_user WHERE nick_name = '"+userName+"';";
                result = JDBCUtil.querySingleData(sql);
            }
        }
    }
}
