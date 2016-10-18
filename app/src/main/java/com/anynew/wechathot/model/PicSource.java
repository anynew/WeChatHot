package com.anynew.wechathot.model;

import java.util.List;

/**
 * Created by anynew on 2016/10/17.
 */

public class PicSource {
    //标题列表
    private List<String> listTitle;
    //内容列表
    private List<String> listLink;
    //图片列表
    private List<String> listImg;
    //新闻源
    private List<String> listSource;
    //发布时间
    private List<String> listTime;

    public PicSource(List<String> listTitle, List<String> listLink, List<String> listImg, List<String> listSource, List<String> listTime) {
        this.listTitle = listTitle;
        this.listLink = listLink;
        this.listImg = listImg;
        this.listSource = listSource;
        this.listTime = listTime;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public void setListTitle(List<String> listTitle) {
        this.listTitle = listTitle;
    }

    public List<String> getListLink() {
        return listLink;
    }

    public void setListLink(List<String> listLink) {
        this.listLink = listLink;
    }

    public List<String> getListImg() {
        return listImg;
    }

    public void setListImg(List<String> listImg) {
        this.listImg = listImg;
    }

    public List<String> getListSource() {
        return listSource;
    }

    public void setListSource(List<String> listSource) {
        this.listSource = listSource;
    }

    public List<String> getListTime() {
        return listTime;
    }

    public void setListTime(List<String> listTime) {
        this.listTime = listTime;
    }

    @Override
    public String toString() {
        return "PicSource{" +
                "listTitle=" + listTitle +
                ", listLink=" + listLink +
                ", listImg=" + listImg +
                ", listSource=" + listSource +
                ", listTime=" + listTime +
                '}';
    }
}
