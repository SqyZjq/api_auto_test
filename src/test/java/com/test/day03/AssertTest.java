package com.test.day03;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 断言练习
 */
public class AssertTest {
    @Test
    public void test_login_no_password(){
        Response res = given().
                header("Content-Type","application/json").
                body("{\"principal\":\"lemon_auto\",\"credentials\":\"\",\"appType\":3,\"loginType\":0}").
        when().
                post("http://mall.lemonban.com:8107/login").
        then().
                //打印响应日志
                        log().all().
                //提取响应结果
                        extract().response();
        Assert.assertEquals(res.statusCode(),400);
        //提取纯文本的响应数据res.body().asString()
        Assert.assertEquals(res.body().asString(),"账号或密码不正确");
    }

    @Test
    public void test_login_success(){
        //1、单接口测试  正向场景 异常场景
        //2、业务流程接口测试
        Response res = given().
                header("Content-Type","application/json").
                body("{\"principal\":\"lemon_auto\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}").
        when().
                post("http://mall.lemonban.com:8107/login").
        then().
                //打印响应日志
                log().all().
                //提取响应结果
                extract().response();
        //TestNG测试框架
        //第一次断言：http响应状态码
        Assert.assertEquals(res.statusCode(),200);
        //第二次断言：响应体字段：nickName
        Assert.assertEquals(res.jsonPath().get("nickName"),"lemon_auto");
        //后续会有数据库断言*/
    }


}
