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

    public PicSource(List<String> listTitle, List<String> listLink, List<String> listImg) {
        this.listTitle = listTitle;
        this.listLink = listLink;
        this.listImg = listImg;
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
}