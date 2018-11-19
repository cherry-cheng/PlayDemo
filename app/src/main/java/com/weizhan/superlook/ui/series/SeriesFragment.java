package com.weizhan.superlook.ui.series;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.common.base.BaseMvpFragment;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.model.event.RefreshEvent;
import com.weizhan.superlook.model.event.SeriesRangeRefresh;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1PartitionItemViewBinder;
import com.weizhan.superlook.ui.series.viewbinder.SeriesBodyItemViewBinder;
import com.weizhan.superlook.ui.series.viewbinder.SeriesFooterItemViewBinder;
import com.weizhan.superlook.widget.adapter.CommonAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/5.
 */

public class SeriesFragment extends BaseMvpFragment<SeriesPresenter> implements SeriesContract.View {

    public static final String TAG = SeriesFragment.class.getSimpleName();

    private static final int SPAN_COUNT = 2;

    @BindView(R.id.rv_region)
    RecyclerView mRecyclerView;

    private CommonAdapter mAdapter;

    private int page1 = 0;
    private int page2 = 0;
    private int page3 = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_series;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = mAdapter.getItems().get(position);
                return item instanceof SeriesBean.Episode ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SeriesIndexItemDecoration());
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mAdapter = new CommonAdapter(0, 1);
        mAdapter.register(RecommendBean.PartTitle.class, new Recommend1PartitionItemViewBinder());
        mAdapter.register(SeriesBean.Episode.class, new SeriesBodyItemViewBinder());
        mAdapter.register(SeriesFooterItemViewBinder.SeriesFooter.class, new SeriesFooterItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        mRecyclerView.setAdapter(mAdapter);

        //初始页数
        page1 = 0;
        page2 = 0;
        page3 = 0;
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
    public void onRangeRefresh(SeriesRangeRefresh rangeRefresh) {
        //为局部刷新做准备
        int i = rangeRefresh.getId();
        int last_page = rangeRefresh.getLast_page();
        Log.e("cyh555", "rangeRefresh = " + rangeRefresh.getId() + "  " + rangeRefresh.getLast_page());
        if (i == 8) {
            page1 ++ ;
            Log.e("cyh555", "page1 = " + page1);
            if (page1 < last_page) {
                mPresenter.refreshRange(rangeRefresh.getId(), rangeRefresh.getType(), page1, rangeRefresh.getPosition(), rangeRefresh.getStyle_name(), rangeRefresh.getPlace());
            }
        }
        if (i == 9) {
            page2 ++;
            if (page2 < last_page) {
                mPresenter.refreshRange(rangeRefresh.getId(), rangeRefresh.getType(), page2, rangeRefresh.getPosition(), rangeRefresh.getStyle_name(), rangeRefresh.getPlace());
            }
        }
        if (i == 23) {
            page3 ++;
            if (page3 < last_page) {
                mPresenter.refreshRange(rangeRefresh.getId(), rangeRefresh.getType(), page3, rangeRefresh.getPosition(), rangeRefresh.getStyle_name(), rangeRefresh.getPlace());
            }
        }
    }
}
