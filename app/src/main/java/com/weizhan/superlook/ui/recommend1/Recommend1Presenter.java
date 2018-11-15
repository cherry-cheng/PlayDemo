package com.weizhan.superlook.ui.recommend1;
import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.recommend1.ChangeBean;
import com.weizhan.superlook.model.bean.recommend1.KeyWordBean;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1FooterItemViewBinder;
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

public class Recommend1Presenter extends AbsBasePresenter<Recommend1Contract.View> {

    private static final String TAG = "cyh111";

    private Recommend1Apis mRecommend1Apis;

    Items items = new Items();

    @Inject
    public Recommend1Presenter(Recommend1Apis regionApis) {
        mRecommend1Apis = regionApis;
    }

    @Override
    public void loadData() {
//        items.add(new SeriesHeaderItemViewBinder.Recommend1Header());
        mView.onDataUpdated(items);
        mRecommend1Apis.getRecommend("0")
                .subscribeOn(Schedulers.newThread())
                .map(new Function<TTDataResponse<RecommendBean>, Items>() {
                    @Override
                    public Items apply(@NonNull TTDataResponse<RecommendBean> recommendBean) throws Exception {
                        return recommendBean2Items(recommendBean.getBody());
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

    public void refreshRange(int id, int type, int page, final int position) {
        Log.e("cyh555", "refreshRange = " + id + " " + type + " " + page + " " + position);

        mRecommend1Apis.getChangeInfo(id, type, page)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<TTDataResponse<ChangeBean>, Items>() {
                    @Override
                    public Items apply(@NonNull TTDataResponse<ChangeBean> changeBean) throws Exception {
                        return changeBean2Items(changeBean.getBody(), position);
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
//                        mView.onDataUpdated(items1);
                        mView.onDataRangeUpdate(items, position - 6, 6);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError = " + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private Items changeBean2Items(ChangeBean changeBean, int position) {
        List<RecommendBean.ComlumInfo.ItemInfo> changeList = changeBean.getData();
        int startP = position - 6;
        int endP = position - 1;
        for (int i = startP; i <= endP ; i ++) {
            items.set(i, changeList.get(i-startP));
        }
        return items;
    }

    private Items recommendBean2Items(RecommendBean recommendBean) {
        Items items = new Items();

        if (recommendBean.getBanner() != null) {
            RecommendBean.Banners banners = new RecommendBean().new Banners();
            banners.setBanners(recommendBean.getBanner());
            items.add(banners);
        }

        List<RecommendBean.ComlumInfo> comlumInfos = recommendBean.getComlum_list();

        for (RecommendBean.ComlumInfo comlunItem : comlumInfos) {

            RecommendBean.PartTitle partTitle = new RecommendBean().new PartTitle();
            partTitle.setTitle(comlunItem.getHome_name());
            items.add(partTitle);

            List<RecommendBean.ComlumInfo.ItemInfo> itemInfos = comlunItem.getList();

            // 如果是电影 type = 1
            if (comlunItem.getType() == 1) {
                for (RecommendBean.ComlumInfo.ItemInfo itemInfo: itemInfos) {
                    RecommendBean.MovieItem movieItem = new RecommendBean().new MovieItem();
                    movieItem.setCurrent_num(itemInfo.getCurrent_num());
                    movieItem.setH_img(itemInfo.getH_img());
                    movieItem.setId(itemInfo.getId());
                    movieItem.setLinkurl(itemInfo.getLinkurl());
                    movieItem.setScore(itemInfo.getScore());
                    movieItem.setDescribes(itemInfo.getDescribes());
                    movieItem.setTitle(itemInfo.getTitle());
                    movieItem.setTotal(itemInfo.getTotal());
                    movieItem.setV_img(itemInfo.getV_img());
                    movieItem.setType(itemInfo.getType());
                    Log.i("cyh111", "movie iteminfo = " + movieItem.getTitle());
                    items.add(movieItem);
                }
            } else {
                for (RecommendBean.ComlumInfo.ItemInfo itemInfo : itemInfos) {
                    items.add(itemInfo);
                }
            }
            //通过查看更多的size 来倒推需要更新数据的位置，只要数据固定为6个
            Recommend1FooterItemViewBinder.Recommend1Footer footer = new Recommend1FooterItemViewBinder.Recommend1Footer();
            footer.setRecommend1("查看更多");
            footer.setId(comlunItem.getId());
            footer.setType(comlunItem.getType());
            Log.i("cyh555", "ssslast _page = " + comlunItem.getLast_page());
            footer.setLast_page(comlunItem.getLast_page());
            items.add(footer);

        }
        return items;
    }


    @Override
    public void releaseData() {

    }
}
