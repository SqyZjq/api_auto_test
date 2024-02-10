package com.test.day03;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 数据驱动测试练习
 */
public class DDTTest {
    @Test(dataProvider = "getDatas")
    public void test_login(ExcelData excelData) throws IOException {
//        System.out.println(excelData);
        //发起接口请求
        //读取到的是json格式的数据{"Content-Type":"application/json"}
        String header = excelData.getHeader();
        //todo:json格式字符串转成map类型,map类型就是键值对的形式- Jackson,
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.readValue(header, Map.class);
        //todo:header只能传1个请求头,headers方法传入多个请求头，而Map类型多组键值对（多组请求头）
        Response res = given().
                log().all().
                headers(map).
                body(excelData.getParam()).
        when().
                post(excelData.getUrl()).
        then().
                //打印响应日志
                log().all().
                //提取响应结果
                extract().response();
    }

    //todo:@DataProvider注解：数据提供者
    @DataProvider
    public Object[] getDatas(){
        //数据驱动测试：通过数据的变化引起测试结果的改变
        //将数据保存在外部的文件/数据结构中/数据库中，再通过特定的技术读取出来，通过这些测试数据重复运行相同测试代码
        //优点：1、减少代码的冗余 2、方便后期维护
        //设置数据驱动测试 数据源
        //todo:读取Excel中的数据 - easyexcel,head映射到ExcelData实体类的属性值,属性值映射到Excel中的表头
        //todo:实体类：保存从Excel读取的数据,doReadSync()返回的是List集合类型
        List<ExcelData> allDatas = EasyExcel.read("src/test/resources/testdata.xlsx").
                head(ExcelData.class).sheet(0).doReadSync();
        //需要把List集合类型转换为数组类型
        return allDatas.toArray();
    }

}
