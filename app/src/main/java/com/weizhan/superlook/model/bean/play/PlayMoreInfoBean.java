package com.weizhan.superlook.model.bean.play;

import java.util.List;

/**
 * Created by Administrator on 2018/11/14.
 */

public class PlayMoreInfoBean {
    List<PlayInfoBean.PlayRecommendBean> recommendslist;
    PlayInfoBean.PlayBean info;
    List<PartNum> num;

    public List<PlayInfoBean.PlayRecommendBean> getRecommendslist() {
        return recommendslist;
    }

    public PlayInfoBean.PlayBean getInfo() {
        return info;
    }

    public List<PartNum> getNum() {
        return num;
    }

    public void setRecommendslist(List<PlayInfoBean.PlayRecommendBean> recommendslist) {
        this.recommendslist = recommendslist;
    }

    public void setInfo(PlayInfoBean.PlayBean info) {
        this.info = info;
    }

    public void setNum(List<PartNum> num) {
        this.num = num;
    }

    public class PartNum {
        int id;
        String linkurl;
        String content;
        int num;
        String times;

        public int getId() {
            return id;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public String getContent() {
            return content;
        }

        public int getNum() {
            return num;
        }

        public String getTimes() {
            return times;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}
