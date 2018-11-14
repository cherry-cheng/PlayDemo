package com.weizhan.superlook.ui.region.series;

import android.text.TextUtils;
import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;
import com.common.util.ToastUtils;
import com.google.gson.Gson;
import com.weizhan.superlook.model.api.ApiHelper;
import com.weizhan.superlook.model.api.SeriesApis;
import com.weizhan.superlook.model.bean.DataListResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.region.CateInfoBean;
import com.weizhan.superlook.model.bean.region.CatePost;
import com.weizhan.superlook.model.bean.region.RecyclerTitleBean;
import com.weizhan.superlook.model.bean.series.AppSeriesShow;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.ui.series.viewbinder.SeriesFooterItemViewBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jiayiyang on 17/3/25.
 */

public class SeriesRPresenter extends AbsBasePresenter<SeriesRContract.View> implements SeriesRContract.Presenter {

    private static final String TAG = SeriesRPresenter.class.getSimpleName();
    private SeriesApis mSeriesApis;

    private int position3 = -1;

    private int position2 = -1;

    Items itemsAll = new Items();

    @Inject
    public SeriesRPresenter(SeriesApis regionApis) {
        mSeriesApis = regionApis;
    }


    @Override
    public void loadData() {

    }

    private Items cateMoreInfoShow2Items(CateInfoBean cateInfoBean, int type, String styleName, String placeName, String hot_type) {
        Items items = new Items();
        List<String> list1 = new ArrayList<String>();
        list1.addAll(Arrays.asList("综合排序", "热播", "好评", "新上线"));
        List<String> list2 = new ArrayList<String>();
        List<String> list3 = new ArrayList<String>();
        int htype = Integer.parseInt(hot_type);
        if (cateInfoBean.getPlace_list() != null) {
            list2.add("全部地区");
            list2.addAll(cateInfoBean.getPlace_list());
            if (placeName.equals("0")) {
                position2 = 0;
            } else {
                position2 = list2.indexOf(placeName);
            }
        }
        if (cateInfoBean.getStyle_list() != null) {
            list3.add("全部类型");
            list3.addAll(cateInfoBean.getStyle_list());
            //返回选中坐标
            if (styleName.equals("0")) {
                position3 = 0;
            } else {
                position3 = list3.indexOf(styleName);
            }
        }

        RecyclerTitleBean recyclerTitleBean = new RecyclerTitleBean();
        recyclerTitleBean.setType(type);
        recyclerTitleBean.setRecDataList1(list1);
        recyclerTitleBean.setRecDataList2(list2);
        recyclerTitleBean.setRecDataList3(list3);
        if (position3 != -1) {
            recyclerTitleBean.setPosition3(position3);
        } else {
            recyclerTitleBean.setPosition3(0);
        }
        if (position2 != -1) {
            recyclerTitleBean.setPosition2(position2);
        } else {
            recyclerTitleBean.setPosition2(0);
        }

        recyclerTitleBean.setPosition1(htype);
        items.add(recyclerTitleBean);

        List<SeriesBean.Episode> episodes = cateInfoBean.getRecommendlist();
        for (SeriesBean.Episode episode : episodes) {
            items.add(episode);
        }
        return items;
    }

    @Override
    public void releaseData() {

    }

    @Override
    public void loadDataWithParas(final CatePost catePost) {
        mView.onDataUpdated(itemsAll);
        String json = new Gson().toJson(catePost);
        Log.i("cyh222", "json = " + json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        mSeriesApis.getMoreCateInfo(requestBody)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<TTDataResponse<CateInfoBean>, Items>() {
                    @Override
                    public Items apply(@NonNull TTDataResponse<CateInfoBean> cateInfoBean) throws Exception {
                        return cateMoreInfoShow2Items(cateInfoBean.getBody(), catePost.getType(), catePost.getStyles(), catePost.getPlaces(), catePost.getHot_type());
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
                        itemsAll = items;
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

    @Override
    public void loadRangeDataWithParas(final CatePost catePost) {
        String json = new Gson().toJson(catePost);
        Log.i("cyh222", "json = " + json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        mSeriesApis.getMoreCateInfo(requestBody)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<TTDataResponse<CateInfoBean>, Items>() {
                    @Override
                    public Items apply(@NonNull TTDataResponse<CateInfoBean> cateInfoBean) throws Exception {
                        return cateMoreInfoShow2Items(cateInfoBean.getBody(), catePost.getType(), catePost.getStyles(), catePost.getPlaces(), catePost.getHot_type());
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
                        mView.onDataRangeUpdated(items, itemsAll, 1);
                        //保持老数据的更新到上一次
                        itemsAll = items;
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
