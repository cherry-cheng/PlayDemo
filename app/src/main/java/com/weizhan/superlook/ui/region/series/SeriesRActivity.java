package com.weizhan.superlook.ui.region.series;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.common.base.BaseActivity;
import com.common.base.IBaseMvpActivity;
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
import com.weizhan.superlook.widget.adapter.EasyAdapter;

import java.util.ArrayList;
import java.util.List;

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

//    TitleRecyItemViewBinder titleRecyItemViewBinder = new TitleRecyItemViewBinder();

    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;

    private EasyAdapter easyAdapter1;
    private EasyAdapter easyAdapter2;
    private EasyAdapter easyAdapter3;

    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    List<String> list3 = new ArrayList<String>();

    int type = 1;

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
        final CatePost catePost = (CatePost) getIntent().getSerializableExtra("catepost");
        type = catePost.getType();
        if (type == 1) {
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
/*        titleRecyItemViewBinder.setTitleChooseListner(new TitleRecyItemViewBinder.TitleChooseListner() {
            @Override
            public void onRecycler1Choose(int itemPosition, boolean isSelected) {
                if (true) {
                    catePost.setHot_type(itemPosition + "");
                    mPresenter.loadDataWithParas(catePost);
                }
            }

            @Override
            public void onRecycler2Choose(int itemPosition, boolean isSelected, String place) {
                if (true) {
                    if (place.equals("全部地区")) {
                        catePost.setPlaces("0");
                    } else {
                        catePost.setPlaces(place);
                    }
                    mPresenter.loadDataWithParas(catePost);
                }
            }

            @Override
            public void onRecycler3Choose(int itemPosition, boolean isSelected, String style) {
                if (true) {
                    if (style.equals("全部类型")) {
                        catePost.setStyles("0");
                    } else {
                        catePost.setStyles(style);
                    }
                    mPresenter.loadDataWithParas(catePost);
                }
            }
        });*/
//        commonAdapter.register(RecyclerTitleBean.class, titleRecyItemViewBinder);
        if (type == 1) {
            commonAdapter.register(SeriesBean.Episode.class, new MovieBodyItemViewBinder());
        } else if (type == 2) {
            commonAdapter.register(SeriesBean.Episode.class, new SeriesBodyItemViewBinder());
        } else if (type == 3) {
            commonAdapter.register(SeriesBean.Episode.class, new VarietyBodyItemViewBinder());
        }
        commonAdapter.setScrollSaveStrategyEnabled(true);
        recyclerViewContent.setAdapter(commonAdapter);

        if (type == 1) {
            title_big.setText(Constants.type_1);
        } else if (type == 2) {
            title_big.setText(Constants.type_2);
        } else if (type == 3) {
            title_big.setText(Constants.type_3);
        }

        easyAdapter1 = new EasyAdapter();
        easyAdapter2 = new EasyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); //把列表设置成水平滚动
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL); //把列表设置成水平滚动
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView1.setAdapter(easyAdapter1);
        recyclerView2.setAdapter(easyAdapter2);
        easyAdapter1.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {
            @Override
            public void onSelected(int itemPosition, boolean isSelected) {
                Log.e("cyh999", "选择处理----1");
                if (isSelected) {
                    catePost.setHot_type(itemPosition + "");
                    mPresenter.loadDataWithParas(catePost);
                }
            }
        });

        easyAdapter2.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {
            @Override
            public void onSelected(int itemPosition, boolean isSelected) {
                Log.e("cyh999", "选择处理----2");
                if (isSelected) {
                    if (itemPosition == 0) {
                        catePost.setPlaces("0");
                    } else {
                        catePost.setPlaces(list2.get(itemPosition));
                    }
                    mPresenter.loadDataWithParas(catePost);
                }
            }
        });

        if (type != 1) {
            recyclerView3.setVisibility(View.GONE);
        } else {
            easyAdapter3 = new EasyAdapter();
            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
            linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL); //把列表设置成水平滚动
            recyclerView3.setLayoutManager(linearLayoutManager3);
            recyclerView3.setAdapter(easyAdapter3);
            easyAdapter3.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {
                @Override
                public void onSelected(int itemPosition, boolean isSelected) {
                    Log.e("cyh999", "选择处理----3");
                    if (isSelected) {
                        if (itemPosition == 0) {
                            catePost.setStyles("0");
                        } else {
                            catePost.setStyles(list3.get(itemPosition));
                        }
                        mPresenter.loadDataWithParas(catePost);
                    }
                }
            });
        }

        //加载数据
        mPresenter.loadDataWithParas(catePost);
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

    @Override
    public void showLoadFailed() {
        commonAdapter.showLoadFailed();
    }

    @Override
    public void onDataRangeUpdated(RecyclerTitleBean recyclerTitleBean) {
        list1 = recyclerTitleBean.getRecDataList1();
        list2 = recyclerTitleBean.getRecDataList2();
        list3 = recyclerTitleBean.getRecDataList3();
        easyAdapter1.setData(list1, recyclerTitleBean.getPosition1());
        easyAdapter2.setData(list2, recyclerTitleBean.getPosition2());
        if (type == 1) {
            easyAdapter3.setData(list3, recyclerTitleBean.getPosition3());
        } else {
        }
    }
}
