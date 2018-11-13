package com.weizhan.superlook.ui.history;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.common.base.BaseFragment;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.event.TabSelectedEvent;
import com.weizhan.superlook.ui.history.collect.CollectFragment;
import com.weizhan.superlook.ui.history.off.OffFragment;
import com.weizhan.superlook.ui.history.past.PastFragment;
import com.weizhan.superlook.ui.main.MainActivity;
import com.weizhan.superlook.ui.movie.MovieFragment;
import com.weizhan.superlook.ui.recommend1.Recommend1Fragment;
import com.weizhan.superlook.ui.series.SeriesFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页主Fragment
 * Created by jiayiyang on 17/4/14.
 */

public class MainHistoryFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Inject
    CollectFragment collectFragment;
    @Inject
    OffFragment offFragment;
    @Inject
    PastFragment pastFragment;

    private HistoryPagerAdapter adapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        mTitles = getResources().getStringArray(R.array.history_section);
        initChildFragment();
        adapter = new HistoryPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).select();
    }

    private void initChildFragment() {
        mFragments.add(collectFragment);
//        mFragments.add(offFragment);
        mFragments.add(pastFragment);
    }

    private class HistoryPagerAdapter extends FragmentPagerAdapter {

        HistoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TabSelectedEvent event){
        if(event.getPosition() == MainActivity.FIRST){

        }
    }

}
