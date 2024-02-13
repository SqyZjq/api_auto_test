package com.test.day09.testcases;

import com.test.day09.common.BaseTest;
import com.test.day09.util.ExcelUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Desc: 下单支付业务流程
 */
public class OrderPayTest2 extends BaseTest {
    @Test(dataProvider = "getDatas")
    public void test_orderpay(ExcelData excelData) {
        request(excelData);
    }

    @DataProvider
    public Object[] getDatas(){
        //需要把集合类型转换为数组类型
        return ExcelUtil.readExcel("下单支付业务").toArray();
    }

}
