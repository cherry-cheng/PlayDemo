package com.weizhan.superlook.ui.region.viewbinder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weizhan.superlook.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weizhan.superlook.model.bean.region.CatePost;
import com.weizhan.superlook.model.bean.region.RegionBean;
import com.weizhan.superlook.ui.region.series.SeriesRActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/7/9.
 */

public class RegionBodyItemViewBinder extends ItemViewBinder<RegionBean.SmallCatBean, RegionBodyItemViewBinder.RegionBodyViewHolder> {

    @NonNull
    @Override
    protected RegionBodyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_region_body, parent, false);
        return new RegionBodyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull RegionBodyViewHolder holder, @NonNull final RegionBean.SmallCatBean item) {
        final Context context = holder.ivCover.getContext();
        holder.ivCover.setImageURI(item.getImgurl());
        holder.tvAreaTitle.setText(item.getCategory_name());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeriesRActivity.class);
                CatePost catePost = new CatePost();
                catePost.setPlaces(item.getPlace());
                catePost.setStyles(item.getStyle_name());
                catePost.setType(item.getType());
                catePost.setYears("0");
                catePost.setHot_type("0");
                intent.putExtra("catepost", catePost);
                context.startActivity(intent);
            }
        });
    }

    static class RegionBodyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.tv_area_title)
        TextView tvAreaTitle;
        @BindView(R.id.card)
        CardView card;

        public RegionBodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
