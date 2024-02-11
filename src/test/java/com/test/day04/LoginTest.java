package com.test.day04;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.day03.ExcelData;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 登录接口测试
 */
public class LoginTest extends BaseTest{
    @Test(dataProvider = "getDatas")
    public void test_login(ExcelData excelData) throws JsonProcessingException {
        Response res = request(excelData);
    }

    @DataProvider
    public Object[] getDatas(){
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata.xlsx").
                head(ExcelData.class).sheet("登录接口").doReadSync();
        //需要把集合类型转换为数组类型
        return allDatas.toArray();
    }

}
