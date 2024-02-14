package com.test.day08.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.day08.config.Config;
import com.test.day08.testcases.ExcelData;
import com.test.day09.config.Environment;
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
    //BeforeSuite 会在整个测试套件运行之前运行一次
    @BeforeSuite
    public void setupSuite(){
        //todo:全局初始化一次，在所有用例之前运行,是最先开始运行的
        //todo:RestAssured.baseURI 全局设置项目的访问base url
        RestAssured.baseURI = Config.BASE_URL;
        //todo:构造所需要的全局性质测试数据-比如：Token
        //1、通过调用接口创建
        //2、通过数据库操作（插入/查询）
    }


    /**
     * 统一封装，封装一个接口请求的公共方法
     */
    public Response request(ExcelData excelData){
        //类似于postman/Jmeter
        //需求：封装一个能够兼容我们的项目所有请求的代码
        //思路：if...else...,通过请求方法来进行判断
        //做日志持久化存储配置-将日志保存到文件中
        //PrintStream 打印输出流，将控制台的日志打印输出到文件
        PrintStream printStream = null;
        //创建一个target/log文件夹用来存储所有的接口日志文件
        String logfileDir = System.getProperty("user.dir")+File.separator+"log";
        File file = new File(logfileDir);
        if(!file.exists()){
            //创建该文件夹路径
            file.mkdirs();
        }
        String logfilePath = logfileDir+File.separator+"testlog_"+excelData.getTitle();
        try {
            printStream = new PrintStream(new File(logfilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RestAssured.config = RestAssuredConfig.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
        //做参数替换动作
        //替换三部分请求参数：url、header、param
        String url = excelData.getUrl();
        //判断是否为空
        if(url != null){
            url = replaceParam(url);
        }
        String header = excelData.getHeader();
        if(header != null){
            header = replaceParam(header);
        }
        String param = excelData.getParam();
        if(param != null){
            param = replaceParam(param);
        }


        Response res = null;
        if( excelData.getMethod().equals("get")){
            //如果请求方法是get，发起get接口请求
            res = given().log().all().when().get(url+"?"+param).then().
                    log().all().extract().response();
        }else if(excelData.getMethod().equals("post")){
//            String header = excelData.getHeader();
            //json格式字符串转成map类型 - Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Object> map = null;
            //内部处理异常
            try {
                map = objectMapper.readValue(header, Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            res = given().log().all().headers(map).body(param).when().post(excelData.getUrl()).then().
                    log().all().extract().response();
            //如果请求方法是post，发起post接口请求
        }else if(excelData.getMethod().equals("put")){
            //后续我们再完善
        }else if(excelData.getMethod().equals("delete")){
            //后续再完善
        }
        Allure.descriptionHtml("XXXXXXXXX");
        //接口请求结束之后，将接口日志添加到Allure报告中
        try {
            Allure.addAttachment("接口请求日志&响应日志",new FileInputStream(logfilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }


    /**
     * 通用响应断言封装
     * @param excelData excel中的用例数据
     * @param res 接口响应数据
     */
    public void assertResponse(ExcelData excelData, Response res){
        String responseAssert = excelData.getResponseAssert();
        //(2)把json格式的字符串转为Map ObjectMapper是Jackson库中的一个类：对json数据处理
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = null;
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
        for (String key: allKeys){
            //value：断言的期望值
            Object value = map.get(key);
            //对key（键名）判断，三种状态：statuscode、bodyString、jsonpath表达式
            if(key.equals("statuscode")){
                //对http响应状态码断言、拿到实际http响应状态码
                Assert.assertEquals(res.statusCode(),value);
            }else if(key.equals("bodyString")){
                //对响应体文本做断言、拿到实际响应体文本
                Assert.assertEquals(res.body().asString(),value);
            }else {
                //jsonPath表达式，对响应体字段做断言，拿到实际的响应体字段值
                Assert.assertEquals(res.jsonPath().get(key),value);

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
        Pattern pattern = Pattern.compile("#(.+?)#");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            //group(0)表示匹配到的整个字符串--#(.+?)#
            String subStr = matcher.group(0);
            //group(1)表示匹配到的第一个分组--(.+?)
            String param = matcher.group(1);
            Object value = Environment.env.get(param);
            //假设环境变量 Environment.env 中有一个键值对 "username" -> "admin"，然后你有一个字符串 str = "Hello, #username#"，使用这段代码处理后，str 就会被替换为 "Hello, admin"。
            str = str.replace(subStr, value + "");
            str = str.replace(subStr, value + "");
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }
}
