package com.weizhan.superlook.ui.recommend1;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.common.base.BaseMvpFragment;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.event.RangeRefresh;
import com.weizhan.superlook.model.event.RefreshEvent;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1BMItemViewBinder;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1BannerItemViewBinder;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1BodyItemViewBinder;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1FooterItemViewBinder;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1PartitionItemViewBinder;
import com.weizhan.superlook.util.SpUtils;
import com.weizhan.superlook.widget.adapter.CommonAdapter;
import com.weizhan.superlook.widget.dialog.UpdateDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/5.
 */

public class Recommend1Fragment extends BaseMvpFragment<Recommend1Presenter> implements Recommend1Contract.View {

    public static final String TAG = Recommend1Fragment.class.getSimpleName();

    private static final int SPAN_COUNT = 3;
    private static final int SPAN_COUNT3 = 6;

    private int page1 = 1;
    private int page2 = 1;
    private int page3 = 1;

    @BindView(R.id.rv_region)
    RecyclerView mRecyclerView;

    private CommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend1;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT3);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = mAdapter.getItems().get(position);
                if (item instanceof RecommendBean.MovieItem) {
                    return 2;
                }
                return item instanceof RecommendBean.ComlumInfo.ItemInfo ? SPAN_COUNT : SPAN_COUNT3;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new Recommend1IndexItemDecoration());
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mAdapter = new CommonAdapter(0, 0);
//        mAdapter.register(SeriesHeaderItemViewBinder.Recommend1Header.class, new SeriesHeaderItemViewBinder());
        mAdapter.register(RecommendBean.Banners.class, new Recommend1BannerItemViewBinder());
        mAdapter.register(RecommendBean.PartTitle.class, new Recommend1PartitionItemViewBinder());
        mAdapter.register(RecommendBean.ComlumInfo.ItemInfo.class, new Recommend1BodyItemViewBinder());
        mAdapter.register(RecommendBean.MovieItem.class, new Recommend1BMItemViewBinder());
        mAdapter.register(Recommend1FooterItemViewBinder.Recommend1Footer.class, new Recommend1FooterItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        mRecyclerView.setAdapter(mAdapter);

        if (SpUtils.getBoolean(getContext(), "isfirst", true)) {
            onDisclaimer();
            SpUtils.putBoolean(getContext(), "isfirst", false);
        }

        //初始页数
        page1 = 1;
        page2 = 1;
        page3 = 1;
    }

    //弹出免责声明
    public void onDisclaimer() {
        final UpdateDialog updateDialog = new UpdateDialog(getActivity(), "免责声明",
                "同意", getResources().getString(R.string.delare_content), true);
        updateDialog.show();
        updateDialog.setClickListener(new UpdateDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                updateDialog.dismiss();
            }

            @Override
            public void doCancel() {
                updateDialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDataUpdated(Items items) {
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadFailed() {
        mAdapter.showLoadFailed();
    }

    @Override
    public void onDataRangeUpdate(Items items, int positionStart, int itemCount) {
        mAdapter.setItems(items);
        mAdapter.notifyItemRangeChanged(positionStart, itemCount, "abc");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent refreshEvent) {
        mPresenter.loadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRangeRefresh(RangeRefresh rangeRefresh) {
        //为局部刷新做准备
        int i = rangeRefresh.getId();
        int last_page = rangeRefresh.getLast_page();
        Log.e("cyh555", "rangeRefresh = " + rangeRefresh.getId() + "  " + rangeRefresh.getLast_page());
        if (i == 1) {
            page1 ++ ;
            Log.e("cyh555", "page1 = " + page1);
            if (page1 < last_page) {
                mPresenter.refreshRange(rangeRefresh.getId(), rangeRefresh.getType(), page1, rangeRefresh.getPosition());
            }
        }
        if (i == 2) {
            page2 ++;
            if (page2 < last_page) {
                mPresenter.refreshRange(rangeRefresh.getId(), rangeRefresh.getType(), page2, rangeRefresh.getPosition());
            }
        }
        if (i == 3) {
            page3 ++;
            if (page3 < last_page) {
                mPresenter.refreshRange(rangeRefresh.getId(), rangeRefresh.getType(), page3, rangeRefresh.getPosition());
            }
        }
    }
}
