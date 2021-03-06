package com.weizhan.superlook.ui.search.result.movie;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.common.base.BaseMvpFragment;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1Show;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.model.event.VisibleClickSearch;
import com.weizhan.superlook.ui.search.result.all.AllSearchBodyItemViewBinder;
import com.weizhan.superlook.widget.adapter.CommonAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/5.
 */

public class MovieSFragment extends BaseMvpFragment<MovieSPresenter> implements MovieSContract.View {

    public static final String TAG = MovieSFragment.class.getSimpleName();

    private static final int SPAN_COUNT = 1;
    @BindView(R.id.rv_region)
    RecyclerView mRecyclerView;

    private CommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_allsearch;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new MovieSIndexItemDecoration());
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mAdapter = new CommonAdapter(2, 99);
        mAdapter.register(SeriesBean.EpisodeSearch.class, new AllSearchBodyItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        mRecyclerView.setAdapter(mAdapter);

        Bundle bundle = this.getArguments();
        String searchword = bundle.getString("search");
        Log.i("cyh555", "bundle moviesfragment = " + searchword);
        mPresenter.onDataSearch("movieinfo", searchword);
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
    public void onEvent(VisibleClickSearch visibleClickSearch) {
        Log.i("cyh555", "visibleClickSearch = " + visibleClickSearch.getSearchword());
        mPresenter.onDataSearch("movieinfo", visibleClickSearch.getSearchword());
    }
}
