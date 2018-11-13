package com.weizhan.superlook.ui.history.past;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.movie.AppMovieShow;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1Show;
import com.weizhan.superlook.ui.movie.viewbinder.MoviePartitionItemViewBinder;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/11/1.
 */

public class PastTitleItemViewBinder extends ItemViewBinder<AppRecommend1Show.Partition, PastTitleItemViewBinder.PartitionViewHolder> {

    @NonNull
    @Override
    protected PastTitleItemViewBinder.PartitionViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_past_partition, parent, false);
        return new PastTitleItemViewBinder.PartitionViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull PastTitleItemViewBinder.PartitionViewHolder holder, @NonNull AppRecommend1Show.Partition item) {
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
