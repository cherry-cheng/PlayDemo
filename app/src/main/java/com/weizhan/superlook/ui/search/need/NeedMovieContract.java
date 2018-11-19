package com.weizhan.superlook.ui.search.need;

import com.common.base.BasePresenter;
import com.common.base.BaseView;

/**
 * Created by jiayiyang on 17/3/25.
 */

public interface NeedMovieContract {

    interface View extends BaseView {

        void onSuccess();
        void onFail();

    }

    interface Presenter extends BasePresenter {

    }

}
