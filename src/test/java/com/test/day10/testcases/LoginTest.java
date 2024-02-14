package com.test.day10.testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.day10.common.BaseTest;
import com.test.day10.util.ExcelUtil;
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
        assertResponse(excelData,res);
    }

    @DataProvider
    public Object[] getDatas(){
        //需要把集合类型转换为数组类型
        return ExcelUtil.readExcel("登录接口").toArray();
    }

}
