package com.anynew.wechathot.parser;

import com.anynew.wechathot.model.HomeSource;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return  轮播视图
     */

    public PicSource getPicSource(){

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

    /**
     * 新闻列表
     * @return
     */
    public HomeSource getHomeSource(){
        Elements urlTitle;
        Elements urlViews;
        Elements urlIllustrator;
        Elements urlFrom;

        urlTitle = document.select(".wx-news-info2 h4 a");
        urlIllustrator = document.select(".wx-img-box img");
        urlViews = document.select(".s-p");
        urlFrom = document.select(".pos-wxrw a");

        List<String>  listTitle = new ArrayList<>();    //列表标题
        List<String>  listGoto = new ArrayList<>();    //列表链接
        List<String>  listViews = new ArrayList<>();    //列表内容
        List<String>  listIllustrator = new ArrayList<>();    //列表插图
        List<String>  listFrom = new ArrayList<>();    //列表来源

        for (int i = 0; i < urlTitle.size(); i++) {

            String title = urlTitle.get(i).ownText();
            listTitle.add(title);

            String link = urlTitle.get(i).attr("href");
            listGoto.add(link);

            String views = urlViews.get(i).ownText();
            listViews.add(views);

            String illustrator = urlIllustrator.get(i).attr("src");
            listIllustrator.add(illustrator);

            urlFrom.remove(i +1);
            String from = urlFrom.get(i).text();
            listFrom.add(from);

            System.out.println(views);
        }

        HomeSource homeSource = new HomeSource(listTitle,listFrom,listIllustrator,listGoto,listViews);
        return  homeSource;
    }

}

