package com.weizhan.superlook.ui.recommend1.viewbinder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.SystemUtil;
import com.common.util.ToastUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.widget.banner.BannerAdapter;
import com.weizhan.superlook.widget.banners.Banner;
import com.weizhan.superlook.widget.banners.BannerConfig;
import com.weizhan.superlook.widget.banners.GlideImageLoader;
import com.weizhan.superlook.widget.banners.listener.OnBannerClickListener;
import com.weizhan.superlook.widget.banners.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/5.
 */

public class Recommend1BannerItemViewBinder extends ItemViewBinder<RecommendBean.Banners, Recommend1BannerItemViewBinder.Recommend1BannerViewHolder> {

    @NonNull
    @Override
    protected Recommend1BannerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_banner, parent, false);
        return new Recommend1BannerViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull Recommend1BannerViewHolder holder, @NonNull RecommendBean.Banners item) {
        holder.setData(item.getBanners());
    }

    static class Recommend1BannerViewHolder extends RecyclerView.ViewHolder implements OnBannerListener {

        @BindView(R.id.banner)
        Banner banner;

        public Recommend1BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        }

        public void setData(List<RecommendBean.Banner> data) {
            List<String> images = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (RecommendBean.Banner ban : data) {
                images.add(ban.getImgurl());
                titles.add(ban.getTitle());
            }
            banner.setImages(images)
                    .setBannerTitles(titles)
                    .setImageLoader(new GlideImageLoader())
                    .start();
        }

        @Override
        public void OnBannerClick(int position) {
            ToastUtils.showLongToast("position = " + position);
        }
    }
}
