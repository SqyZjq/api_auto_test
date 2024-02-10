package com.test.day01.homework;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 作业练习
 */
public class HomeWork {
    public static void main(String[] args) {
        //通过REST-assured发起接口请求测试
        //1、搜索商品接口
        given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage?" +
                        "prodName=衣柜&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12 ").
        then().
                log().all();

        //2、商品详情页接口
        given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/prod/prodInfo?prodId=83").
        then().
                log().all();
    }
}
