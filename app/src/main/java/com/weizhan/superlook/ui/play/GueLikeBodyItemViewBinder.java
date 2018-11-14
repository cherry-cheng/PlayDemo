package com.weizhan.superlook.ui.play;

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
import com.weizhan.superlook.model.bean.play.PlayInfoBean;
import com.weizhan.superlook.model.event.PlayPost;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/18.
 */

public class GueLikeBodyItemViewBinder extends ItemViewBinder<PlayInfoBean.PlayRecommendBean, GueLikeBodyItemViewBinder.GueLikeBodyViewHolder> {

    @NonNull
    @Override
    protected GueLikeBodyItemViewBinder.GueLikeBodyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_guelike_body, parent, false);
        return new GueLikeBodyItemViewBinder.GueLikeBodyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull GueLikeBodyItemViewBinder.GueLikeBodyViewHolder holder, @NonNull final PlayInfoBean.PlayRecommendBean item) {
        Context context = holder.ivCover.getContext();
        holder.ivCover.setImageURI(item.getV_img());
        holder.tvAreaTitle.setText(item.getDescribes());
        holder.tv_big_title.setText(item.getTitle());
        holder.score.setText(item.getScore());
        holder.item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayPost playPost = new PlayPost();
                playPost.setId(item.getId());
                playPost.setType(item.getType());
                EventBus.getDefault().post(playPost);
            }
        });
    }

    static class GueLikeBodyViewHolder extends RecyclerView.ViewHolder {

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

        public GueLikeBodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
