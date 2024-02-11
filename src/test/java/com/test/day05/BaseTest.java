package com.test.day05;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.day03.ExcelData;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 公共测试类
 */
public class BaseTest {

    /**
     * 统一封装，封装一个接口请求的公共方法
     */
    public Response request(ExcelData excelData){
        //类似于postman/Jmeter
        //需求：封装一个能够兼容我们的项目所有请求的代码
        //思路：if...else...,通过请求方法来进行判断
        Response res = null;
        if( excelData.getMethod().equals("get")){
            //如果请求方法是get，发起get接口请求
            res = given().log().all().when().get(excelData.getUrl()+"?"+excelData.getParam()).then().
                    log().all().extract().response();
        }else if(excelData.getMethod().equals("post")){
            String header = excelData.getHeader();
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
            res = given().log().all().headers(map).body(excelData.getParam()).when().post(excelData.getUrl()).then().
                    log().all().extract().response();
            //如果请求方法是post，发起post接口请求
        }else if(excelData.getMethod().equals("put")){
            //后续我们再完善
        }else if(excelData.getMethod().equals("delete")){
            //后续再完善
        }
        return res;
    }


    /**
     * 通用响应断言封装
     * @param excelData excel中的用例数据
     * @param res 接口响应数据
     */
    public void assertResponse(ExcelData excelData,Response res){
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
}
