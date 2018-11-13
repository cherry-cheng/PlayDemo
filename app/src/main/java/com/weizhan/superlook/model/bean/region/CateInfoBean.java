package com.weizhan.superlook.model.bean.region;

import com.weizhan.superlook.model.bean.series.SeriesBean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/9.
 */

public class CateInfoBean {

    List<String> style_list;
    List<String> year_list;
    List<String> place_list;
    RecommendList recommendlist;
    public class RecommendList {
        List<SeriesBean.Episode> data;

        public List<SeriesBean.Episode> getData() {
            return data;
        }

        public void setData(List<SeriesBean.Episode> data) {
            this.data = data;
        }
    }

    public List<String> getStyle_list() {
        return style_list;
    }

    public List<String> getYear_list() {
        return year_list;
    }

    public List<String> getPlace_list() {
        return place_list;
    }

    public RecommendList getRecommendlist() {
        return recommendlist;
    }

    public void setStyle_list(List<String> style_list) {
        this.style_list = style_list;
    }

    public void setYear_list(List<String> year_list) {
        this.year_list = year_list;
    }

    public void setPlace_list(List<String> place_list) {
        this.place_list = place_list;
    }

    public void setRecommendlist(RecommendList recommendlist) {
        this.recommendlist = recommendlist;
    }
}
