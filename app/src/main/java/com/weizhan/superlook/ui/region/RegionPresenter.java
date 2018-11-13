package com.weizhan.superlook.ui.region;

import android.util.Log;
import com.weizhan.superlook.model.api.RegionApis;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.bean.region.RegionBean;
import com.common.base.AbsBasePresenter;
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
 * Created by Android_ZzT on 17/7/6.
 */

public class RegionPresenter extends AbsBasePresenter<RegionContract.View> {

    private static final String TAG = RegionPresenter.class.getSimpleName();

    private RegionApis mRegionApis;

    @Inject
    public RegionPresenter(RegionApis regionApis) {
        mRegionApis = regionApis;
    }

    @Override
    public void loadData() {
        Items items = new Items();
        mView.onDataUpdated(items);
        mRegionApis.getCateInfo()
                .subscribeOn(Schedulers.newThread())
                .map(new Function<SeriesDataResponse<RegionBean>, Items>() {
                    @Override
                    public Items apply(@NonNull SeriesDataResponse<RegionBean> reginBeans) throws Exception {
                        return regionShow2Items(reginBeans.getBody());
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

    private Items regionShow2Items(List<RegionBean> regionBeans) {
        Items items = new Items();
        for (RegionBean regionBean : regionBeans) {
            //partition
            RecommendBean.PartTitle partTitle = new RecommendBean().new PartTitle();
            partTitle.setTitle(regionBean.getBigcategory_name());
            items.add(partTitle);

            List<RegionBean.SmallCatBean> smallCatBeans = regionBean.getSmallcategorylist();
            for (RegionBean.SmallCatBean smallCatBean : smallCatBeans) {
                items.add(smallCatBean);
            }
        }
/*        List<AppRegionShow> regionShowList = regionShow.getData();
        for (AppRegionShow appRegionShow : regionShowList) {
            //partition
            AppRegionShow.Partition p = appRegionShow.new Partition();
            p.setTitle(appRegionShow.getTitle());
//            p.setLogo(ResourceManager.getRegionIconByTitle(appRegionShow.getTitle()));
//            p.setLogo(ResourceManager.getRegionIconByParam(appRegionShow.getParam()));
            appRegionShow.setPartition(p);
            items.add(p);

            //body
            List<AppRegionShow.Body> bodyList = appRegionShow.getBody();
            for (AppRegionShow.Body b : bodyList) {
                items.add(b);
            }
        }*/
        return items;
    }


    @Override
    public void releaseData() {

    }
}
