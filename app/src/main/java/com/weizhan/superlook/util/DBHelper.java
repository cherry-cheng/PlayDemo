package com.weizhan.superlook.util;

import com.weizhan.superlook.model.bean.past.PastBean;
import com.weizhan.superlook.model.bean.search.SearchKey;

import java.util.List;

import io.realm.Realm;

public interface DBHelper {
    Realm getRealm();
    void insertSearchHistory(SearchKey bean);

    List<SearchKey> getSearchHistoryList(String value);

    void deleteSearchHistoryList(String value);

    void deleteSearchHistoryAll();

    List<SearchKey> getSearchHistoryListAll();

    //影片
    void insertPlayInfo(PastBean pastBean);
    List<PastBean> getPlayInfoHistoryList(String id);
    List<PastBean> getPlayInfoAll();
}
