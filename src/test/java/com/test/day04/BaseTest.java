package com.test.day04;

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
        //todo:需求：封装一个能够兼容我们的项目所有请求的代码
        //todo:思路：if...else...,通过请求方法来进行判断
        Response res = null;
        if( excelData.getMethod().equals("get")){
            //todo:如果请求方法是get，发起get接口请求
            res = given().log().all().when().get(excelData.getUrl()+"?"+excelData.getParam()).then().
                    log().all().extract().response();
        }else if(excelData.getMethod().equals("post")){
            String header = excelData.getHeader();
            //todo:json格式字符串转成map类型,map类型就是键值对的形式- Jackson,
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Object> map = null;
            //内部处理异常
            try {
                map = objectMapper.readValue(header, Map.class);
                //todo:header只能传1个请求头,headers方法传入多个请求头，而Map类型多组键值对（多组请求头）
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            res = given().log().all().headers(map).body(excelData.getParam()).when().post(excelData.getUrl()).then().
                    log().all().extract().response();
            //todo:如果请求方法是post，发起post接口请求
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
    public void assertResponse(ExcelData excelData,Response res)  {
        String responseAssert = excelData.getResponseAssert();
        //todo:(2)将json格式的字符串转成map类型,ObjectMapper()是Jackson中的一个类
        ObjectMapper objectMapper = new ObjectMapper();
        //Object能接收任意类型的数据--{"statuscode":200,"nickName":"lemon_auto"}
        Map<String,Object> map = null;
        try {
            map = objectMapper.readValue(responseAssert, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //todo:遍历map集合,keySet()方法获取map集合中所有的键
        Set<String> allkeys = map.keySet();
        for (String key : allkeys) {
            Object value = map.get(key);
            //对键名进行判断
            if(key.equals("statuscode")){
                //对响应状态码进行断言
                Assert.assertEquals(res.statusCode(),value);
            }else if(key.contains("jsonpath")){
                //对响应体文本做断言
                Assert.assertEquals(res.body().asString(),value);}
            else{
                //todo:jsonpath表达式提取响应体字段
                Assert.assertEquals(res.jsonPath().get(key),value);
            }
        }
    }
}
