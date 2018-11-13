package com.weizhan.superlook.ui.movie;

import android.text.TextUtils;
import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;
import com.weizhan.superlook.model.api.ApiHelper;
import com.weizhan.superlook.model.api.MovieApis;
import com.weizhan.superlook.model.bean.DataListResponse;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.movie.AppMovieShow;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.ui.movie.viewbinder.MovieFooterItemViewBinder;
import com.weizhan.superlook.ui.series.viewbinder.SeriesFooterItemViewBinder;

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

public class MoviePresenter extends AbsBasePresenter<MovieContract.View> {

    private static final String TAG = MoviePresenter.class.getSimpleName();

    private MovieApis mMovieApis;

    @Inject
    public MoviePresenter(MovieApis regionApis) {
        mMovieApis = regionApis;
    }

    @Override
    public void loadData() {
        Items items = new Items();
        mView.onDataUpdated(items);
        mMovieApis.getMovie("2")
                .subscribeOn(Schedulers.newThread())
                .map(new Function<SeriesDataResponse<SeriesBean>, Items>() {
                    @Override
                    public Items apply(@NonNull SeriesDataResponse<SeriesBean> seriesBean) throws Exception {
                        return movieBean2Items(seriesBean.getBody());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Items>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        Log.i("cyh111", "请求走到了。。。。");
                    }

                    @Override
                    public void onNext(@NonNull Items items) {
                        mView.onDataUpdated(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError = " + e);
                        e.printStackTrace();
                        mView.showLoadFailed();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");

                    }
                });
    }

    private Items movieBean2Items(List<SeriesBean> seriesBeans) {
        Items items = new Items();

        for (SeriesBean seriesBean : seriesBeans) {

            RecommendBean.PartTitle partTitle = new RecommendBean().new PartTitle();
            partTitle.setTitle(seriesBean.getHome_name());
            items.add(partTitle);
            if (seriesBean.getList() != null) {
                List<SeriesBean.Episode> episodes = seriesBean.getList();
                for (SeriesBean.Episode episode : episodes) {
                    items.add(episode);
                }
            }

            MovieFooterItemViewBinder.MovieFooter footer = new MovieFooterItemViewBinder.MovieFooter();
            footer.setMovie("查看更多");
            items.add(footer);
        }

/*        List<RecommendBean.ComlumInfo> comlumInfos = recommendBean.getComlum_list();

        for (RecommendBean.ComlumInfo comlunItem : comlumInfos) {

            RecommendBean.PartTitle partTitle = new RecommendBean().new PartTitle();
            partTitle.setTitle(comlunItem.getHome_name());
            items.add(partTitle);

            List<RecommendBean.ComlumInfo.ItemInfo> itemInfos = comlunItem.getList();

            // 如果是电影 type = 1
            if (comlunItem.getType() == 2) {
                for (RecommendBean.ComlumInfo.ItemInfo itemInfo : itemInfos) {
                    items.add(itemInfo);
                }
            }

            Recommend1FooterItemViewBinder.Recommend1Footer footer = new Recommend1FooterItemViewBinder.Recommend1Footer();
            footer.setRecommend1("查看更多");
            items.add(footer);

        }*/
        return items;
    }

    @Override
    public void releaseData() {

    }
}
