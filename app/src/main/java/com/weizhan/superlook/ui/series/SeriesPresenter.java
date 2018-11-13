package com.weizhan.superlook.ui.series;
import android.util.Log;
import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.model.api.SeriesApis;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.bean.series.SeriesBean;
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

public class SeriesPresenter extends AbsBasePresenter<SeriesContract.View> {

    private static final String TAG = SeriesPresenter.class.getSimpleName();

    private SeriesApis mSeriesApis;

    @Inject
    public SeriesPresenter(SeriesApis regionApis) {
        mSeriesApis = regionApis;
    }

    @Override
    public void loadData() {
        Items items = new Items();
        mView.onDataUpdated(items);
        mSeriesApis.getSeries("1")
                .subscribeOn(Schedulers.newThread())
                .map(new Function<SeriesDataResponse<SeriesBean>, Items>() {
                    @Override
                    public Items apply(@NonNull SeriesDataResponse<SeriesBean> seriesBean) throws Exception {
                        return seriesBean2Items(seriesBean.getBody());
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

    private Items seriesBean2Items(List<SeriesBean> seriesBeans) {
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

            SeriesFooterItemViewBinder.SeriesFooter footer = new SeriesFooterItemViewBinder.SeriesFooter();
            footer.setSeries("查看更多");
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
