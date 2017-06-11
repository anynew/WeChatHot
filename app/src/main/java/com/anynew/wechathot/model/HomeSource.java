package com.anynew.wechathot.model;

import java.util.List;

/**
 * Created by anynew on 2016/10/19.
 */

public class HomeSource {
    private List<String> listTitle;  //新闻列表标题
    private List<String> listFrom;  // 新闻来源
    private List<String> listIllustrator; //  新闻列表配图
    private List<String> listGoto;  //新闻列链接

    public HomeSource(List<String> listTitle, List<String> listFrom, List<String> listIllustrator, List<String> listGoto) {
        this.listTitle = listTitle;
        this.listFrom = listFrom;
        this.listIllustrator = listIllustrator;
        this.listGoto = listGoto;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public void setListTitle(List<String> listTitle) {
        this.listTitle = listTitle;
    }

    public List<String> getListFrom() {
        return listFrom;
    }

    public void setListFrom(List<String> listFrom) {
        this.listFrom = listFrom;
    }

    public List<String> getListIllustrator() {
        return listIllustrator;
    }

    public void setListIllustrator(List<String> listIllustrator) {
        this.listIllustrator = listIllustrator;
    }

    public List<String> getListGoto() {
        return listGoto;
    }

    public void setListGoto(List<String> listGoto) {
        this.listGoto = listGoto;
    }
}
