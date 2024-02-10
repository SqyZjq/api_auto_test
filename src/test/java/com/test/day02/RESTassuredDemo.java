package com.test.day02;
import java.io.File;

import static io.restassured.RestAssured.given;
/**
 * @Desc: REST-assured使用练习
 */
public class RESTassuredDemo {
    public static void main(String[] args) {
        //1、通过REST-assured发起get请求
        /*given().
                queryParam("prodName","衣柜").
                log().all().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage?" +
                        "categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12 ").
        then().
                log().all();*/

        //2、通过REST-assured发起post请求
        //2-1、json传参
        /*given().
                log().all().
                header("Content-Type","application/json").
                header("Authorization","bearere7bf770b-94d0-4122-9c2f-5f4941cc6632").
                body("{\"basketId\":0,\"count\":1,\"prodId\":\"85\",\"shopId\":1,\"skuId\":417}").
        when().
                post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
        then().
                log().all();*/

        //2-2、form表单传参
        /*given().
                log().all().
                header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8").
                body("loginame=admin&password=e10adc3949ba59abbe56e057f20f883e").
        when().
                post("http://erp.lemfix.com/user/login").
        then().
                log().all();*/

        //2-3、上传文件  multipart/form-data（多参数表单）
        given().
                log().all().
                header("Authorization","bearer668b096d-d6a1-49da-b7e1-d920b47e8c5d").
                multiPart(new File("src/test/resources/111.png")).
        when().
                post("http://mall.lemonban.com:8107/p/file/upload").
        then().
                log().all();

        //保存用户信息
        given().
                log().all().
                header("Content-Type","application/json; charset=UTF-8").
                body("{\"avatarUrl\":\"http://mall.lemonban.com:8108/2022/11/542c1874e06e4ea7b8c065bbf7722de7.png\"," +
                        "\"nickName\":\"lemon_auto\",\"userMobile\":\"13323232323\",\"auth\":{}}").
        when().
                put("http://mall.lemonban.com:8107/p/user/setUserInfo").
        then().
                log().all();
    }
}
