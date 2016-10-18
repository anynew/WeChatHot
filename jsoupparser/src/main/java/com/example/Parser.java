package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static final String url = "http://weixin.sogou.com";

    public static void main(String args[]){

        List<String> listLink = getSource().getListImg();
        for (int i = 0; i < listLink.size(); i++) {
            System.out.println(listLink.get(i));
        }
    }

    private static PicSource getSource(){
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements urlLink;
        Elements urlTitle;
        Elements urlImg;
        Elements urlSorcre;
        Elements urlTime;

        urlLink = document.select(".tabcontant a");
        urlTitle = document.select(".wx-topbpx h3 a");
        urlImg = document.select(".tabcontant a img");
        urlSorcre = document.select(".wx-topbpx span a");
        urlTime = document.select(".wx-topbpx span");

        List<String>  listTitle = new ArrayList<>();    //标题
        List<String>  listLink = new ArrayList<>();     //链接
        List<String>  listImg = new ArrayList<>();      //图片地址
        List<String>  listSource = new ArrayList<>();   //新闻源
        List<String>  listTime = new ArrayList<>();     //时间

        for (int i = 0; i < 5; i++) {
            listTitle.add(urlTitle.get(i).text());
            listLink.add(urlLink.get(i).absUrl("href"));
            listImg.add(urlImg.get(i).attr("src"));
            listSource.add(urlSorcre.get(i).attr("title"));
            listTime.add(urlTime.get(i).ownText());
        }

        PicSource ps = new PicSource(listTitle,listLink,listImg,listSource,listTime);
        return ps;
    }

}

