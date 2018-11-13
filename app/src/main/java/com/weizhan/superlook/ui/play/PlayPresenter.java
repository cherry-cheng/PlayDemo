package com.weizhan.superlook.ui.play;

import android.util.Log;
import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.play.PlayInfoBean;
import com.weizhan.superlook.model.bean.play.TestBean;

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
 * Created by Administrator on 2018/9/18.
 */

public class PlayPresenter extends AbsBasePresenter<PlayContract.View> implements PlayContract.Presenter {

    private static final String TAG = PlayPresenter.class.getSimpleName();

    private Recommend1Apis mRecommend1Apis;

    @Inject
    public PlayPresenter(Recommend1Apis recommend1Apis) {
        mRecommend1Apis = recommend1Apis;
    }

    private Items playInfoShow2Items(PlayInfoBean playInfoBean) {
        Log.e("PlayPresenter", "playInfoBean = " + playInfoBean.getInfo().getActors());
        Items items = new Items();
        List<PlayInfoBean.PlayRecommendBean> playRecommendBeans = playInfoBean.getRecommendslist();
        PlayInfoBean.PlayBean playBean = playInfoBean.getInfo();
        // 播放详情下面的信息需要添加，待续...........
        items.add(playBean);
        TestBean testBean = new TestBean();
        testBean.setTitle("猜你喜欢");
        items.add(testBean);
        for (PlayInfoBean.PlayRecommendBean playRecommendBean: playRecommendBeans) {
            items.add(playRecommendBean);
        }
        return items;
    }


    @Override
    public void releaseData() {

    }

    @Override
    public void loadPlayInfo(int id, int type) {
        Log.e(TAG, "loadPlayInfo = " + id + " " + type);
        mRecommend1Apis.getPlayInfo(id, type)
                .subscribeOn(Schedulers.io())
                .map(new Function<TTDataResponse<PlayInfoBean>, Items>() {
                    @Override
                    public Items apply(@NonNull TTDataResponse<PlayInfoBean> palyInfoBean) throws Exception {
                        Log.e(TAG, "palyInfoBean = " + palyInfoBean.getMsg() + palyInfoBean.getBody().getInfo().getSummary());
                        return playInfoShow2Items(palyInfoBean.getBody());
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
                        //让view播放影片
                        mView.showPlay(((PlayInfoBean.PlayBean)items.get(0)).getLinkurl(), ((PlayInfoBean.PlayBean)items.get(0)).getTitle());
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
