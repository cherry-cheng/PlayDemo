package com.weizhan.superlook.ui.mine;

import com.common.base.BasePresenter;
import com.common.base.BaseView;
import com.weizhan.superlook.model.bean.mine.UpdateBean;

import me.drakeet.multitype.Items;

/**
 * Created by Android_ZzT on 17/7/6.
 */

public interface MineContract {

    interface View extends BaseView {

        void onDataUpdated();

        void showLoadFailed();

        void updateInfo(UpdateBean updateBean);
    }

    interface Presenter extends BasePresenter {

    }
}
