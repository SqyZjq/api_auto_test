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
 * @Desc: 搜索商品测试
 */
public class SearchProductTest extends BaseTest{
    @Test(dataProvider = "getDatas")
    public void test_search(ExcelData excelData) throws JsonProcessingException {
        //todo:发起接口请求 excelData里面包含接口请求相关数据：接口地址、请求头、请求参数、请求方法
        Response res = request(excelData);
        /*System.out.println(res.statusCode());
        System.out.println(res.headers());
        System.out.println(res.asString());*/
        //断言
    }

    @DataProvider
    public Object[] getDatas(){
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata.xlsx").
                head(ExcelData.class).sheet("搜索商品接口").doReadSync();
        //todo:需要把集合类型转换为数组类型
        return allDatas.toArray();
    }


}
