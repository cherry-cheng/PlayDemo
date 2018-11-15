package com.weizhan.superlook.ui.region.series;

import com.common.base.BasePresenter;
import com.common.base.BaseView;
import com.weizhan.superlook.model.bean.region.CatePost;
import com.weizhan.superlook.model.bean.region.RecyclerTitleBean;

import me.drakeet.multitype.Items;

/**
 * Created by jiayiyang on 17/3/25.
 */

public interface SeriesRContract {

    interface View extends BaseView {
        void onDataUpdated(Items items);

        void showLoadFailed();

        void onDataRangeUpdated(RecyclerTitleBean recyclerTitleBean);
    }

    interface Presenter extends BasePresenter {

        void loadDataWithParas(CatePost catePost);
        void loadRangeDataWithParas(CatePost catePost);
    }

}
