package com.test.day09.testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.day09.common.BaseTest;
import com.test.day09.util.ExcelUtil;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        //需要把集合类型转换为数组类型
        return ExcelUtil.readExcel("登录接口").toArray();
    }

}
