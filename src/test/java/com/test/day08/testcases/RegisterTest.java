package com.test.day08.testcases;

import com.test.day08.config.Config;
import com.test.day08.util.JDBCUtil;
import com.test.day08.util.RandomDataUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 注册测试
 */
public class RegisterTest {

    @Test
    public void register_success(){
        //准备好所需要的测试数据
        String mobile= RandomDataUtil.getUnregisterPhone();
        String userName= RandomDataUtil.getUnregisterName();
        //（1）发送短信验证码接口
        given().log().all().header("Content-Type","application/json; charset=UTF-8").
                body("{\"mobile\":\""+mobile+"\"}").when().
                put("/user/sendRegisterSms").
                then().log().all();
        //（2）查询数据库获取注册的短信验证码内容
        String sql="SELECT mobile_code FROM tz_sms_log WHERE user_phone='"+mobile+"' ORDER BY rec_date DESC LIMIT 1;";
        Object verify_code = JDBCUtil.querySingleData(sql);
        //（3）发送校验短信验证码接口请求
        Response res1 = given().log().all().header("Content-Type","application/json; charset=UTF-8").
                body("{\"mobile\":\""+mobile+"\",\"validCode\":\""+verify_code+"\"}").when().
                put("/user/checkRegisterSms").
                then().log().all().extract().response();
        String checkCode = res1.body().asString();
        //（4）发送真正的注册接口请求
        String param = "{\"appType\":3,\"checkRegisterSmsFlag\":\""+checkCode+"\"," +
                "\"mobile\":\""+mobile+"\",\"userName\":\""+userName+"\"," +
                "\"password\":\"lemon123456\",\"registerOrBind\":1,\"validateType\":1}";
        Response res2 = given().log().all().header("Content-Type","application/json; charset=UTF-8").
                body(param).when().
                put("/user/registerOrBindUser").
                then().log().all().extract().response();
        //接口响应断言
        Assert.assertEquals(res2.statusCode(),200);
        Assert.assertEquals(res2.jsonPath().get("nickName"),userName);
        //通过JDBC的工具方法来查询数据库-断言
        Object result = JDBCUtil.querySingleData("SELECT COUNT(*) FROM tz_user WHERE user_mobile='"+mobile+"';");
        System.out.println(result.getClass());
        Assert.assertEquals(result.toString(),"1");
    }

    public static void main(String[] args) {
        //1、怎么生成一些随机的数据？？？个人地址、手机号码、身份证
        //可以使用第三方库生成我们所需要的随机数据 javafaker
        //切换地区到中国
        /*Faker faker = new Faker(Locale.CHINA);
        System.out.println(faker.address().fullAddress());
        System.out.println(faker.phoneNumber().cellPhone());
        System.out.println(faker.name().lastName());*/
        //得到英文用户名
        //Faker faker = new Faker();

        //System.out.println(faker.name().firstName());
        //2、结合查询数据库判断该用户名 or 手机号有没有被注册
        //思路：得到一个随机的数据->查询数据库？数据有被注册->生成随机数据->查询
        //System.out.println(getUnregisterPhone());
        //System.out.println(getUnregisterName());
    }


}
