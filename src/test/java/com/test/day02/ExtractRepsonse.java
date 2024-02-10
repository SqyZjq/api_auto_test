package com.test.day02;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
/**
 * @Desc: 解析响应结果练习
 */
public class ExtractRepsonse {
    public static void main(String[] args) {
        //解析获取token_type 和 access_token这两个字段的值

       /* Response res = given().
                header("Content-Type","application/json").
                body("{\"principal\":\"lemon_auto\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}").
        when().
                post("http://mall.lemonban.com:8107/login").
        then().
                //打印响应日志
                log().all().
                //提取响应结果
                extract().response();
        //http状态码提取
        System.out.println(res.statusCode());
        //响应头部字段提取
        System.out.println(res.header("Set-Cookie"));
        //响应体提取
        System.out.println((String) res.jsonPath().get("access_token"));
        System.out.println((String) res.jsonPath().get("token_type"));*/
        //搜索商品接口
       /* Response res = given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage?" +
                        "prodName=冰箱&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12 ").
        then().
                log().all().extract().response();
        //需求1：提取第一款冰箱的价格
        System.out.println(res.jsonPath().get("records[0].price").toString());*/
        //需求2：提取购物车商品信息里面的商品名字
        //购物车商品信息接口
        /*Response res = given().
                log().all().
                header("Authorization","bearerdb5206e5-ca61-4ee4-bfdd-6c4b89039e07").
                header("Content-Type","application/json; charset=UTF-8").
                body("[]").
        when().
                post("http://mall.lemonban.com:8107/p/shopCart/info").
        then().
                log().all().extract().response();
        System.out.println(res.jsonPath().get("shopCartItemDiscounts[0].shopCartItems[0].prodName[0]").toString());*/
        //需求3：通过商品的名字来获取对应的价格
        /*Response res = given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage?" +
                        "prodName=冰箱&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12 ").
        then().
                log().all().extract().response();
        //jsonpath表达式里面关键字“find”的用法
        //1、find通过条件进行筛选
        //2、如果通过条件找到结果有多个的话，他会默认使用第一个
        //3、如果要找到符合条件的所有的结果，可以使用findAll
        System.out.println((float) res.jsonPath().get("records.findAll{it.prodName == 'mini小冰箱AS_008'}.price[0]"));
        System.out.println((float) res.jsonPath().get("records.findAll{it.prodCommNumber == 0}.price[0]"));*/

        //接口关联！！！！高频面试题
        //文件上传
        /*Response res = given().
                log().all().
                header("Authorization","bearer39cbce92-fa01-49c3-be36-3c939006d9b5").
                multiPart(new File("src/test/resources/111.png")).
        when().
                post("http://mall.lemonban.com:8107/p/file/upload").
        then().
                log().all().extract().response();
        //提取resourcesUrl字段
        String resourcesUrl = res.jsonPath().get("resourcesUrl");
        //提取filePath字段
        String filePath = res.jsonPath().get("filePath");
        System.out.println(resourcesUrl+filePath);
        //保存用户信息
        given().
                log().all().
                header("Content-Type","application/json; charset=UTF-8").
                header("Authorization","bearer39cbce92-fa01-49c3-be36-3c939006d9b5").
                body("{\"avatarUrl\":\""+resourcesUrl+filePath+"\"," +
                        "\"nickName\":\"lemon_auto\",\"userMobile\":\"13323232323\",\"auth\":{}}").
        when().
                put("http://mall.lemonban.com:8107/p/user/setUserInfo").
        then().
                log().all();*/

        /*String a = "hello";
        System.out.println("lemon"+a+"tester");*/

        //要求：实现登录->搜索商品->商品详情页->添加购物车
        //1、登录接口
        Response res = given().
                log().all().
                header("Content-Type","application/json").
                body("{\"principal\":\"lemon_auto\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}").
        when().
                post("http://mall.lemonban.com:8107/login").
        then().
                log().all().extract().response();
        String accessToken = res.jsonPath().get("access_token");
        String tokenType = res.jsonPath().get("token_type");
        String token = tokenType+accessToken;
        //2、搜索商品接口
        given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage?" +
                        "prodName=衣柜&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12 ").
        then().
                log().all();
        //3、商品详情页接口请求
        //4、添加购物车

    }
}
