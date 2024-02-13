package com.test.day09.util;

import com.alibaba.excel.EasyExcel;
import com.test.day09.config.Config;
import com.test.day09.testcases.ExcelData;

import java.util.List;

/**
 * @Desc: Excel文件操作工具类
 */
public class ExcelUtil {

    public static List<ExcelData> readExcel(String sheetName){
        List<ExcelData> allDatas = EasyExcel.read(Config.TEST_DATA_FILE).
                head(ExcelData.class).sheet(sheetName).doReadSync();
        return allDatas;
    }

    public static List<ExcelData> readExcel(int sheetNum){
        List<ExcelData> allDatas = EasyExcel.read(Config.TEST_DATA_FILE).
                head(ExcelData.class).sheet(sheetNum-1).doReadSync();
        return allDatas;
    }
}
