package com.weizhan.superlook.model.bean.recommend1;

import java.util.List;

/**
 * Created by Administrator on 2018/11/12.
 */

public class ChangeBean {
    int total;
    int per_page;
    int current_page;
    int last_page;
    String next_page_url;
    String prev_page_url;
    int from;
    int to;
    List<RecommendBean.ComlumInfo.ItemInfo> data;

    public int getTotal() {
        return total;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public List<RecommendBean.ComlumInfo.ItemInfo> getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setData(List<RecommendBean.ComlumInfo.ItemInfo> data) {
        this.data = data;
    }
}
