package com.anynew.wechathot.model;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by anynew on 2016/10/19.
 */

public class HomeSource  {
    private List<String> listTitle;
    private List<String> listContent;
    private List<String> listIllustrator;
    private List<String> listGoto;

    public HomeSource(List<String> listTitle, List<String> listContent, List<String> listIllustrator, List<String> listGoto) {
        this.listTitle = listTitle;
        this.listContent = listContent;
        this.listIllustrator = listIllustrator;
        this.listGoto = listGoto;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public void setListTitle(List<String> listTitle) {
        this.listTitle = listTitle;
    }

    public List<String> getListContent() {
        return listContent;
    }

    public void setListContent(List<String> listContent) {
        this.listContent = listContent;
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
