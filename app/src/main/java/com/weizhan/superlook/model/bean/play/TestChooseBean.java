package com.weizhan.superlook.model.bean.play;

import java.util.List;

/**
 * Created by Administrator on 2018/9/19.
 */

public class TestChooseBean {


    List<ChooseItem> items;

    public List<ChooseItem> getItems() {
        return items;
    }

    public void setItems(List<ChooseItem> items) {
        this.items = items;
    }

    public class ChooseItem {
        private String time = "2018.9.19";
        private String des = "因爱而生";

        public String getTime() {
            return time;
        }

        public String getDes() {
            return des;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
