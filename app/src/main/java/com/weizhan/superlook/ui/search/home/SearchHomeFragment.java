package com.weizhan.superlook.ui.search.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.base.BaseMvpFragment;
import com.common.util.ToastUtils;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.KeyWordBean;
import com.weizhan.superlook.model.bean.search.SearchKey;
import com.weizhan.superlook.model.event.ClickMessage;
import com.weizhan.superlook.ui.search.viewbinder.HotSearchViewBinder;
import com.weizhan.superlook.util.RealmHelper;
import com.weizhan.superlook.widget.adapter.CommonAdapter;
import com.weizhan.superlook.widget.textview.AutoLinefeedLayout;

import org.greenrobot.eventbus.EventBus;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;

public class SearchHomeFragment extends BaseMvpFragment<SearchHomePresenter> implements SearchHomeContract.View {

    public static final String TAG = SearchHomeFragment.class.getSimpleName();

    @BindView(R.id.search_history)
    AutoLinefeedLayout search_history;
    @BindView(R.id.hotRecyclerView)
    RecyclerView hotRecyclerView;
    @BindView(R.id.searchHistory_rl)
    RelativeLayout searchHistory_rl;
    @BindView(R.id.clearAll)
    ImageView clearAll;

    private CommonAdapter mAdapter;
    private static final int SPAN_COUNT = 1;
    List<SearchKey> searchHistory;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_home;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        hotRecyclerView.setLayoutManager(layoutManager);
        hotRecyclerView.addItemDecoration(new SearchIndexItemDecoration());
        hotRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mAdapter = new CommonAdapter(0, 99);
        mAdapter.register(KeyWordBean.class, new HotSearchViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        hotRecyclerView.setAdapter(mAdapter);
        setHistory();
    }

    private void setHistory() {
        searchHistory = RealmHelper.getInstance().getSearchHistoryListAll();
        if (searchHistory != null && searchHistory.size() > 0) {
            searchHistory_rl.setVisibility(View.VISIBLE);
            search_history.removeAllViews();
            for (int i = 0; i < searchHistory.size(); i++) {
                View view = LinearLayout.inflate(getContext(), R.layout.item_hot_key, null);
                TextView textView = (TextView) view.findViewById(R.id.textview);
                textView.setText(searchHistory.get(i).getSearchKey());
                search_history.addView(view);
                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showLongToast("点击了历史记录");
                        ClickMessage clickMessage = new ClickMessage();
                        if (searchHistory != null && searchHistory.size() > 0) {
                            clickMessage.setSearchString(searchHistory.get(finalI).getSearchKey());
                            EventBus.getDefault().post(clickMessage);
                        }
                    }
                });
            }
        } else {
            searchHistory_rl.setVisibility(View.GONE);
        }

/*        //装入假数据
        List<SearchKey> historySearch = new ArrayList<SearchKey>();
        for (int i = 0; i < 6; i++) {
            SearchKey searchKey = new SearchKey();
            searchKey.setSearchKey("西红柿首富");
            searchKey.setInsertTime(System.currentTimeMillis());
            historySearch.add(searchKey);
        }*/

    }

    @OnClick(R.id.clearAll)
    void onClearAll() {
        RealmHelper.getInstance().deleteSearchHistoryAll();
        searchHistory_rl.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
//        EventBus.getDefault().unregister(this);
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
}
