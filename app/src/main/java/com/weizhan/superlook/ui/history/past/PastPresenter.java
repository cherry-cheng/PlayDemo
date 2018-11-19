package com.weizhan.superlook.ui.history.past;

import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.bean.past.PastBean;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.util.AppUtils;
import com.weizhan.superlook.util.RealmHelper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/5.
 */

public class PastPresenter extends AbsBasePresenter<PastContract.View> {

    private static final String TAG = PastPresenter.class.getSimpleName();

    private Recommend1Apis mRecommend1Apis;

    @Inject
    public PastPresenter(Recommend1Apis regionApis) {
        mRecommend1Apis = regionApis;
    }

    @Override
    public void loadData() {
        Items items = new Items();
        //从数据库中获取数据
        items = regionShow2Items(RealmHelper.getInstance().getPlayInfoAll());
        mView.onDataUpdated(items);
    }

    private Items regionShow2Items(List<PastBean> pastBeans) {
        Items items = new Items();
        List<PastBean> pastTodays = new ArrayList<PastBean>();
        List<PastBean> pastYesTodays = new ArrayList<PastBean>();
        List<PastBean> pastOldDays = new ArrayList<PastBean>();
        Log.e("cyh777", "pastBean = " + pastBeans.size() + "content = " + pastBeans.get(0).getTitle());
        for (PastBean pastBean : pastBeans) {
            int process = AppUtils.format(pastBean.getInsertTime());
            if (process == 1) {
                pastTodays.add(pastBean);
            } else if (process == 0) {
                pastYesTodays.add(pastBean);
            } else {
                pastOldDays.add(pastBean);
            }
        }

        if (pastTodays.size() > 0) {
            RecommendBean.PartTitle partTitle = new RecommendBean().new PartTitle();
            partTitle.setTitle("今天");
            items.add(partTitle);
            for (PastBean pastBean : pastTodays) {
                items.add(pastBean);
            }
        }

        if (pastYesTodays.size() > 0) {
            RecommendBean.PartTitle partTitle = new RecommendBean().new PartTitle();
            partTitle.setTitle("昨天");
            items.add(partTitle);
            for (PastBean pastBean : pastYesTodays) {
                items.add(pastBean);
            }
        }

        if (pastOldDays.size() > 0) {
            RecommendBean.PartTitle partTitle = new RecommendBean().new PartTitle();
            partTitle.setTitle("更早");
            items.add(partTitle);
            for (PastBean pastBean : pastOldDays) {
                items.add(pastBean);
            }
        }

        return items;
    }


    @Override
    public void releaseData() {

    }
}
