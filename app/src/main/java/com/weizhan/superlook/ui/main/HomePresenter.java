package com.weizhan.superlook.ui.main;

import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.App;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.recommend1.KeyWordBean;
import com.weizhan.superlook.ui.region.RegionPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/15.
 */

public class HomePresenter extends AbsBasePresenter<HomeContract.View> {

    private static final String TAG = RegionPresenter.class.getSimpleName();

    private Recommend1Apis recommend1Apis;

    @Inject
    public HomePresenter(Recommend1Apis regionApis) {
        recommend1Apis = regionApis;
    }

    @Override
    public void loadData() {
        recommend1Apis.getKeyWords()
                .subscribeOn(Schedulers.newThread())
                .map(new Function<SeriesDataResponse<KeyWordBean>, List<KeyWordBean>>() {
                    @Override
                    public List<KeyWordBean> apply(@NonNull SeriesDataResponse<KeyWordBean> keywordBean) throws Exception {
                        return keywordBean.getBody();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<KeyWordBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        Log.i("cyh111", "请求走到了。。。。");
                    }

                    @Override
                    public void onNext(@NonNull List<KeyWordBean> keyWordBeans) {
                        //刷新热词
                        Log.i("cyh8888", "热词 = " + keyWordBeans.get(0).getKeywords());
                        App.getInstance().setKeyWordBeans(keyWordBeans);
                        mView.onDataUpdated(keyWordBeans.get(0).getKeywords());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError = " + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }


    @Override
    public void releaseData() {

    }
}
