package com.weizhan.superlook.model.bean.region;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/9.
 */

public class CatePost implements Serializable {
    String place;
    String style_name;
    int type;

    public String getPlace() {
        return place;
    }

    public String getStyle_name() {
        return style_name;
    }

    public int getType() {
        return type;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public void setType(int type) {
        this.type = type;
    }
}
