package com.test.day01;

import static io.restassured.RestAssured.given;

/**
 * @Desc: REST-assured编写代码进行接口测试
 */
public class FirstAuto {
    public static void main(String[] args) {
        //todo:通过REST-assured编写代码进行接口测试
        //todo:语法格式：三段式结构 given...when...then..
        //todo:given()后面一般我们是设置请求头、请求参数
        //todo:when() 后面一般我们是发起接口请求
        //todo:then() 后面一般我们可以解析获取接口的响应结果
        //===================登录接口请求代码============================
        given().
                log().all().
                header("Content-Type","application/json").
                body("{\"principal\":\"lemon_auto\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}").
        when().
                post("http://mall.lemonban.com:8107/login").
        then().
                log().all();

        //===================添加购物车接口请求代码=======================
        given().
                log().all().
                header("Content-Type","application/json").
                header("Authorization","bearere7bf770b-94d0-4122-9c2f-5f4941cc6632").
                body("{\"basketId\":0,\"count\":1,\"prodId\":\"85\",\"shopId\":1,\"skuId\":417}").
                when().
                post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
                then().
                log().all();
    }
}
