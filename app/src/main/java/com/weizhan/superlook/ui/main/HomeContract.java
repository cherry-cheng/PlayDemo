package com.weizhan.superlook.ui.main;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

/**
 * Created by Administrator on 2018/11/15.
 */

public class HomeContract {
    interface View extends BaseView {

        void onDataUpdated(String keyword);
    }

    interface Presenter extends BasePresenter {

    }
}
