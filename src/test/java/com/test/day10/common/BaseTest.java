package com.test.day10.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.day10.config.Config;
import com.test.day10.config.Environment;
import com.test.day10.testcases.ExcelData;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 公共测试类
 */
public class BaseTest {

    @BeforeSuite
    public void setupSuite() {
        //全局初始化一次，最先开始运行的
        //RestAssured.baseURI 全局设置项目的访问base url
        RestAssured.baseURI = Config.BASE_URL;
        //构造所需要的全局性质测试数据-比如：Token
        //1、通过调用接口创建
        //2、通过数据库操作（插入/查询）
    }


    /**
     * 统一封装，封装一个接口请求的公共方法
     */
    public Response request(ExcelData excelData) {
        //类似于postman/Jmeter
        //需求：封装一个能够兼容我们的项目所有请求的代码
        //思路：if...else...,通过请求方法来进行判断
        //做日志持久化存储配置-将日志保存到文件中
        //PrintStream 打印输出流，将控制台的日志打印输出到文件
        //====================================1、日志持久化存储=====================
        PrintStream printStream = null;
        //创建一个target/log文件夹用来存储所有的接口日志文件
        String logfileDir = System.getProperty("user.dir") + "\\log";
        File file = new File(logfileDir);
        if (!file.exists()) {
            //创建该文件夹路径
            file.mkdirs();
        }
        String logfilePath = logfileDir + "\\testlog_" + excelData.getTitle();
        try {
            printStream = new PrintStream(new File(logfilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RestAssured.config = RestAssuredConfig.config().logConfig(LogConfig.logConfig().defaultStream(printStream));

        //=======================2、做参数替换动作=========================
        //替换三部分请求地址、请求头、请求参数
        String url = excelData.getUrl();
        if (url != null) {
            url = replaceParam(url);
        }
        String header = excelData.getHeader();
        if (header != null) {
            header = replaceParam(header);
        }
        String param = excelData.getParam();
        if (param != null) {
            param = replaceParam(param);
        }

        //3、============================发起接口请求==============================
        Response res = null;
        //json格式字符串转成map类型 - Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> headerMap = null;
        if(header != null) {
            //内部处理异常
            try {
                headerMap = objectMapper.readValue(header, Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (excelData.getMethod().equals("get")) {
            //如果请求方法是get，发起get接口请求
            if (header != null) {
                res = given().log().all().headers(headerMap).when().get(url + "?" + param).then().
                        log().all().extract().response();
            } else {
                res = given().log().all().when().get(url + "?" + param).then().
                        log().all().extract().response();
            }

        } else if (excelData.getMethod().equals("post")) {
            //需要判断是否为上传文件的接口请求
            if(header == null){
                res = given().log().all().body(param).when().post(url).then().
                        log().all().extract().response();
            }else if(header.contains("multipart/form-data")) {
                res = given().log().all().headers(headerMap).multiPart(new File(param)).when().post(url).then().
                        log().all().extract().response();
            } else {
                res = given().log().all().headers(headerMap).body(param).when().post(url).then().
                        log().all().extract().response();
            }
            //如果请求方法是post，发起post接口请求
        } else if (excelData.getMethod().equals("put")) {
            //后续我们再完善
            res = given().log().all().headers(headerMap).body(param).when().put(url).then().
                    log().all().extract().response();
        } else if (excelData.getMethod().equals("delete")) {
            //后续再完善
        }

        //=============4、接口请求结束之后，将接口日志添加到Allure报告中=============
        try {
            Allure.addAttachment("接口请求日志&响应日志", new FileInputStream(logfilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //=============5、提取响应字段并且将其保存到环境变量中=================
        // {"prodId":"records[0].prodId","price":"records[0].price"}
        String extractStr = excelData.getExtract();
        if (extractStr != null) {
            //把json格式的字符串转成Map
            Map<String, String> map = null;
            try {
                map = objectMapper.readValue(extractStr, Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //遍历map
            Set<String> allKeys = map.keySet();
            for (String key : allKeys) {
                //实际响应结果字段提取表达式
                Object value = res.jsonPath().get(map.get(key));
                //将提取到的响应字段保存到环境变量中
                Environment.env.put(key, value);
            }
        }

        return res;
    }


    /**
     * 通用响应断言封装
     *
     * @param excelData excel中的用例数据
     * @param res       接口响应数据
     */
    public void assertResponse(ExcelData excelData, Response res) {
        String responseAssert = excelData.getResponseAssert();
        if(responseAssert != null) {
            //(2)把json格式的字符串转为Map ObjectMapper是Jackson库中的一个类：对json数据处理
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = null;
            try {
                map = objectMapper.readValue(responseAssert, Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //(3)循环遍历Map
            //keySet()获取map中的所有键
            Set<String> allKeys = map.keySet();
            for (String key : allKeys) {
                //value：断言的期望值
                Object value = map.get(key);
                //对key（键名）判断，三种状态：statuscode、bodyString、jsonpath表达式
                if (key.equals("statuscode")) {
                    //对http响应状态码断言、拿到实际http响应状态码
                    Assert.assertEquals(res.statusCode(), value);
                } else if (key.equals("bodyString")) {
                    //对响应体文本做断言、拿到实际响应体文本
                    Assert.assertEquals(res.body().asString(), value);
                } else {
                    //jsonPath表达式，对响应体字段做断言，拿到实际的响应体字段值
                    Assert.assertEquals(res.jsonPath().get(key), value);

                }
            }
        }
    }

    /**
     * 从环境变量中取出对应参数并且替换
     *
     * @param str 原始的字符串
     * @return 替换之后的结果
     */
    public static String replaceParam(String str) {
        //#(.+?)# 正则表达式 识别：#XXX#
        //        正则表达式 识别 ${XXX} --参考Jmeter参数引用规则
        //有两次转义过程，1：对Java的反斜杠进行转义，转义成普通反斜杠字符
        //2：对正则里面的$通过反斜杠字符进行转义
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String subStr = matcher.group(0);
            String param = matcher.group(1);
            Object value = Environment.env.get(param);
            str = str.replace(subStr, value + "");
        }
        return str;
    }

    public static void main(String[] args) {
        //去把反斜杠转义为普通反斜杠字符
        System.out.println("\\");
    }
}
