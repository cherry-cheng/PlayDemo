package com.weizhan.superlook.ui.search.result.movie;

import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;
import com.weizhan.superlook.model.api.ApiHelper;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.DataListResponse;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1Show;
import com.weizhan.superlook.model.bean.series.SeriesBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/5.
 */

public class MovieSPresenter extends AbsBasePresenter<MovieSContract.View> {

    private static final String TAG = MovieSPresenter.class.getSimpleName();

    private Recommend1Apis mRecommend1Apis;

    @Inject
    public MovieSPresenter(Recommend1Apis regionApis) {
        mRecommend1Apis = regionApis;
    }

    @Override
    public void loadData() {
    }

    private Items mapShow2Items(List<SeriesBean.EpisodeSearch> episodes) {
        Items items = new Items();
        List<SeriesBean.EpisodeSearch> episodes1 = episodes;
        for (SeriesBean.EpisodeSearch episode : episodes1) {
            items.add(episode);
        }
        return items;
    }


    @Override
    public void releaseData() {

    }

    public void onDataSearch(int type, String keywords) {
        Items items = new Items();
        mView.onDataUpdated(items);
        mRecommend1Apis.getSearchResult(type, keywords)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<SeriesDataResponse<SeriesBean.EpisodeSearch>, Items>() {
                    @Override
                    public Items apply(@NonNull SeriesDataResponse<SeriesBean.EpisodeSearch> episodes) throws Exception {
                        return mapShow2Items(episodes.getBody());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Items>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void onNext(@NonNull Items items) {
                        mView.onDataUpdated(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
                        mView.showLoadFailed();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");

                    }
                });
    }
}
