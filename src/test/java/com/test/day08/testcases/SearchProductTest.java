package com.test.day08.testcases;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.day08.common.BaseTest;
import com.test.day08.util.ExcelUtil;
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
        //2、通过调用数据库插入/查询动作，创建我们所需要的测试数据--前提是你有数据库权限
        //获取在数据库中的商品名称
        //发起接口请求 excelData里面包含接口请求相关数据：接口地址、请求头、请求参数、请求方法
        Response res = request(excelData);
        //断言
        assertResponse(excelData,res);
    }

    @DataProvider
    public Object[] getDatas(){
        return ExcelUtil.readExcel("搜索商品接口").toArray();
    }

}
