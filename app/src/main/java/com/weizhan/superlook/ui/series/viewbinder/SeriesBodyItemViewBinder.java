package com.weizhan.superlook.ui.series.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.common.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.series.SeriesBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/5.
 */

public class SeriesBodyItemViewBinder extends ItemViewBinder<SeriesBean.Episode, SeriesBodyItemViewBinder.SeriesBodyViewHolder> {

    @NonNull
    @Override
    protected SeriesBodyItemViewBinder.SeriesBodyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_series_body, parent, false);
        return new SeriesBodyItemViewBinder.SeriesBodyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull SeriesBodyItemViewBinder.SeriesBodyViewHolder holder, @NonNull final SeriesBean.Episode item) {
        final Context context = holder.ivCover.getContext();
        holder.ivCover.setImageURI(item.getV_img());
        holder.tv_big_title.setText(item.getTitle());
        holder.tvAreaTitle.setText(item.getDescribes());
        holder.score.setText(item.getScore());
        if (item.getCurrent_num() >= item.getTotal()) {
            holder.searial_number.setText("完结");
        } else {
            holder.searial_number.setText("更新至" + item.getCurrent_num() + "集");
        }
        holder.item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLongToast("点击了电视剧");
                /*Intent intent = new Intent(context, Play1Activity.class);
                intent.putExtra("url", item.get);
                intent.putExtra("isLive", true);
                context.startActivity(intent);*/ //少了播放地址
            }
        });
    }

    static class SeriesBodyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.tv_area_title)
        TextView tvAreaTitle;
        @BindView(R.id.item_rl)
        RelativeLayout item_rl;
        @BindView(R.id.tv_big_title)
        TextView tv_big_title;
        @BindView(R.id.score)
        TextView score;
        @BindView(R.id.searial_number)
        TextView searial_number;

        public SeriesBodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
