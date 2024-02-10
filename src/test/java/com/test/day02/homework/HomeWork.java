package com.test.day02.homework;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 作业练习
 */
public class HomeWork {
    public static void main(String[] args) {
        //要求：实现登录->搜索商品->商品详情页->添加购物车
        //1、登录接口
        Response res1 = given().
                log().all().
                header("Content-Type","application/json").
                body("{\"principal\":\"lemon_auto\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}").
        when().
                post("http://mall.lemonban.com:8107/login").
        then().
                log().all().extract().response();
        String accessToken = res1.jsonPath().get("access_token");
        String tokenType = res1.jsonPath().get("token_type");
        String token = tokenType+accessToken;
        //2、搜索商品接口
        Response res2 = given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage?" +
                        "prodName=lucky&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12").
        then().
                log().all().extract().response();
        int prodId = res2.jsonPath().get("records[0].prodId");
        //3、商品详情页接口请求
        Response res3 = given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/prod/prodInfo?prodId="+prodId).
        then().
                log().all().extract().response();
        int skuId = res3.jsonPath().get("skuList[0].skuId");
        //4、添加购物车
        given().
                log().all().
                header("Authorization",token).
                header("Content-Type","application/json; charset=UTF-8").
                body("{\"basketId\":0,\"count\":1,\"prodId\":\""+prodId+"\",\"shopId\":1,\"skuId\":"+skuId+"}").
        when().
                post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
        then().
                log().all();

        //断言-比较实际的结果与期望结果
    }
}
