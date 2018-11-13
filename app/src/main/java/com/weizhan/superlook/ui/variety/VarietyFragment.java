package com.weizhan.superlook.ui.variety;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.base.BaseMvpFragment;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.model.bean.variety.AppVarietyShow;
import com.weizhan.superlook.model.event.RefreshEvent;
import com.weizhan.superlook.ui.recommend1.viewbinder.Recommend1PartitionItemViewBinder;
import com.weizhan.superlook.ui.series.viewbinder.SeriesBodyItemViewBinder;
import com.weizhan.superlook.ui.series.viewbinder.SeriesFooterItemViewBinder;
import com.weizhan.superlook.ui.variety.viewbinder.VarietyBodyItemViewBinder;
import com.weizhan.superlook.ui.variety.viewbinder.VarietyFooterItemViewBinder;
import com.weizhan.superlook.ui.variety.viewbinder.VarietyPartitionItemViewBinder;
import com.weizhan.superlook.widget.adapter.CommonAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/5.
 */

public class VarietyFragment extends BaseMvpFragment<VarietyPresenter> implements VarietyContract.View {

    public static final String TAG = VarietyFragment.class.getSimpleName();

    private static final int SPAN_COUNT = 2;

    @BindView(R.id.rv_region)
    RecyclerView mRecyclerView;

    private CommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_variety;
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
        mRecyclerView.addItemDecoration(new VarietyIndexItemDecoration());
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mAdapter = new CommonAdapter(0, 3);
        /*mAdapter.register(AppVarietyShow.Partition.class, new VarietyPartitionItemViewBinder());
        mAdapter.register(AppVarietyShow.Body.class, new VarietyBodyItemViewBinder());
        mAdapter.register(VarietyFooterItemViewBinder.VarietyFooter.class, new VarietyFooterItemViewBinder());*/
        mAdapter.register(RecommendBean.PartTitle.class, new Recommend1PartitionItemViewBinder());
        mAdapter.register(SeriesBean.Episode.class, new VarietyBodyItemViewBinder());
        mAdapter.register(VarietyFooterItemViewBinder.VarietyFooter.class, new VarietyFooterItemViewBinder());
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
