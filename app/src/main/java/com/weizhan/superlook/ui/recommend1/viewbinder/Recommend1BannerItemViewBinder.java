package com.weizhan.superlook.ui.recommend1.viewbinder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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
import com.weizhan.superlook.App;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.widget.banner.BannerAdapter;
import com.weizhan.superlook.widget.banner.SmartViewPager;

import org.w3c.dom.Text;

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

    static class Recommend1BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        SmartViewPager banner;

        private Recommend1BannerAdapter adapter;

        public Recommend1BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.setNeedCirculate(true);
            banner.setNeedAutoScroll(true);
            banner.setIndicatorGravity(Gravity.BOTTOM | Gravity.RIGHT);
            banner.setIndicatorColor(ContextCompat.getColor(itemView.getContext(), R.color.white),
                    ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
            /*int height = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.banner_item_height);
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            banner.setLayoutParams(params);*/
            adapter = new Recommend1BannerAdapter(itemView.getContext());
        }

        public void setData(List<RecommendBean.Banner> data) {
            adapter.setData(data, true);
            if (App.getData() != null) {
                App.getData().clear();
            }
            App.setData(data);
            banner.setAdapter(adapter);
        }
    }

    static class Recommend1BannerAdapter extends BannerAdapter<RecommendBean.Banner, SimpleDraweeView> {

        private Context context;

        public Recommend1BannerAdapter(Context context) {
            this.context = context;
        }

        @Override
        protected int getLayoutId() {
            return INVALID_ID;
        }

        @Override
        protected SimpleDraweeView getItemView() {
            SimpleDraweeView imageView = new SimpleDraweeView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setCornersRadius(context.getResources().getDimensionPixelSize(R.dimen.index_card_radius));
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
            GenericDraweeHierarchy hierarchy = builder.build();
            hierarchy.setRoundingParams(roundingParams);
            imageView.setHierarchy(hierarchy);
            return imageView;
        }

        @Override
        protected TextView getTitleView() {
            ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.height = 100;
            params.width = 100;
            TextView textView = new TextView(context);
            textView.setTextSize(20);
            textView.setTextColor(Color.WHITE);
            textView.setLayoutParams(params);
            return textView;
        }

        @Override
        protected void bindData(SimpleDraweeView itemView, TextView title, final RecommendBean.Banner item) {
            int width = ScreenUtil.getScreenWidth(context) - SystemUtil.dp2px(context, 16);
            int height = context.getResources().getDimensionPixelSize(R.dimen.banner_item_height);
            ImageUtil.load(itemView, item.getImgurl(), width, height);
            title.setText(item.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showLongToast(item.getTitle());
                }
            });
        }
    }
}
