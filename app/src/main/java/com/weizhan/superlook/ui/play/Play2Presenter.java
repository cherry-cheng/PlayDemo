package com.weizhan.superlook.ui.play;

import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.DataListResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.play.PlayInfoBean;
import com.weizhan.superlook.model.bean.play.PlayMoreInfoBean;
import com.weizhan.superlook.model.bean.play.TestBean;
import com.weizhan.superlook.model.bean.play.TestChooseBean;
import com.weizhan.superlook.model.bean.play.TestSeriesBean;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1Show;

import java.util.ArrayList;
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
 * Created by Administrator on 2018/9/19.
 */

public class Play2Presenter extends AbsBasePresenter<Play2Contract.View> implements Play2Contract.Presenter {

    private static final String TAG = PlayPresenter.class.getSimpleName();

    private Recommend1Apis mRecommend1Apis;

    private String playUrl = "";
    private String playTitle="";

    @Inject
    public Play2Presenter(Recommend1Apis regionApis) {
        mRecommend1Apis = regionApis;
    }

    private Items regionShow2Items(DataListResponse<AppRecommend1Show> regionShow) {
        Items items = new Items();
        List<AppRecommend1Show> regionShowList = regionShow.getData();
        AppRecommend1Show.Partition p = new AppRecommend1Show().new Partition();
        items.add(p);
        TestBean testBean1 = new TestBean();
        testBean1.setTitle("选集");
        items.add(testBean1);

        TestChooseBean testChooseBean = new TestChooseBean();
        List<TestChooseBean.ChooseItem> list = new ArrayList<TestChooseBean.ChooseItem>();

        for (int i = 0; i < 9; i++) {
            TestChooseBean.ChooseItem chooseItem = new TestChooseBean().new ChooseItem();
            chooseItem.setTime("2018.9.1" + i);
            chooseItem.setDes("因爱而生" + i);
            list.add(chooseItem);
        }
        testChooseBean.setItems(list);
        items.add(testChooseBean);

        TestBean testBean = new TestBean();
        testBean.setTitle("踩你喜欢");
        items.add(testBean);
        for (AppRecommend1Show appRecommend1Show : regionShowList) {
            //body
            List<AppRecommend1Show.Body> bodyList = appRecommend1Show.getBody();
            for (AppRecommend1Show.Body b : bodyList) {
                items.add(b);
            }
            break;
        }
        return items;
    }


    @Override
    public void releaseData() {

    }

    private Items playInfoShow2Items(PlayMoreInfoBean playMoreInfoBean) {
        Log.e("PlayPresenter", "playInfoBean = " + playMoreInfoBean.getInfo().getActors());
        Items items = new Items();
        List<PlayInfoBean.PlayRecommendBean> playRecommendBeans = playMoreInfoBean.getRecommendslist();
        PlayInfoBean.PlayBean playBean = playMoreInfoBean.getInfo();
        // 播放详情下面的信息需要添加，待续...........
        items.add(playBean);

        //选集
        TestBean testBean1 = new TestBean();
        testBean1.setTitle("选集");
        items.add(testBean1);

        TestSeriesBean testSeriesBean = new TestSeriesBean();
        testSeriesBean.setList(playMoreInfoBean.getNum());
        items.add(testSeriesBean);
        playUrl = playMoreInfoBean.getNum().get(0).getLinkurl();
        playTitle = playMoreInfoBean.getInfo().getTitle();
        TestBean testBean = new TestBean();
        testBean.setTitle("猜你喜欢");
        items.add(testBean);
        for (PlayInfoBean.PlayRecommendBean playRecommendBean: playRecommendBeans) {
            items.add(playRecommendBean);
        }
        return items;
    }

    @Override
    public void loadPlayInfo(int id, int type) {
        mRecommend1Apis.getPlayMoreInfo(id, type)
                .subscribeOn(Schedulers.io())
                .map(new Function<TTDataResponse<PlayMoreInfoBean>, Items>() {
                    @Override
                    public Items apply(@NonNull TTDataResponse<PlayMoreInfoBean> palyInfoBean) throws Exception {
                        Log.e(TAG, "playmoreninfo = " + palyInfoBean.getMsg());
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
                        Log.e("cyh444", "playUrl = " + playUrl);
                        mView.showPlay(playUrl, playTitle);
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
