package com.weizhan.superlook.model.bean.region;

import java.util.List;

/**
 * Created by Administrator on 2018/11/9.
 */

public class RegionBean {
    int bid;
    String bigcategory_name;
    List<SmallCatBean> smallcategorylist;

    public class SmallCatBean {
        int id;
        String category_name;
        String imgurl;
        String place;
        String style_name;
        int type;

        public int getId() {
            return id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public String getPlace() {
            return place;
        }

        public String getStyle_name() {
            return style_name;
        }

        public int getType() {
            return type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
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

    public int getBid() {
        return bid;
    }

    public String getBigcategory_name() {
        return bigcategory_name;
    }

    public List<SmallCatBean> getSmallcategorylist() {
        return smallcategorylist;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setBigcategory_name(String bigcategory_name) {
        this.bigcategory_name = bigcategory_name;
    }

    public void setSmallcategorylist(List<SmallCatBean> smallcategorylist) {
        this.smallcategorylist = smallcategorylist;
    }
}
