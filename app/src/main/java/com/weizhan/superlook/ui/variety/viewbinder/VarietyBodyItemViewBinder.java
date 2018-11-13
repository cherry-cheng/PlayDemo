package com.weizhan.superlook.ui.variety.viewbinder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.util.StringUtil;
import com.common.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.series.SeriesBean;
import com.weizhan.superlook.model.bean.variety.AppVarietyShow;
import com.weizhan.superlook.ui.play.Play1Activity;
import com.weizhan.superlook.ui.play.Play2Activity;
import com.weizhan.superlook.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/5.
 */

public class VarietyBodyItemViewBinder extends ItemViewBinder<SeriesBean.Episode, VarietyBodyItemViewBinder.VarietyBodyViewHolder> {

    @NonNull
    @Override
    protected VarietyBodyItemViewBinder.VarietyBodyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_variety_body, parent, false);
        return new VarietyBodyItemViewBinder.VarietyBodyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull VarietyBodyItemViewBinder.VarietyBodyViewHolder holder, @NonNull SeriesBean.Episode item) {
        final Context context = holder.ivCover.getContext();
        holder.ivCover.setImageURI(item.getV_img());
        holder.tvAreaTitle.setText(item.getTitle());
        holder.score.setText(item.getScore());
        holder.tv_big_title.setText(item.getTitle());
        holder.searial_number.setText(item.getTimes() + "期");
        holder.item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLongToast("点击了综艺");
                Intent intent = new Intent(context, Play2Activity.class);
                intent.putExtra("url", Constants.PLAY_URL);
                intent.putExtra("isLive", true);
                context.startActivity(intent);
            }
        });
    }

    static class VarietyBodyViewHolder extends RecyclerView.ViewHolder {

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

        public VarietyBodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
