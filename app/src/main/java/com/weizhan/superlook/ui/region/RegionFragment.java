package com.weizhan.superlook.ui.region;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.bean.region.RegionBean;
import com.weizhan.superlook.model.event.RefreshEvent;
import com.weizhan.superlook.ui.region.viewbinder.RegionBodyItemViewBinder;
import com.weizhan.superlook.ui.region.viewbinder.RegionPartitionItemViewBinder;
import com.weizhan.superlook.widget.adapter.CommonAdapter;
import com.common.base.BaseMvpFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.drakeet.multitype.Items;

public class RegionFragment extends BaseMvpFragment<RegionPresenter> implements RegionContract.View {

    public static final String TAG = RegionFragment.class.getSimpleName();

    private static final int SPAN_COUNT = 2;

    @BindView(R.id.rv_region)
    RecyclerView mRecyclerView;

    private CommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_region;
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
                return item instanceof RegionBean.SmallCatBean ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RegionIndexItemDecoration());
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mAdapter = new CommonAdapter(0, 4);
        mAdapter.register( RecommendBean.PartTitle.class, new RegionPartitionItemViewBinder());
        mAdapter.register(RegionBean.SmallCatBean.class, new RegionBodyItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent refreshEvent) {
        mPresenter.loadData();
    }

}
