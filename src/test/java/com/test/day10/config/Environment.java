package com.test.day10.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 环境变量区域
 */
public class Environment {
    //通过Map类型参考postman的环境变量区域设计--支持全局访问
    public static Map<String,Object> env = new HashMap<>();
}
