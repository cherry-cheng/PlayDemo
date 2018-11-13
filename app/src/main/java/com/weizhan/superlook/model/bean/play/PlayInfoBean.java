package com.weizhan.superlook.model.bean.play;

import java.util.List;

/**
 * Created by Administrator on 2018/11/9.
 */

public class PlayInfoBean {
    List<PlayRecommendBean> recommendslist;
    PlayBean info;

    public List<PlayRecommendBean> getRecommendslist() {
        return recommendslist;
    }

    public PlayBean getInfo() {
        return info;
    }

    public void setRecommendslist(List<PlayRecommendBean> recommendslist) {
        this.recommendslist = recommendslist;
    }

    public void setInfo(PlayBean info) {
        this.info = info;
    }

    public class PlayBean {
        String title;
        int id;
        String v_img;
        String h_img;
        String linkurl;
        String score;
        String summary;
        int total;
        int current_num;
        String actors;
        String dirctors;
        String describes;
        String times;
        String style_name;
        String years;

        public String getTimes() {
            return times;
        }

        public String getStyle_name() {
            return style_name;
        }

        public String getYears() {
            return years;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public void setStyle_name(String style_name) {
            this.style_name = style_name;
        }

        public void setYears(String years) {
            this.years = years;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public String getV_img() {
            return v_img;
        }

        public String getH_img() {
            return h_img;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public String getScore() {
            return score;
        }

        public String getSummary() {
            return summary;
        }

        public int getTotal() {
            return total;
        }

        public int getCurrent_num() {
            return current_num;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setV_img(String v_img) {
            this.v_img = v_img;
        }

        public void setH_img(String h_img) {
            this.h_img = h_img;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setCurrent_num(int current_num) {
            this.current_num = current_num;
        }

        public String getActors() {
            return actors;
        }

        public String getDirctors() {
            return dirctors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public void setDirctors(String dirctors) {
            this.dirctors = dirctors;
        }
    }
    public class PlayRecommendBean {
        String title;
        int id;
        String v_img;
        String h_img;
        String linkurl;
        String score;
        String summary;
        int total;
        int current_num;
        String actors;
        String dirctors;
        String describes;
        int type;
        String times;

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public String getV_img() {
            return v_img;
        }

        public String getH_img() {
            return h_img;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public String getScore() {
            return score;
        }

        public String getSummary() {
            return summary;
        }

        public int getTotal() {
            return total;
        }

        public int getCurrent_num() {
            return current_num;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setV_img(String v_img) {
            this.v_img = v_img;
        }

        public void setH_img(String h_img) {
            this.h_img = h_img;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setCurrent_num(int current_num) {
            this.current_num = current_num;
        }

        public String getActors() {
            return actors;
        }

        public String getDirctors() {
            return dirctors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public void setDirctors(String dirctors) {
            this.dirctors = dirctors;
        }
    }
}
