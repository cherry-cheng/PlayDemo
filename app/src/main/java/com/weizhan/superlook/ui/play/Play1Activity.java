package com.weizhan.superlook.ui.play;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.common.base.BaseActivity;
import com.common.base.IBaseMvpActivity;
import com.common.util.ToastUtils;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.umeng.socialize.UMShareAPI;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.play.PlayInfoBean;
import com.weizhan.superlook.model.bean.play.TestBean;
import com.weizhan.superlook.model.bean.play.TestSeriesBean;
import com.weizhan.superlook.model.event.PartNumBean;
import com.weizhan.superlook.model.event.Play;
import com.weizhan.superlook.model.event.PlayPost;
import com.weizhan.superlook.util.ShareUtil;
import com.weizhan.superlook.util.SpUtils;
import com.weizhan.superlook.widget.adapter.CommonAdapter;
import com.weizhan.superlook.widget.dialog.SharePopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by Administrator on 2018/9/18.
 */

public class Play1Activity extends BaseActivity implements IBaseMvpActivity<Play1Presenter>, Play1Contract.View {

    private IjkVideoView ijkVideoView;
    private SharePopWindow sharePopWindow;

    @Inject
    Play1Presenter mPresenter;
    @BindView(R.id.guessLike_recyclerView)
    RecyclerView guessLike_recyclerView;

    private CommonAdapter mAdapter;
    private static final int SPAN_COUNT = 2;
    PlayTitleItemViewBinder playTitleItemViewBinder;

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
        int id = intent.getIntExtra("id", 0);
        int type = intent.getIntExtra("type", 0);
        Log.e("PlayPresenter", "id = " + id + " type = " + type);
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = mAdapter.getItems().get(position);
                return item instanceof PlayInfoBean.PlayRecommendBean ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        guessLike_recyclerView.setLayoutManager(layoutManager);
        guessLike_recyclerView.addItemDecoration(new GuessLike1ItemDecoration());
        guessLike_recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mAdapter = new CommonAdapter(0, 99);
        playTitleItemViewBinder = new PlayTitleItemViewBinder();
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

            @Override
            public void onUserLoveClick(ImageView imageView, int id, int type, int isLove) {
                //用户收藏~~
                if (SpUtils.getBoolean(Play1Activity.this, "isLogin", false)) {
                    mPresenter.lovesMovie(id, type, isLove);
                } else {
                    ToastUtils.showLongToast("请先登录");
                }
            }

            @Override
            public void onUserShareClick(ImageView imageView) {
                //分享应用
                sharePopWindow = new SharePopWindow(Play1Activity.this, itemsOnClick);
                sharePopWindow.setAnimationStyle(R.style.PopupWindow);
                sharePopWindow.showAtLocation(imageView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        mAdapter.register(PlayInfoBean.PlayBean.class, playTitleItemViewBinder);
        mAdapter.register(PlayInfoBean.PlayRecommendBean.class, new GueLikeBodyItemViewBinder());
        mAdapter.register(TestBean.class, new GuessTitleViewBinder());
        mAdapter.register(TestSeriesBean.class, new ChooseIndexItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        guessLike_recyclerView.setAdapter(mAdapter);

        mPresenter.loadPlayInfo(id, type);

    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sharePopWindow.dismiss();
            sharePopWindow.backgroundAlpha(Play1Activity.this, 1f);
            switch (view.getId()) {
                case R.id.weixinf:
                    ShareUtil.weiXin(view, Play1Activity.this);
                    break;
                case R.id.wechatc:
                    ShareUtil.weixinCircle(view, Play1Activity.this);
                    break;
                case R.id.sina:
                    ShareUtil.sina(view, Play1Activity.this);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public Play1Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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
    public void showPlay(String url, String title) {
        //播放影片
        ijkVideoView.release();
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
        ijkVideoView.setUrl(url);
        ijkVideoView.setTitle(title);
        ijkVideoView.setVideoController(controller);
        ijkVideoView.start();
        Log.e("cyh777", "play---------1--");
        EventBus.getDefault().post(new Play());
    }

    @Override
    public void updateIsLove(int isLove) {
        //将是否收藏的按键改变状态
        ImageView imageView = playTitleItemViewBinder.getImageView();
        if (isLove == 0) {
            //未登录
            imageView.setSelected(false);
        } else if (isLove == 1) {
            //收藏
            imageView.setSelected(true);
        } else {
            //未收藏
            imageView.setSelected(false);
        }
    }

    @Override
    public void loveMovies(int love) {
        ImageView imageView = playTitleItemViewBinder.getImageView();
        boolean isLove = imageView.isSelected();
        if (love == 0) {
            //未登录
        } else if (love == 1) {
            //成功
            if (isLove) {
                imageView.setSelected(false);
            } else {
                imageView.setSelected(true);
            }
        } else {
            //失败
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayRefresh(PlayPost playPost) {
        if (playPost != null) {
            mPresenter.loadPlayInfo(playPost.getId(), playPost.getType());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayPartNum(PartNumBean partNumBean) {
        if (partNumBean != null) {
            showPlay(partNumBean.getUrl(), partNumBean.getTitle());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(Play1Activity.this).onActivityResult(requestCode, resultCode, data);
        Log.i("cyh333", "有回调妈？？？？？？？？");
    }

}
