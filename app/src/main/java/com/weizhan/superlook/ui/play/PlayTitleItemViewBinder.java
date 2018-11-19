package com.weizhan.superlook.ui.play;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.play.PlayInfoBean;
import com.weizhan.superlook.util.SpUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/19.
 */

public class PlayTitleItemViewBinder extends ItemViewBinder<PlayInfoBean.PlayBean, PlayTitleItemViewBinder.PlayTitleViewHolder> {


    private tvClick clickListenr;

    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setClickListenr(tvClick listenr) {
        clickListenr = listenr;
    }

    @NonNull
    @Override
    protected PlayTitleItemViewBinder.PlayTitleViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.play_title_item, parent, false);
        return new PlayTitleItemViewBinder.PlayTitleViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlayTitleItemViewBinder.PlayTitleViewHolder holder, @NonNull final PlayInfoBean.PlayBean item) {
        final Context context = holder.iv_displayIntro.getContext();
        holder.title.setText(item.getTitle());
        holder.tv_score.setText(item.getScore());
        holder.tv_type.setText(item.getStyle_name());
        holder.tv_year.setText(item.getYears());
        holder.tv_actor.setText("主演：" + item.getActors());
        holder.des_tv.setText("剧情简介：" + item.getSummary());
        holder.iv_displayIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenr.ontvClick(holder.iv_displayIntro, holder.ll_display);
            }
        });

        holder.like_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用搜藏接口
                int isLove = 1;
                if (holder.like_iv.isSelected()) {
                    isLove = 2;
                } else {
                    isLove = 1;
                }
                clickListenr.onUserLoveClick(holder.like_iv, item.getId(), item.getType(), isLove);
            }
        });

        this.imageView = holder.like_iv;

        holder.share_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //分享应用
                clickListenr.onUserShareClick(holder.share_iv);
            }
        });
    }

    static class PlayTitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_displayIntro)
        ImageView iv_displayIntro;
        @BindView(R.id.ll_display)
        LinearLayout ll_display;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_score)
        TextView tv_score;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.tv_year)
        TextView tv_year;
        @BindView(R.id.tv_actor)
        TextView tv_actor;
        @BindView(R.id.des_tv)
        TextView des_tv;
        @BindView(R.id.like_iv)
        ImageView like_iv;
        @BindView(R.id.share_iv)
        ImageView share_iv;
        public PlayTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface tvClick {
        void ontvClick(ImageView imageView, LinearLayout linearLayout);
        void onUserLoveClick(ImageView imageView, int id, int type, int isLove);
        void onUserShareClick(ImageView imageView);
    }
}
