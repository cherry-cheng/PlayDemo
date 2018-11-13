package com.weizhan.superlook.model.event;

/**
 * Created by Administrator on 2018/11/8.
 */

public class RangeRefresh {

    private int id;
    private int type;
    private int last_page;
    private int position;

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }
}
