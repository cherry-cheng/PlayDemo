package com.weizhan.superlook.ui.test.mvp.presenter;

import com.weizhan.superlook.model.api.WeChatApis;
import com.weizhan.superlook.ui.test.mvp.contract.MvpStructureContract;
import com.common.base.AbsBasePresenter;

import javax.inject.Inject;

/**
 * Created by jiayiyang on 17/3/25.
 */

public class NoBaseMvpPresenter extends AbsBasePresenter<MvpStructureContract.View> implements MvpStructureContract.Presenter {

    private static final String TAG = NoBaseMvpPresenter.class.getSimpleName();

    private WeChatApis weChatApis;

    @Inject
    public NoBaseMvpPresenter(WeChatApis weChatApis) {
        this.weChatApis = weChatApis;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void releaseData() {

    }

}