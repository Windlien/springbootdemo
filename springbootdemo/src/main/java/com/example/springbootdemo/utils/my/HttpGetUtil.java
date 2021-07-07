package com.example.springbootdemo.utils.my;

import cn.hutool.http.HttpUtil;

public class HttpGetUtil {
    public static void main(String[] args){
        //以调用中国天气网的天气api为例，在实际使用中，可以进行传参
        //这个是简单的http请求，如果需要使用更加复杂的可以参考Hutool开发文档哦
        try{
            String s = HttpUtil.get("http://www.weather.com.cn/data/sk/101010100.html");
            System.out.println(s);
        }catch (Exception e){

        }
        //以调用中国天气网的天气api为例，在实际使用中，可以进行传参
        //这个是简单的http请求，如果需要使用更加复杂的可以参考Hutool开发文档哦
    }
}
