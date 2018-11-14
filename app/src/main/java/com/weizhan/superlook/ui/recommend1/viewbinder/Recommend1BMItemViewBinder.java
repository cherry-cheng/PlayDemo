package com.weizhan.superlook.ui.recommend1.viewbinder;

import android.content.Context;
import android.content.Intent;
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
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.ui.play.PlayerActivity;
import com.weizhan.superlook.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/6.
 */

public class Recommend1BMItemViewBinder extends ItemViewBinder<RecommendBean.MovieItem, Recommend1BMItemViewBinder.Recommend1BMViewHolder> {

    @NonNull
    @Override
    protected Recommend1BMViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_movie_body, parent, false);
        return new Recommend1BMViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull Recommend1BMViewHolder holder, @NonNull final RecommendBean.MovieItem item) {
        final Context context = holder.ivCover.getContext();
        holder.ivCover.setImageURI(item.getV_img());
        holder.tvAreaTitle.setText(item.getDescribes());
        holder.score.setText(item.getScore());
        holder.tv_big_title.setText(item.getTitle());
        holder.item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("type", item.getType());
                context.startActivity(intent);
            }
        });
    }

    static class Recommend1BMViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.tv_area_title)
        TextView tvAreaTitle;
        @BindView(R.id.item_rl)
        RelativeLayout item_rl;
        @BindView(R.id.score)
        TextView score;
        @BindView(R.id.tv_big_title)
        TextView tv_big_title;

        public Recommend1BMViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
