package com.weizhan.superlook.model.bean.mine;

/**
 * Created by Administrator on 2018/11/19.
 */

public class UpdateBean {
    String linkurl;
    int issuper; //是否强制更新 1 强制更新 0 不强制更新
    String update_log;
    String appversion;

    public String getLinkurl() {
        return linkurl;
    }

    public int getIssuper() {
        return issuper;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public void setIssuper(int issuper) {
        this.issuper = issuper;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }
}
