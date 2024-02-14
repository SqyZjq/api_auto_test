package com.test.day05;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.day03.ExcelData;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Desc: 登录接口测试
 */
public class LoginTest extends BaseTest {
    @Test(dataProvider = "getDatas")
    public void test_login(ExcelData excelData) throws JsonProcessingException {
        Response res = request(excelData);
        //断言
        //1、http响应状态码
        //Assert.assertEquals(res.statusCode(),400);
        //2、响应体数据中核心字段
        //Assert.assertEquals(res.jsonPath().get("nickName"),"lemon_auto");
        //3、响应体纯文本数据
        //在Excel中新增一列-数据格式json：{"statuscode":200,"nickName.XXX.YY":"lemon_auto"}
        //约定：
        // 1、如果是对http响应状态码做断言。“statuscode”:XXX
        // 2、如果是响应体字段断言。“jsonpath表达式”:YYY
        // 3、如果是响应体文本断言。“bodyString”:ZZZ
        //(1)获取Excel中的响应断言数据 {"statuscode":200,"nickName.XXX.YY":"lemon_auto"}
        assertResponse(excelData,res);
    }

    @DataProvider
    public Object[] getDatas(){
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata05.xlsx").
                head(ExcelData.class).sheet("登录接口").doReadSync();
        //需要把集合类型转换为数组类型
        return allDatas.toArray();
    }

}
