package com.weizhan.superlook.ui.search.home;

import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.App;
import com.weizhan.superlook.model.api.RegionApis;
import com.weizhan.superlook.model.bean.recommend1.KeyWordBean;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.multitype.Items;
/**
 * Created by Android_ZzT on 17/7/6.
 */

public class SearchHomePresenter extends AbsBasePresenter<SearchHomeContract.View> {

    private static final String TAG = SearchHomePresenter.class.getSimpleName();

//    private RegionApis mRegionApis;

    @Inject
    public SearchHomePresenter(RegionApis regionApis) {
//        mRegionApis = regionApis;
    }

    @Override
    public void loadData() {
        Items items = new Items();
        List<KeyWordBean> wordBeans = App.getInstance().getKeyWordBeans();
        for (int i = 0; i < wordBeans.size(); i++) {
            KeyWordBean hotWord = new KeyWordBean();
            hotWord.setKeywords(wordBeans.get(i).getKeywords());
            items.add(hotWord);
        }
        mView.onDataUpdated(items);
    }


    @Override
    public void releaseData() {

    }
}
