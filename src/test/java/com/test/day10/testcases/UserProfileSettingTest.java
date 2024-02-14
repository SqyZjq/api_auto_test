package com.test.day10.testcases;

import com.test.day10.common.BaseTest;
import com.test.day10.util.ExcelUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Desc: 用户头像设置流程
 */
public class UserProfileSettingTest extends BaseTest {
    @Test(dataProvider = "getDatas")
    public void test_setUserProfile(ExcelData excelData) {
        request(excelData);
    }

    @DataProvider
    public Object[] getDatas(){
        //需要把集合类型转换为数组类型
        return ExcelUtil.readExcel("修改个人头像").toArray();
    }
}
