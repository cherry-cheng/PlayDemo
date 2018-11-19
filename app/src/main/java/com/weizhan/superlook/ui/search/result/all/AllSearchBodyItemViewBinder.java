package com.weizhan.superlook.ui.search.result.all;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.ui.play.Play1Activity;
import com.weizhan.superlook.ui.play.Play2Activity;
import com.weizhan.superlook.ui.play.PlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/5.
 */

public class AllSearchBodyItemViewBinder extends ItemViewBinder<SeriesBean.EpisodeSearch, AllSearchBodyItemViewBinder.Recommend1BodyViewHolder> {

    @NonNull
    @Override
    protected AllSearchBodyItemViewBinder.Recommend1BodyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_allsearch_body, parent, false);
        return new AllSearchBodyItemViewBinder.Recommend1BodyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllSearchBodyItemViewBinder.Recommend1BodyViewHolder holder, @NonNull final SeriesBean.EpisodeSearch item) {
        final Context context = holder.ivCover.getContext();
        String type = "电影";
        if (item.getType() == 1) {
            holder.ivCover.setImageURI(item.getH_img());
            holder.update_tv.setText("");
            type = "电影";
        } else {
            if (item.getType() == 3) {
                holder.update_tv.setText(item.getTimes() + "期");
                type = "综艺";
            } else {
                type = "电视剧";
                if (item.getCurrent_num() >= item.getTotal()) {
                    holder.update_tv.setText("完结");
                } else {
                    holder.update_tv.setText("更新至" + item.getCurrent_num() + "集");
                }
            }
            holder.ivCover.setImageURI(item.getV_img());
        }
        holder.big_tv.setText(item.getTitle());
        holder.score.setText(item.getScore());
        holder.small_des.setText(item.getDescribes());
        holder.category_tv.setText(type + " | " + item.getPlace());

        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type = item.getType();
                if (type == 1) {
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type", item.getType());
                    context.startActivity(intent);
                } else if (type == 2) {
                    Intent intent = new Intent(context, Play1Activity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type", item.getType());
                    context.startActivity(intent);
                } else if (type == 3) {
                    Intent intent = new Intent(context, Play2Activity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type", item.getType());
                    context.startActivity(intent);
                }
            }
        });
    }

    static class Recommend1BodyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.score)
        TextView score;
        @BindView(R.id.big_tv)
        TextView big_tv;
        @BindView(R.id.small_des)
        TextView small_des;
        @BindView(R.id.category_tv)
        TextView category_tv;
        @BindView(R.id.update_tv)
        TextView update_tv;
        @BindView(R.id.rl_item)
        RelativeLayout rl_item;

        public Recommend1BodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
