package com.test.day10.testcases;

import com.test.day10.config.Environment;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * @Desc: 下单支付业务流程
 */
public class OrderPayTest {
    @Test
    public void test_orderpay() {
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
        Environment.env.put("token",token);
        //2、搜索商品接口
        Response res2 = given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage?" +
                        "prodName=衣柜&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12").
        then().
                log().all().extract().response();
        int prodId = res2.jsonPath().get("records[0].prodId");
        Environment.env.put("prodId",prodId);
        //3、商品详情页接口请求
        Response res3 = given().
                log().all().
        when().
                get("http://mall.lemonban.com:8107/prod/prodInfo?prodId=#prodId#").
        then().
                log().all().extract().response();
        int skuId = res3.jsonPath().get("skuList[0].skuId");
        Environment.env.put("skuId",skuId);
        //4、添加购物车
        given().
                log().all().
                header("Authorization","#token#").
                header("Content-Type","application/json; charset=UTF-8").
                body("{\"basketId\":0,\"count\":1,\"prodId\":\"#prodId#\",\"shopId\":1,\"skuId\":#skuId#}").
        when().
                post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
        then().
                log().all();
        //断言-比较实际的结果与期望结果
        //正则表达式？
    }

    public static void main(String[] args) {
        //Java中怎么去使用正则表达式
        //{"basketId":0,"count":1,"prodId":"#prodId#","shopId":1,"skuId":#skuId#,"token":#token#}
        //识别里面#XX#
        /*Environment.env.put("prodId",80);
        Environment.env.put("skuId",200);
        Environment.env.put("token","XXXXXXXXX");
        String str = "{\"basketId\":0,\"count\":1,\"prodId\":\"#prodId#\",\"shopId\":1,\"skuId\":#skuId#,\"token\":#token#}";
        //1、先去对正则表达式进行编译
        System.out.println(str);
        Pattern pattern = Pattern.compile("#(.+?)#");
        //2、通过编译对象进行正则的匹配，得到匹配器
        Matcher matcher = pattern.matcher(str);
        //3、通过匹配器循环遍历 matcher.find()-->返回true就表示找到了对应子串符合正则的要求
        while (matcher.find()){
            //匹配得到的子串
            String subStr = matcher.group(0);
            //4、获取参数名
            //通过正则的分组功能，我们可以通过matcher.group(1)来获取里面的参数名prodId、skuId、token
            String param = matcher.group(1);
            //5、从环境变量区域中取出来对应的参数值
            Object value = Environment.env.get(param);
            //6、把正则匹配的字串（#XXX#）,替换为实际的参数值
            str = str.replace(subStr,value+"");
        }
        System.out.println(str);*/

    }


}
