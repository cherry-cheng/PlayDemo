package com.weizhan.superlook.model.bean.region;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/9.
 */

public class CatePost implements Serializable {
    String places;  //0全部
    String styles;  //0全部
    int type;  //1 电影   2  电视剧   3  综艺   0 全部
    String years;  //0 全部
    String hot_type;  //1新上线 2 热播 3 综艺  0全部


    public String getPlaces() {
        return places;
    }

    public String getStyles() {
        return styles;
    }

    public int getType() {
        return type;
    }

    public String getYears() {
        return years;
    }

    public String getHot_type() {
        return hot_type;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public void setHot_type(String hot_type) {
        this.hot_type = hot_type;
    }
}
