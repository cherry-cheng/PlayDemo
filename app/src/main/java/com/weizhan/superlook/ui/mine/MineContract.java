package com.weizhan.superlook.ui.mine;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

import me.drakeet.multitype.Items;

/**
 * Created by Android_ZzT on 17/7/6.
 */

public interface MineContract {

    interface View extends BaseView {

        void onDataUpdated();

        void showLoadFailed();
    }

    interface Presenter extends BasePresenter {

    }
}
