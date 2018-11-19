package com.weizhan.superlook.ui.play;

import android.util.Log;
import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.App;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.past.PastBean;
import com.weizhan.superlook.model.bean.play.PlayInfoBean;
import com.weizhan.superlook.model.bean.play.TestBean;
import com.weizhan.superlook.model.bean.recommend1.ISLoveBean;
import com.weizhan.superlook.util.RealmHelper;
import com.weizhan.superlook.util.SpUtils;

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

    public void lovesMovie(int id, int type, int islove) {
        mRecommend1Apis.lovesMovie(id, type, islove)
                .subscribeOn(Schedulers.io())
                .map(new Function<TTDataResponse<ISLoveBean>, ISLoveBean>() {
                    @Override
                    public ISLoveBean apply(@NonNull TTDataResponse<ISLoveBean> isLoveBeanTTDataResponse) throws Exception {
                        return isLoveBeanTTDataResponse.getBody();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ISLoveBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void onNext(@NonNull ISLoveBean isLoveBean) {
                        Log.e("cyh112", "是否请求成功" + isLoveBean.getStatus());
                        mView.loveMovies(isLoveBean.getStatus());
                        // 0是未登录 1是成功 2是失败
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public void isLove(int id, int type) {
        Log.e("cyh112", "请求开始 = " + id + "  " + type);
        mRecommend1Apis.getIsLove(id, type)
                .subscribeOn(Schedulers.io())
                .map(new Function<TTDataResponse<ISLoveBean>, ISLoveBean>() {
                    @Override
                    public ISLoveBean apply(@NonNull TTDataResponse<ISLoveBean> isLoveBeanTTDataResponse) throws Exception {
                        return isLoveBeanTTDataResponse.getBody();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ISLoveBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void onNext(@NonNull ISLoveBean isLoveBean) {
                        Log.e("cyh112", "是否请求成功" + isLoveBean.getStatus());
                        mView.updateIsLove(isLoveBean.getStatus());
                        // 0是未登录 1是收藏 2是未收藏
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    @Override
    public void loadPlayInfo(final int id, final int type) {
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

                        PlayInfoBean.PlayBean playBean = (PlayInfoBean.PlayBean)items.get(0);
                        //将播放信息放入数据库
                        PastBean pastBean = new PastBean();
                        pastBean.setId(playBean.getId() + "");
                        pastBean.setActors(playBean.getActors());
                        pastBean.setCurrent_num(playBean.getCurrent_num());
                        pastBean.setDescribes(playBean.getDescribes());
                        pastBean.setDirctors(playBean.getDirctors());
                        pastBean.setH_img(playBean.getH_img());
                        pastBean.setV_img(playBean.getV_img());
                        pastBean.setLinkurl(playBean.getLinkurl());
                        pastBean.setScore(playBean.getScore());
                        pastBean.setType(playBean.getType());
                        pastBean.setTimes(playBean.getTimes());
                        pastBean.setYears(playBean.getYears());
                        pastBean.setSummary(playBean.getSummary());
                        pastBean.setStyle_name(playBean.getStyle_name());
                        pastBean.setTitle(playBean.getTitle());
                        pastBean.setTotal(playBean.getTotal());
                        pastBean.setInsertTime(System.currentTimeMillis());
                        RealmHelper.getInstance().insertPlayInfo(pastBean);
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
                        if (SpUtils.getBoolean(App.getInstance(), "isLogin", false)) {
                            isLove(id, type);
                        }
                    }
                });
    }
}
