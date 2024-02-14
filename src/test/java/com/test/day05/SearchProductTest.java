package com.test.day05;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.day03.ExcelData;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Desc: 搜索商品测试
 */
public class SearchProductTest extends BaseTest {
    @Test(dataProvider = "getDatas")
    public void test_search(ExcelData excelData) throws JsonProcessingException {
        //在发起接口测试前需要确保有商品的数据在
        //1、通过调用创建商品的接口，创建我们所需要的测试数据
        //2、通过调用数据库插入动作，创建我们所需要的测试数据--前提是你有数据库权限
        //发起接口请求 excelData里面包含接口请求相关数据：接口地址、请求头、请求参数、请求方法
        Response res = request(excelData);
        //断言
        assertResponse(excelData,res);
    }

    @DataProvider
    public Object[] getDatas(){
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata05.xlsx").
                head(ExcelData.class).sheet("搜索商品接口").doReadSync();
        //需要把集合类型转换为数组类型
        return allDatas.toArray();
    }


}
