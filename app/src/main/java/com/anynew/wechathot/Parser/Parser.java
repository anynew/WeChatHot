package com.anynew.wechathot.Parser;

import com.anynew.wechathot.model.PicSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static final String url = "http://weixin.sogou.com";
    Document document;
    public Parser() {
        try {
            document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0").timeout(8000).get();
            System.out.println("文档已加载");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PicSource getSource(){

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

