package com.weizhan.superlook.ui.play;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.base.BaseActivity;
import com.common.base.IBaseMvpActivity;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.play.TestBean;
import com.weizhan.superlook.model.bean.play.TestSeriesBean;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1Show;
import com.weizhan.superlook.widget.adapter.CommonAdapter;
import com.weizhan.superlook.widget.adapter.SeriesAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/18.
 */

public class Play1Activity extends BaseActivity implements IBaseMvpActivity<Play1Presenter>, Play1Contract.View {

    private IjkVideoView ijkVideoView;

    @Inject
    Play1Presenter mPresenter;
    @BindView(R.id.guessLike_recyclerView)
    RecyclerView guessLike_recyclerView;

    private CommonAdapter mAdapter;
    private static final int SPAN_COUNT = 2;

    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.release();
    }

    @Override
    public void onBackPressed() {
        if (!ijkVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public int getLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        return R.layout.play1_activity;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    public void initViewAndEvent() {
        ijkVideoView = findViewById(R.id.player);

        Intent intent = getIntent();

        if (intent != null) {
            StandardVideoController controller = new StandardVideoController(this);
            ijkVideoView.setPlayerConfig(new PlayerConfig.Builder()
                    .autoRotate()//自动旋转屏幕
//                    .enableCache()//启用边播边存
//                    .enableMediaCodec()//启动硬解码
//                    .usingSurfaceView()//使用SurfaceView
//                    .setCustomMediaPlayer(new ExoMediaPlayer(this))
//                    .setCustomMediaPlayer(new AndroidMediaPlayer(this))
                    .build());
//            ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
            ijkVideoView.setUrl(intent.getStringExtra("url"));
            ijkVideoView.setVideoController(controller);
            ijkVideoView.setTitle("西红柿首富SSSSSSSSSSSSSSSSSSSBBBBBBBBBBBBBBB西红柿首富~~~~~~~~~~~~~~~~~~~~~");
            ijkVideoView.start();
        }


        final GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = mAdapter.getItems().get(position);
                return item instanceof AppRecommend1Show.Body ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        guessLike_recyclerView.setLayoutManager(layoutManager);
        guessLike_recyclerView.addItemDecoration(new GuessLike1ItemDecoration());
        guessLike_recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mAdapter = new CommonAdapter(0, 99);
        PlayTitleItemViewBinder playTitleItemViewBinder = new PlayTitleItemViewBinder();
        playTitleItemViewBinder.setClickListenr(new PlayTitleItemViewBinder.tvClick() {
            @Override
            public void ontvClick(ImageView imageView, LinearLayout linearLayout) {
                if (imageView.isSelected()) {
                    imageView.setSelected(false);
                    linearLayout.setVisibility(View.GONE);
                } else {
                    imageView.setSelected(true);
                    linearLayout.setVisibility(View.VISIBLE);
                    guessLike_recyclerView.scrollToPosition(0);
                }
            }
        });
//        mAdapter.register(AppRecommend1Show.Partition.class, playTitleItemViewBinder);
        mAdapter.register(TestBean.class, new GuessTitleViewBinder());
        mAdapter.register(AppRecommend1Show.Body.class, new GueLikeBodyItemViewBinder());
        mAdapter.register(TestSeriesBean.class, new ChooseIndexItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        guessLike_recyclerView.setAdapter(mAdapter);

    }

    @Override
    public Play1Presenter getPresenter() {
        return mPresenter;
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
