package com.test.day10.testcases;

import com.test.day10.common.BaseTest;
import com.test.day10.util.ExcelUtil;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Desc: 下单支付业务流程
 */
public class OrderPayTest2 extends BaseTest {
    @Test(dataProvider = "getDatas")
    public void test_orderpay(ExcelData excelData) {
        Response res = request(excelData);
        //断言
        assertResponse(excelData,res);
    }

    @DataProvider
    public Object[] getDatas(){
        //需要把集合类型转换为数组类型
        return ExcelUtil.readExcel("下单支付业务").toArray();
    }

}
