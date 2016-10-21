package com.example;

import java.util.List;

/**
 * Created by anynew on 2016/10/19.
 */

public class HomeSource {
    private List<String> listTitle;  //新闻列表标题
    private List<String> listFrom;  // 新闻来源
    private List<String> listIllustrator; //  新闻列表配图
    private List<String> listGoto;  //新闻列链接
    private List<String> listViews; //阅读次数

    public HomeSource(List<String> listTitle, List<String> listFrom, List<String> listIllustrator, List<String> listGoto, List<String> listViews) {
        this.listTitle = listTitle;
        this.listFrom = listFrom;
        this.listIllustrator = listIllustrator;
        this.listGoto = listGoto;
        this.listViews = listViews;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public List<String> getListFrom() {
        return listFrom;
    }

    public List<String> getListIllustrator() {
        return listIllustrator;
    }

    public List<String> getListGoto() {
        return listGoto;
    }

    public List<String> getListViews() {
        return listViews;
    }
}
