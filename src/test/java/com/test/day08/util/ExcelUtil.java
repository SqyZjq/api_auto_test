package com.test.day08.util;

import com.alibaba.excel.EasyExcel;
import com.test.day08.config.Config;
import com.test.day08.testcases.ExcelData;

import java.util.List;

/**
 * @Desc: Excel文件操作工具类
 */
public class ExcelUtil {
    //读取Excel文件的sheetName
    public static List<ExcelData> readExcel(String sheetName){
        List<ExcelData> allDatas = EasyExcel.read(Config.TEST_DATA_FILE).
                head(ExcelData.class).sheet(sheetName).doReadSync();
        return allDatas;
    }
    //重载,增加sheetNum数
    public static List<ExcelData> readExcel(int sheetNum){
        List<ExcelData> allDatas = EasyExcel.read(Config.TEST_DATA_FILE).
                head(ExcelData.class).sheet(sheetNum-1).doReadSync();
        return allDatas;
    }
}
