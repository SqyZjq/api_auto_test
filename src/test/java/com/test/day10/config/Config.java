package com.test.day10.config;

/**
 * @Desc: 项目的配置文件
 */
public class Config {
    //全局静态常量的命名规则：全部大写，不同单词组成通过下划线分割
    //项目的Base URL
    public static final String BASE_URL = "http://mall.lemonban.com:8107";
    //项目数据库的地址
    public static final String DB_URL = "jdbc:mysql://mall.lemonban.com/yami_shops?useUnicode=true&characterEncoding=utf-8&useSSL=true";
    //账号/密码
    public static final String DB_USERNAME = "lemon";
    public static final String DB_PASSWORD = "lemon123";

    //用例数据文件路径
    public static final String TEST_DATA_FILE="src/test/resources/testdata10.xlsx";
}
