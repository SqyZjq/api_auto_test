package com.test.day06;

import com.alibaba.excel.EasyExcel;
import com.test.day03.ExcelData;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

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
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata.xlsx").
                head(ExcelData.class).sheet("添加购物车接口").doReadSync();
        //需要把集合类型转换为数组类型
        return allDatas.toArray();
    }

}
