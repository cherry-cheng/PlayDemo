package com.weizhan.superlook.util;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.weizhan.superlook.R;

/**
 * Created by Administrator on 2018/9/27.
 */

public class ImageUtil {
    public static void loadUserIconImage(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_icon_notloggedin)
                .resize(44, 44)
                .centerCrop()
                .into(imageView);
    }
}
