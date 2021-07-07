package com.example.springbootdemo.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建时间：2018/4/2
 * @描述： 爬取银监局网站公布的处罚信息，并存入数据库
 */
@Controller
public class Spider {
    private static String title;
    private static String bookNum;
    private static String personName;
    private static String personCompany;
    private static String companyName;
    private static String companyManager;
    private static String reason;
    private static String law;
    private static String decision;
    private static String organ;
    private static String date;

    @RequestMapping("/spider")
    public void spider() {
        ExcelWrite excelWrite = new ExcelWrite();
        // 银监会机关
//        for(int k=1;k<3;k++){
//            CbrcDownData.third("http://www.cbrc.gov.cn/chinese/home/docViewPage/110002&current="+k,"银监会机关");
//        }
        //银监局
//        for(int i=1;i<99;i++){
//            System.out.println("页数-- "+i);
//            List list = null;
//            System.out.print("页数-- "+i);
//            try {
//                third("http://www.cbrc.gov.cn/zhuanti/xzcf/get2and3LevelXZCFDocListDividePage//1.html?current="+i,"银监局");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                excelWrite.writeToExcel("C:\\Users\\alien\\Desktop\\银监局.xls",list);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("出错了");
//            }
//        }
        //银监分局
        for (int j = 0; j < 247; j++) {
            System.out.println("页数-- " + j);
            List list = null;
            try {
                list = third("http://www.cbrc.gov.cn/zhuanti/xzcf/get2and3LevelXZCFDocListDividePage//2.html?current=" + j, "银监分局");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ExcelWrite.writeToExcel("C:\\Users\\alien\\Desktop\\银监分局.xls", list);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("出错了");
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        ExcelWrite excelWrite = new ExcelWrite();
        // 银监会机关
//        for(int k=1;k<3;k++){
//            CbrcDownData.third("http://www.cbrc.gov.cn/chinese/home/docViewPage/110002&current="+k,"银监会机关");
//        }
        //银监局
//        for(int i=1;i<99;i++){
//            System.out.println("页数-- "+i);
//            List list = null;
//            System.out.print("页数-- "+i);
//            try {
//                third("http://www.cbrc.gov.cn/zhuanti/xzcf/get2and3LevelXZCFDocListDividePage//1.html?current="+i,"银监局");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                excelWrite.writeToExcel("C:\\Users\\alien\\Desktop\\银监局.xls",list);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("出错了");
//            }
//        }
        //银监分局
        for (int j = 0; j < 247; j++) {
            System.out.println("页数-- " + j);
            List list = null;
            try {
                list = third("http://www.cbrc.gov.cn/zhuanti/xzcf/get2and3LevelXZCFDocListDividePage//2.html?current=" + j, "银监分局");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ExcelWrite.writeToExcel("C:\\Users\\alien\\Desktop\\银监分局.xls", list);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("出错了");
            }
        }
        System.exit(0);
    }

    public static List third(String homeUrl, String publisher) throws IOException {
        Document document = (Document) Jsoup.connect(homeUrl).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").timeout(6000).get();
        Element elements = document.getElementById("testUI");
        Elements elements1 = elements.children().select("tr");  //列表所有tr
        List list = new ArrayList();
        for (int i = 0; i < elements1.size(); i++) {
            String url = elements1.get(i).select(" td.cc").select("a").attr("href");
            Document document1 = null;
            try {
                document1 = Jsoup.connect("http://www.cbrc.gov.cn" + url).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").timeout(6000).get();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Elements trs = null;
            if (document1 != null) {
                trs = document1.select("table[class='MsoNormalTable']").select("tr");
            }
            if (trs.text().contains("是否公开")) {
////                    改用循环找指定内容
//                    title =document1.head().select("title").get(0).text();
//                    for(Element tr:trs){
//                       if ( tr.text().contains("行政处罚决定书文号")){
//                           bookNum =tr.select("td").get(1).text(); //行政处罚决定书文号
//                       }else if(tr.text().contains("个人姓名")){
//                           personName=tr.select("td").get(2).text();//个人姓名
//                       }else if(tr.text().contains("个人单位")){
//                           personCompany =  trs.get(2).select("td").get(1).text();  //个人单位
//                       }else if(tr.text().contains("行政处罚决定书文号")){
//
//                       }
//                    }
                continue;
            } else {
                if (trs.size() == 9) {
                    title = document1.head().select("title").get(0).text();
                    bookNum = trs.get(0).select("td").get(1).text(); //行政处罚决定书文号
                    personName = trs.get(1).select("td").get(2).text();//个人姓名
                    personCompany = "";
                    companyName = trs.get(2).select("td").get(2).text();//单位名称
                    companyManager = trs.get(3).select("td").get(1).text();//法定代表人姓名
                    reason = trs.get(4).select("td").get(1).text();//主要违法违规事实（案由）
                    law = trs.get(5).select("td").get(1).text();//行政处罚依据
                    decision = trs.get(6).select("td").get(1).text();//行政处罚决定
                    organ = trs.get(7).select("td").get(1).text();//作出处罚决定的机关名称
                    date = trs.get(8).select("td").get(1).text();//作出处罚决定的日期
                } else if (trs.size() == 10) {
                    title = document1.head().select("title").get(0).text();
                    bookNum = trs.get(0).select("td").get(1).text(); //行政处罚决定书文号
                    personName = trs.get(1).select("td").get(3).text(); //个人姓名
                    personCompany = trs.get(2).select("td").get(1).text();  //个人单位
                    companyName = trs.get(3).select("td").get(2).text();//单位名称
                    companyManager = trs.get(4).select("td").get(1).text();//法定代表人姓名
                    reason = trs.get(5).select("td").get(1).text();//主要违法违规事实（案由）
                    law = trs.get(6).select("td").get(1).text();//行政处罚依据
                    decision = trs.get(7).select("td").get(1).text();//行政处罚决定
                    organ = trs.get(8).select("td").get(1).text();//作出处罚决定的机关名称
                    date = trs.get(9).select("td").get(1).text();//作出处罚决定的日期
                } else if (trs.size() == 8) {
                    title = document1.head().select("title").get(0).text();
                    bookNum = trs.get(0).select("td").get(1).text(); //行政处罚决定书文号
                    personName = ""; //个人姓名
                    personCompany = "";//个人单位
                    companyName = trs.get(1).select("td").get(2).text();//单位名称
                    companyManager = trs.get(2).select("td").get(1).text();//法定代表人姓名
                    reason = trs.get(3).select("td").get(1).text();//主要违法违规事实（案由）
                    law = trs.get(4).select("td").get(1).text();//行政处罚依据
                    decision = trs.get(5).select("td").get(1).text();//行政处罚决定
                    organ = trs.get(6).select("td").get(1).text();//作出处罚决定的机关名称
                    date = trs.get(7).select("td").get(1).text();//作出处罚决定的日期
                } else if (trs.size() == 13) {   //trs.size()==13
                    title = document1.head().select("title").get(0).text();
                    bookNum = trs.get(3).select("td").get(1).text(); //行政处罚决定书文号
                    personName = trs.get(4).select("td").get(2).text(); //个人姓名
                    personCompany = "";//个人单位
                    companyName = trs.get(5).select("td").get(2).text();//单位名称
                    companyManager = trs.get(6).select("td").get(1).text();//法定代表人姓名
                    reason = trs.get(7).select("td").get(1).text();//主要违法违规事实（案由）
                    law = trs.get(8).select("td").get(1).text();//行政处罚依据
                    decision = trs.get(9).select("td").get(1).text();//行政处罚决定
                    organ = trs.get(10).select("td").get(1).text();//作出处罚决定的机关名称
                    date = trs.get(11).select("td").get(1).text();//作出处罚决定的日期
                } else {
                    continue;
                }
            }
            List list1 = new ArrayList();
            list1.add(title);
            list1.add(bookNum);
            list1.add(personName);
            list1.add(personCompany);
            list1.add(companyName);
            list1.add(companyManager);
            list1.add(reason);
            list1.add(law);
            list1.add(decision);
            list1.add(organ);
            list1.add(date);
            list.add(list1);
        }
        return list;
    }
}
