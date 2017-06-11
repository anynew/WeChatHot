package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static final String url = "http://weixin.sogou.com";
    private static Document document;

    public static void main(String args[]){
        try {
            document = Jsoup.connect(url).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        getBannerSource().getListTitle();
//        getHomeSource().getListFrom();
    }

    private static PicSource getBannerSource(){

        Elements urlTitle = document.select(".text p"); //Banner标题
        Elements urlImage = document.select(".sd-slider-item img"); // Banner图片
        Elements urlLink = document.select(".sd-slider-item");// Banner 链接

        for (int i = 0; i < urlTitle.size(); i++) {
            System.out.println(urlTitle.get(i).attr("title"));
            System.out.println(urlImage.get(i).attr("src"));
            System.out.println(urlLink.get(i).attr("href"));
        }

        List<String>  listTitle = new ArrayList<>();    //标题
        List<String>  listLink = new ArrayList<>();     //链接
        List<String>  listImg = new ArrayList<>();      //图片地址

        PicSource ps = new PicSource(listTitle,listLink,listImg);
        return ps;
    }

    /**
     * 新闻列表
     * @return
     */
    private static HomeSource getHomeSource(){
        Elements urlTitle;  // 新闻标题
        Elements urlGoto;   // 新闻超链接
        Elements urlFrom;   // 新闻出处
        Elements urlIllustrator; //新闻配图

        urlTitle = document.select(".txt-box h3");
        urlGoto = document.select(".txt-box h3 a");
        urlFrom = document.select(".account");
        urlIllustrator = document.select(".news-box a img");

        List<String>  listTitle = new ArrayList<>();    //列表标题
        List<String>  listGoto = new ArrayList<>();    //列表链接
        List<String>  listFrom = new ArrayList<>();    //列表来源
        List<String>  listIllustrator = new ArrayList<>();    //列表插图

        for (int i = 0; i < urlTitle.size(); i++) {

            String title = urlTitle.get(i).text();
            System.out.println(title);
            listTitle.add(title);

            String link = urlGoto.get(i).attr("href");
            System.out.println(link);
            listGoto.add(link);

            String from = urlFrom.get(i).ownText();
            System.out.println(from);
            listFrom.add(from);

            String illustrator = urlIllustrator.get(i).attr("src");
            System.out.println("img = "+ illustrator);
            listIllustrator.add(illustrator);
        }

        HomeSource homeSource = new HomeSource(listTitle,listGoto,listFrom,listIllustrator);
        return  homeSource;
    }
}

