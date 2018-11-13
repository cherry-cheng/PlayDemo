package com.weizhan.superlook.ui.play;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/18.
 */

public interface PlayContract {
    interface View extends BaseView {
        void onDataUpdated(Items items);

        void showLoadFailed();

        void showPlay(String url, String title);
    }

    interface Presenter extends BasePresenter {

        void loadPlayInfo(int id, int type);

    }
}
