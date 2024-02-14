package com.test.day10;

import com.lemon.encryption.MD5Util;
import com.lemon.encryption.RSAManager;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 加密使用的测试
 */
public class EncryptTest {
    public static void main(String[] args) throws Exception{
        //测试加密jar包的使用
        //测试MD5加密
        /*String result =  MD5Util.stringMD5("123456");
        System.out.println(result);
        given().log().all().header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8").
                body("loginame=admin&password="+result).
                when().post("http://erp.lemfix.com/user/login").then().log().all();*/
        //获取时间戳(默认是毫秒)
        //System.out.println(System.currentTimeMillis());
        //获取时间戳（秒级别的）
        //System.out.println(System.currentTimeMillis()/1000);

        //RSA加密算法
        //1、登录接口
        Response res = given().log().all().
                header("X-Lemonban-Media-Type","lemonban.v3").
                header("Content-Type","application/json").
                body("{\"mobile_phone\": \"13323231111\",\"pwd\": \"12345678\"}").
                when().post("http://api.lemonban.com:8788/futureloan/member/login").
                then().log().all().extract().response();
        int member_id = res.jsonPath().get("data.id");
        String token_type = res.jsonPath().get("data.token_info.token_type");
        String token_str = res.jsonPath().get("data.token_info.token");
        String token = token_type+" "+token_str;
        //获取timestamp（秒级别）
        long timestamp = System.currentTimeMillis()/1000;
        //获取sign参数：取 token字段 前 50 位再拼接上 timestamp 值，然后通过 RSA 公钥加密得的字符串
        String subToken = token_str.substring(0,50);
        String sign = RSAManager.encryptWithBase64(subToken+timestamp);
        //2、充值接口
        Response res2 = given().log().all().
                header("X-Lemonban-Media-Type","lemonban.v3").
                header("Content-Type","application/json; charset=UTF-8").
                header("Authorization",token).
                body("{\"member_id\": "+member_id+",\"amount\": 10000.0,\"timestamp\": "+timestamp+",\"sign\": \""+sign+"\"}").
                when().post("http://api.lemonban.com:8788/futureloan/member/recharge").
                then().log().all().extract().response();

    }
}
