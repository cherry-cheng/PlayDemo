package com.weizhan.superlook.ui.series;
import android.util.Log;
import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.model.api.SeriesApis;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.recommend1.ChangeBean;
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

    Items items = new Items();
    @Inject
    public SeriesPresenter(SeriesApis regionApis) {
        mSeriesApis = regionApis;
    }

    @Override
    public void loadData() {
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
                    public void onNext(@NonNull Items items1) {
                        items = items1;
                        mView.onDataUpdated(items1);
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
        int number = 0;
        for (SeriesBean seriesBean : seriesBeans) {
            number ++;
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
            footer.setId(seriesBean.getId());
            footer.setType(seriesBean.getType());
            footer.setPlace(seriesBean.getPlace());
            footer.setStyle_name(seriesBean.getStyle_name());
            footer.setLast_page(seriesBean.getLast_page());
            items.add(footer);

        }
        return items;
    }


    public void refreshRange(int id, int type, int page, final int position, String style_name, String place) {
        Log.e("cyh556", "refreshRange = " + id + " " + page + " " + style_name + " " + place + " " + type);

        mSeriesApis.getChangeInfo(id, page, style_name, place, type)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<TTDataResponse<ChangeBean>, Items>() {
                    @Override
                    public Items apply(@NonNull TTDataResponse<ChangeBean> changeBean) throws Exception {
                        Log.i("cyh556", "请求走到了。。。。" + changeBean.getMsg());
                        return changeBean2Items(changeBean.getBody(), position);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Items>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        Log.i("cyh556", "请求走到了。。。。");
                    }

                    @Override
                    public void onNext(@NonNull Items items1) {
//                        mView.onDataUpdated(items1);
                        mView.onDataRangeUpdate(items, position - 6, 6);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("cyh556", "onError = " + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("cyh556", "onComplete");
                    }
                });
    }


    private Items changeBean2Items(ChangeBean changeBean, int position) {
        /*List<RecommendBean.ComlumInfo.ItemInfo> changeList = changeBean.getData();
        int startP = position - 6;
        int endP = position - 1;
        for (int i = startP; i <= endP ; i ++) {
            items.set(i, changeList.get(i-startP));
        }
        return items;*/
        return items;
    }


    @Override
    public void releaseData() {

    }
}
