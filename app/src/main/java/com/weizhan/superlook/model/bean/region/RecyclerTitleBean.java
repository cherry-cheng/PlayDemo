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
