package com.test.day10.testcases;

import com.test.day10.common.BaseTest;
import com.test.day10.util.ExcelUtil;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Desc: 添加购物车测试
 */
public class AddCartTest extends BaseTest {

    @Test(dataProvider = "getDatas")
    public void test_addCart(ExcelData excelData){
        Response res = request(excelData);
        assertResponse(excelData,res);
    }

    @DataProvider
    public Object[] getDatas(){
        return ExcelUtil.readExcel("添加购物车接口").toArray();
    }

}
