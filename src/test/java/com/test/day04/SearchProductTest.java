package com.test.day04;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.day03.ExcelData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 搜索商品测试
 */
public class SearchProductTest extends BaseTest{
    @Test(dataProvider = "getDatas")
    public void test_search(ExcelData excelData) throws IOException {
        //todo:发起接口请求 excelData里面包含接口请求相关数据：接口地址、请求头、请求参数、请求方法
        //1、通过调用创建商品的接口，创建我们所需要的测试数据
        //2、通过调用数据库插入动作，创建我们所需要的测试数据--前提是你有数据库权限
        //发起接口请求 excelData里面包含接口请求相关数据：接口地址、请求头、请求参数、请求方法
        Response res = request(excelData);
        //下面的断言和LoginTest中的断言是一样的，可以封装到BaseTest中
        /*System.out.println(res.statusCode());
        System.out.println(res.headers());
        System.out.println(res.asString());*/
        //断言
        /*String responseAssert = excelData.getResponseAssert();
        //todo:(2)将json格式的字符串转成map类型,ObjectMapper()是Jackson中的一个类
        ObjectMapper objectMapper = new ObjectMapper();
        //Object能接收任意类型的数据--{"statuscode":200,"nickName":"lemon_auto"}
        Map<String,Object> map = objectMapper.readValue(responseAssert, Map.class);
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
        }*/
        //上面的断言代码可以封装到BaseTest中
        assertResponse(excelData,res);
    }

    @DataProvider
    public Object[] getDatas(){
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata04.xlsx").
                head(ExcelData.class).sheet("搜索商品接口").doReadSync();
        //todo:需要把集合类型转换为数组类型
        return allDatas.toArray();
    }


}
