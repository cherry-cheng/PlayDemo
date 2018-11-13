package com.weizhan.superlook.ui.region.series;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.common.base.BaseActivity;
import com.common.base.IBaseMvpActivity;
import com.common.util.ToastUtils;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.region.CatePost;
import com.weizhan.superlook.model.bean.region.RecyclerTitleBean;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.ui.movie.viewbinder.MovieBodyItemViewBinder;
import com.weizhan.superlook.ui.series.viewbinder.SeriesBodyItemViewBinder;
import com.weizhan.superlook.ui.variety.viewbinder.VarietyBodyItemViewBinder;
import com.weizhan.superlook.util.Constants;
import com.weizhan.superlook.widget.adapter.CommonAdapter;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/11.
 */

public class SeriesRActivity extends BaseActivity implements IBaseMvpActivity<SeriesRPresenter>, SeriesRContract.View {
    @Inject
    SeriesRPresenter mPresenter;
    @BindView(R.id.recyclerViewContent)
    RecyclerView recyclerViewContent;
    @BindView(R.id.title_big)
    TextView title_big;
    private CommonAdapter commonAdapter;
    private int SPAN_COUNT = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_seriesr;
    }

    @OnClick(R.id.back_iv)
    void onBack() {
        finish();
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    public void initViewAndEvent() {
        CatePost catePost = (CatePost) getIntent().getSerializableExtra("catepost");
        if (catePost.getType() == 1) {
            SPAN_COUNT = 3;
        } else {
            SPAN_COUNT = 2;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = commonAdapter.getItems().get(position);
                return item instanceof SeriesBean.Episode ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        recyclerViewContent.setLayoutManager(layoutManager);
        if (catePost.getType() == 1) {
            recyclerViewContent.addItemDecoration(new MovieRItemDecoration());
        } else {
            recyclerViewContent.addItemDecoration(new SeriesRItemDecoration());
        }
        recyclerViewContent.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        commonAdapter = new CommonAdapter(0, 1);
        TitleRecyItemViewBinder titleRecyItemViewBinder = new TitleRecyItemViewBinder();
        titleRecyItemViewBinder.setTitleChooseListner(new TitleRecyItemViewBinder.TitleChooseListner() {
            @Override
            public void onRecycler1Choose(int itemPosition, boolean isSelected) {
                ToastUtils.showLongToast("点击了" + itemPosition + isSelected);
            }

            @Override
            public void onRecycler2Choose(int itemPosition, boolean isSelected) {
                ToastUtils.showLongToast("点击了" + itemPosition + isSelected);
            }

            @Override
            public void onRecycler3Choose(int itemPosition, boolean isSelected) {
                ToastUtils.showLongToast("点击了" + itemPosition + isSelected);
            }
        });
        commonAdapter.register(RecyclerTitleBean.class, titleRecyItemViewBinder);
        if (catePost.getType() == 1) {
            commonAdapter.register(SeriesBean.Episode.class, new MovieBodyItemViewBinder());
        } else if (catePost.getType() == 2) {
            commonAdapter.register(SeriesBean.Episode.class, new SeriesBodyItemViewBinder());
        } else if (catePost.getType() == 3) {
            commonAdapter.register(SeriesBean.Episode.class, new VarietyBodyItemViewBinder());
        }
        commonAdapter.setScrollSaveStrategyEnabled(true);
        recyclerViewContent.setAdapter(commonAdapter);
        mPresenter.loadDataWithParas(catePost);

        if (catePost.getType() == 1) {
            title_big.setText(Constants.type_1);
        } else if (catePost.getType() == 2) {
            title_big.setText(Constants.type_2);
        } else if (catePost.getType() == 3) {
            title_big.setText(Constants.type_3);
        }
    }

    @Override
    public SeriesRPresenter getPresenter() {
        return mPresenter;
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
        commonAdapter.setItems(items);
        commonAdapter.notifyDataSetChanged();
    }

/*    @Override
    public void onDataResponse(int type) {
        if (type == 1) {
            easyAdapter3.setData(list2);
        }
    }*/

    @Override
    public void showLoadFailed() {
        commonAdapter.showLoadFailed();
    }

    @Override
    public void loadNow(int type, RecyclerTitleBean recyclerTitleBean) {

    }
}