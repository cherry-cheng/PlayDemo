package com.weizhan.superlook.ui.test.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.WeiXinJingXuanBean;
import com.weizhan.superlook.ui.test.adapter.NewsItemViewBinder;
import com.weizhan.superlook.ui.test.mvp.contract.NewsContract;
import com.weizhan.superlook.ui.test.mvp.presenter.NewsPresenter;
import com.weizhan.superlook.widget.adapter.CommonAdapter;
import com.common.base.BaseMvpFragment;
import com.common.widget.adapter.DefaultAdapterWrapper;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jiayiyang on 17/4/14.
 */

public class NewsPageFragment extends BaseMvpFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private CommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_news;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommonAdapter(0, 99);
        mAdapter.register(WeiXinJingXuanBean.NewsList.class, new NewsItemViewBinder(getContext()));
        mAdapter.setOnLoadMoreListener(new DefaultAdapterWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateData(List<WeiXinJingXuanBean.NewsList> list) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        mAdapter.setItems(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(List<WeiXinJingXuanBean.NewsList> list) {
//        mAdapter.addItems(list);
//        mAdapter.loadMoreComplete();
        mAdapter.showNoMore();
    }

    @Override
    public void setRefreshing() {
        mRefreshLayout.setRefreshing(true);
    }
}