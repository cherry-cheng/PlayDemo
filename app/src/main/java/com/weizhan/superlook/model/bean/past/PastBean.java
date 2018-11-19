package com.weizhan.superlook.model.bean.past;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2018/11/19.
 */

public class PastBean extends RealmObject {
    String title;
    String id;
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
    int type;
    public long insertTime;//插入时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getActors() {
        return actors;
    }

    public String getDirctors() {
        return dirctors;
    }

    public String getDescribes() {
        return describes;
    }

    public String getTimes() {
        return times;
    }

    public String getStyle_name() {
        return style_name;
    }

    public String getYears() {
        return years;
    }

    public int getType() {
        return type;
    }

    public long getInsertTime() {
        return insertTime;
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

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setDirctors(String dirctors) {
        this.dirctors = dirctors;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
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

    public void setType(int type) {
        this.type = type;
    }

    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }
}
