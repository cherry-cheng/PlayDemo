package com.weizhan.superlook.ui.region.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/7/8.
 */

public class RegionPartitionItemViewBinder extends ItemViewBinder< RecommendBean.PartTitle, RegionPartitionItemViewBinder.PartitionViewHolder> {

    @NonNull
    @Override
    protected PartitionViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_region_partition, parent, false);
        return new PartitionViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull PartitionViewHolder holder, @NonNull  RecommendBean.PartTitle item) {
        holder.tvName.setText(item.getTitle());
    }

    static class PartitionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        public PartitionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
