package com.anynew.wechathot.parser;

import com.anynew.wechathot.model.HomeSource;
import com.anynew.wechathot.model.PicSource;
import com.anynew.wechathot.network.getData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    Document document;
    Document documentMore_1;
    public Parser() {
        try {
            document = Jsoup.connect(getData.URL_WECHAT).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0").timeout(8000).get();
            documentMore_1 = Jsoup.connect(getData.HOME_LOAD_MORE_1).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0").timeout(8000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return  轮播视图
     */

    public  PicSource getBannerSource(){

        Elements urlTitle = document.select(".text p"); //Banner标题
        Elements urlImage = document.select(".sd-slider-item img"); // Banner图片
        Elements urlLink = document.select(".sd-slider-item");// Banner 链接

        List<String>  listTitle = new ArrayList<>();    //标题
        List<String>  listLink = new ArrayList<>();     //链接
        List<String>  listImg = new ArrayList<>();      //图片地址

        for (int i = 0; i < urlTitle.size(); i++) {
            System.out.println(urlTitle.get(i).attr("title"));
            System.out.println(urlImage.get(i).attr("src"));
            System.out.println(urlLink.get(i).attr("href"));

            listTitle.add(urlTitle.get(i).attr("title"));
            listLink.add(urlLink.get(i).attr("href"));
            listImg.add(urlImage.get(i).attr("src"));
        }

        PicSource ps = new PicSource(listTitle,listLink,listImg);
        return ps;
    }

    /**
     * 新闻列表
     * @return
     */
    public HomeSource getHomeSource(){
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
//            System.out.println("title= "+ title);
            listTitle.add(title);

            String link = urlGoto.get(i).attr("href");
//            System.out.println("link ==== "+link);
            listGoto.add(link);

            String from = urlFrom.get(i).ownText();
//            System.out.println("from = "+from);
            listFrom.add(from);

            String illustrator = urlIllustrator.get(i).attr("src");
//            System.out.println("img = "+ illustrator);
            listIllustrator.add(illustrator);

        }

        HomeSource homeSource = new HomeSource(listTitle,listFrom,listIllustrator,listGoto);
        return  homeSource;
    }

}

