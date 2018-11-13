package com.weizhan.superlook.ui.mine;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.BaseMvpFragment;
import com.common.util.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.mine.PostUserBean;
import com.weizhan.superlook.model.event.RefreshEvent;
import com.weizhan.superlook.ui.mine.login.Defaultcontent;
import com.weizhan.superlook.ui.mine.login.ShareUtils;
import com.weizhan.superlook.ui.search.need.NeedMovieActivity;
import com.weizhan.superlook.util.FileUtils;
import com.weizhan.superlook.util.ImageUtil;
import com.weizhan.superlook.util.SpUtils;
import com.weizhan.superlook.widget.dialog.SharePopWindow;
import com.weizhan.superlook.widget.dialog.UpdateDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.Items;

public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View {

    public static final String TAG = MineFragment.class.getSimpleName();
    private static final int TYPE_BTN_CLICK = 2;
    private static final String WEIXIN_TYPE = "2";
    private static final String WEIBO_TYPE = "3";

    @BindView(R.id.weixin_iv)
    ImageView weixin_iv;
    @BindView(R.id.weibo_iv)
    ImageView weibo_iv;
    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.rl_login)
    RelativeLayout rl_login;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.tv_loggout)
    TextView tv_loggout;
    @BindView(R.id.view6)
    View view6;

    private SharePopWindow sharePopWindow;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TYPE_BTN_CLICK:
                    weixin_iv.setEnabled(true);
                    weibo_iv.setEnabled(true);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @OnClick(R.id.weixin_iv)
    void weixinLogin(View view) {
        //微信登录
        weiXinLogin(view);
        weixin_iv.setEnabled(false);
        weibo_iv.setEnabled(false);
        handler.sendEmptyMessageDelayed(TYPE_BTN_CLICK, 5000);
    }

    @OnClick(R.id.weibo_iv)
    void weiboLogin(View view) {
        //微博登录
        sinaLogin(view);
        weixin_iv.setEnabled(false);
        weibo_iv.setEnabled(false);
        handler.sendEmptyMessageDelayed(TYPE_BTN_CLICK, 5000);
    }

    @OnClick(R.id.tv_need)
    void needMovie() {
        //我要求片
        Intent intent = new Intent(getActivity(), NeedMovieActivity.class);
        intent.putExtra("isMine", true);
        startActivity(intent);
    }

    @OnClick(R.id.tv_share)
    void shareApp(View view) {
        //分享应用
        sharePopWindow = new SharePopWindow(getActivity(), itemsOnClick);
        sharePopWindow.setAnimationStyle(R.style.PopupWindow);
        sharePopWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sharePopWindow.dismiss();
            sharePopWindow.backgroundAlpha(getActivity(), 1f);
            switch (view.getId()) {
                case R.id.weixinf:
                    weiXin(view);
                    break;
                case R.id.wechatc:
                    weixinCircle(view);
                    break;
                case R.id.sina:
                    sina(view);
                    break;
                default:
                    break;
            }
        }
    };

    public void weiXin(View view) {
        ShareUtils.shareWeb(getActivity(), Defaultcontent.url, Defaultcontent.title
                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon, SHARE_MEDIA.WEIXIN
        );
    }

    public void weixinCircle(View view) {
        ShareUtils.shareWeb(getActivity(), Defaultcontent.url, Defaultcontent.title
                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon, SHARE_MEDIA.WEIXIN_CIRCLE
        );
    }

    public void sina(View view) {
        ShareUtils.shareWeb(getActivity(), Defaultcontent.url, Defaultcontent.title
                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon, SHARE_MEDIA.SINA
        );
    }

    @OnClick(R.id.tv_clean)
    void cleanCache() {
        //清除缓存
        FileUtils.clearCache();
        ToastUtils.showLongToast("缓存清除成功");
    }

    @OnClick(R.id.tv_declare)
    void declareProtocle() {
        //免责声明
        startActivity(new Intent(getActivity(), DeclareActivity.class));
    }

    @OnClick(R.id.tv_update)
    void updateCheck() {
        //检查更新
        final UpdateDialog updateDialog = new UpdateDialog(getActivity(), "发现新版本",
                "现在更新", getResources().getString(R.string.update_content), true);
        updateDialog.show();
        updateDialog.setClickListener(new UpdateDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                updateDialog.dismiss();
                ToastUtils.showLongToast("正在下载");
            }

            @Override
            public void doCancel() {
                updateDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.tv_loggout)
    void logOut() {
        //退出登录
        rl_login.setVisibility(View.GONE);
        rl_title.setVisibility(View.VISIBLE);
        tv_loggout.setVisibility(View.GONE);
        view6.setVisibility(View.GONE);
        SpUtils.putBoolean(getContext(), "isLogin", false);
        ToastUtils.showLongToast("已退出");
    }

    public void weiXinLogin(View view) {
        authorization(SHARE_MEDIA.WEIXIN, WEIXIN_TYPE);
    }

    public void sinaLogin(View view) {
        authorization(SHARE_MEDIA.SINA, WEIBO_TYPE);
    }

    //授权
    private void authorization(SHARE_MEDIA share_media, final String type) {
        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");
                Log.i("cyh222", "icon url = " + iconurl);
                //拿到信息去请求登录接口。。。
                PostUserBean postUserBean = new PostUserBean();
                postUserBean.setOpenid(uid);
                postUserBean.setUsername(name);
                postUserBean.setHeaderimg(iconurl);
                mPresenter.verifyUserInfo(postUserBean);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }


    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        checkLogin();
    }

    private void checkLogin() {
        if (SpUtils.getBoolean(getContext(), "isLogin", false)) {
            rl_title.setVisibility(View.GONE);
            rl_login.setVisibility(View.VISIBLE);
            tv_loggout.setVisibility(View.VISIBLE);
            view6.setVisibility(View.VISIBLE);
            Log.i("cyh222", "islogin");
            String iconurl = SpUtils.getString(getContext(), "iconurl", "abc");
            String nameq = SpUtils.getString(getContext(), "name", "abc");
            ImageUtil.loadUserIconImage(getActivity(), iconurl, avatar);
            name.setText(nameq);
        } else {
            rl_title.setVisibility(View.VISIBLE);
            rl_login.setVisibility(View.GONE);
            tv_loggout.setVisibility(View.GONE);
            view6.setVisibility(View.GONE);
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

    @Override
    public void onDataUpdated() {
        checkLogin();
    }

    @Override
    public void showLoadFailed() {
        ToastUtils.showLongToast("服务器出小差了");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent refreshEvent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
        Log.i("cyh333", "有回调妈？？？？？？？？");
    }

}
