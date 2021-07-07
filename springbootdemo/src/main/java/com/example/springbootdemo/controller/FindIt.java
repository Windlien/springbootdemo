package com.example.springbootdemo.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * @创建时间：2018/3/12
 * @描述：使用Jsoup获取网页内容
 */
@Controller
public class FindIt {
    public static void main(String[] args) throws IOException {
//    获取编辑推荐页
        Document document= Jsoup.connect("http://blog.csdn.net/yunyu5120/article/details/37822283").userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").timeout(6000).get();
//        body > div.container.clearfix > main > article > h1
        Elements article=document.select("body").select("div.container.clearfix").select("main").select("article");
        Elements url = article.select("h1");
        System.out.println("标题： "+url.text());
//        body > div.container.clearfix > main > article > div.article_bar.clearfix > div > span.time
        Elements time = article.select("div.article_bar.clearfix").select("div").select("span.time");
        System.out.println("时间： "+time.text());
//        #article_content > div
        Elements content = document.getElementById("article_content").select("div");
        System.out.println("内容1： "+content.text());
//        #article_content > div > div > p:nth-child(1) > span
        Elements span = content.select("div").select("p:nth-child(1)").select("span");
        System.out.println("内容2： "+span.text());
    }
}
