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
 * @Desc: 登录接口测试
 */
public class LoginTest extends BaseTest{
    @Test(dataProvider = "getDatas")
    public void test_login(ExcelData excelData) throws IOException {
        Response res = request(excelData);
        //断言
        //todo:1、http响应状态码
        //todo: Assert.assertEquals(res.statusCode(),400);
        //todo:2、响应体数据中核心字段
        //Assert.assertEquals(res.jsonPath().get("nickName"),"lemon_auto");
        //todo:3、响应体纯文本数据
        //todo:在Excel中新增一列-数据格式json：{"statuscode":200,"nickName.XXX.YY":"lemon_auto"}
        //约定：
        // todo:1、如果是对http响应状态码做断言。“statuscode”:XXX
        // todo:2、如果是响应体字段json提取断言。“jsonpath表达式(nickName.XXX.YY)”:YYY
        // todo:3、如果是响应体文本断言。“bodyString”:ZZZ
        //todo:(1)获取Excel中的响应断言数据 {"statuscode":200,"nickName.XXX.YY":"lemon_auto"}
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
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata.xlsx").
                head(ExcelData.class).sheet("登录接口").doReadSync();
        //需要把集合类型转换为数组类型
        return allDatas.toArray();
    }

}
