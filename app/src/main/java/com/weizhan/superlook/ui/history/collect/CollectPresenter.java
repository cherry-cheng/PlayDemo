package com.weizhan.superlook.ui.history.collect;
import android.util.Log;
import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
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

public class CollectPresenter extends AbsBasePresenter<CollectContract.View> {

    private static final String TAG = CollectPresenter.class.getSimpleName();

    private Recommend1Apis mRecommend1Apis;

    @Inject
    public CollectPresenter(Recommend1Apis regionApis) {
        mRecommend1Apis = regionApis;
    }

    @Override
    public void loadData() {
        Items items = new Items();
        mView.onDataUpdated(items);
        mRecommend1Apis.getUserLoveList()
                .subscribeOn(Schedulers.newThread())
                .map(new Function<SeriesDataResponse<SeriesBean.EpisodeSearch>, Items>() {
                    @Override
                    public Items apply(@NonNull SeriesDataResponse<SeriesBean.EpisodeSearch> regionShow) throws Exception {
                        return regionShow2Items(regionShow.getBody());
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
                        if (items.size() > 0) {
                            mView.onDataUpdated(items);
                        } else {
                            mView.showLoadFailed();
                        }
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

    private Items regionShow2Items(List<SeriesBean.EpisodeSearch> episodes) {
        Items items = new Items();
        for (SeriesBean.EpisodeSearch episode : episodes) {
            items.add(episode);
        }
        return items;
    }


    @Override
    public void releaseData() {

    }
}
