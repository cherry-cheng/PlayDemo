package com.weizhan.superlook.ui.recommend1;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/5.
 */

public class Recommend1Contract {
    interface View extends BaseView {

        void onDataUpdated(Items items);

        void showLoadFailed();

        void onDataRangeUpdate(Items items, int positionStart, int itemCount);
    }

    interface Presenter extends BasePresenter {

    }
}
