package com.weizhan.superlook.model.bean.series;

import java.util.List;

/**
 * Created by Administrator on 2018/11/8.
 */

public class SeriesBean {
    int id;
    String home_name;
    String style_name;
    String place;
    String type;
    List<Episode> list;

    public int getId() {
        return id;
    }

    public String getHome_name() {
        return home_name;
    }

    public String getStyle_name() {
        return style_name;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public List<Episode> getList() {
        return list;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setList(List<Episode> list) {
        this.list = list;
    }

    public class Episode {
        String score;
        String title;
        String v_img;
        String h_img;
        String describes;
        int total;
        int type;
        int id;
        int current_num;
        String times;

        public String getScore() {
            return score;
        }

        public String getTitle() {
            return title;
        }

        public String getV_img() {
            return v_img;
        }

        public String getH_img() {
            return h_img;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getTotal() {
            return total;
        }

        public int getId() {
            return id;
        }

        public int getCurrent_num() {
            return current_num;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setV_img(String v_img) {
            this.v_img = v_img;
        }

        public void setH_img(String h_img) {
            this.h_img = h_img;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCurrent_num(int current_num) {
            this.current_num = current_num;
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
    }

    public class EpisodeSearch {
        String score;
        String title;
        String v_img;
        String h_img;
        String describes;
        int total;
        int type;
        int id;
        int current_num;
        String times;
        String place;

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getScore() {
            return score;
        }

        public String getTitle() {
            return title;
        }

        public String getV_img() {
            return v_img;
        }

        public String getH_img() {
            return h_img;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getTotal() {
            return total;
        }

        public int getId() {
            return id;
        }

        public int getCurrent_num() {
            return current_num;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setV_img(String v_img) {
            this.v_img = v_img;
        }

        public void setH_img(String h_img) {
            this.h_img = h_img;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCurrent_num(int current_num) {
            this.current_num = current_num;
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
    }
}
