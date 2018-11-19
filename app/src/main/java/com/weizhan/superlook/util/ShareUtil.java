package com.weizhan.superlook.util;

import android.app.Activity;
import android.view.View;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.weizhan.superlook.R;
import com.weizhan.superlook.ui.mine.login.Defaultcontent;
import com.weizhan.superlook.ui.mine.login.ShareUtils;

/**
 * Created by Administrator on 2018/11/16.
 */

public class ShareUtil {
    public static void weiXin(View view, Activity mContext) {
        ShareUtils.shareWeb(mContext, Defaultcontent.url, Defaultcontent.title
                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon, SHARE_MEDIA.WEIXIN
        );
    }

    public static void weixinCircle(View view, Activity mContext) {
        ShareUtils.shareWeb(mContext, Defaultcontent.url, Defaultcontent.title
                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon, SHARE_MEDIA.WEIXIN_CIRCLE
        );
    }

    public static void sina(View view, Activity mContext) {
        ShareUtils.shareWeb(mContext, Defaultcontent.url, Defaultcontent.title
                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon, SHARE_MEDIA.SINA
        );
    }
}
