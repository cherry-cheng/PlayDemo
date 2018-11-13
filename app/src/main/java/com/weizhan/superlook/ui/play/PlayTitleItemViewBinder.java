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
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/19.
 */

public class PlayTitleItemViewBinder extends ItemViewBinder<PlayInfoBean.PlayBean, PlayTitleItemViewBinder.PlayTitleViewHolder> {


    private tvClick clickListenr;

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
    protected void onBindViewHolder(@NonNull final PlayTitleItemViewBinder.PlayTitleViewHolder holder, @NonNull PlayInfoBean.PlayBean item) {
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
/*                if (holder.iv_displayIntro.isSelected()) {
                    holder.iv_displayIntro.setSelected(false);
                    holder.ll_display.setVisibility(View.GONE);
                } else {
                    holder.iv_displayIntro.setSelected(true);
                    holder.ll_display.setVisibility(View.VISIBLE);
                }*/
                clickListenr.ontvClick(holder.iv_displayIntro, holder.ll_display);

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
        public PlayTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface tvClick {
        void ontvClick(ImageView imageView, LinearLayout linearLayout);
    }
}
