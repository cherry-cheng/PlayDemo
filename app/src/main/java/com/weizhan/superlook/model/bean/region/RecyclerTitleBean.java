package com.weizhan.superlook.model.bean.region;

import java.util.List;

/**
 * Created by Administrator on 2018/9/29.
 */

public class RecyclerTitleBean {
    List<String> recDataList1;
    List<String> recDataList2;
    List<String> recDataList3;
    int type;

    int position3;
    int position2;
    int position1;

    public int getPosition1() {
        return position1;
    }

    public void setPosition1(int position1) {
        this.position1 = position1;
    }

    public int getPosition2() {
        return position2;
    }

    public void setPosition2(int position2) {
        this.position2 = position2;
    }

    public int getPosition3() {
        return position3;
    }

    public void setPosition3(int position3) {
        this.position3 = position3;
    }

    public List<String> getRecDataList1() {
        return recDataList1;
    }

    public List<String> getRecDataList2() {
        return recDataList2;
    }

    public List<String> getRecDataList3() {
        return recDataList3;
    }

    public void setRecDataList1(List<String> recDataList1) {
        this.recDataList1 = recDataList1;
    }

    public void setRecDataList2(List<String> recDataList2) {
        this.recDataList2 = recDataList2;
    }

    public void setRecDataList3(List<String> recDataList3) {
        this.recDataList3 = recDataList3;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
