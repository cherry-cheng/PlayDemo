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
import com.weizhan.superlook.ui.play.Play1Activity;
import com.weizhan.superlook.ui.play.Play2Activity;
import com.weizhan.superlook.ui.play.PlayerActivity;
import com.weizhan.superlook.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/5.
 */

public class Recommend1BodyItemViewBinder extends ItemViewBinder<RecommendBean.ComlumInfo.ItemInfo, Recommend1BodyItemViewBinder.Recommend1BodyViewHolder> {

    @NonNull
    @Override
    protected Recommend1BodyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_recommend1_body, parent, false);
        return new Recommend1BodyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull Recommend1BodyViewHolder holder, @NonNull final RecommendBean.ComlumInfo.ItemInfo item) {
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
                if (item.getType() == 2) {
                    Intent intent = new Intent(context, Play1Activity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type", item.getType());
                    context.startActivity(intent);
                } else {
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

        public Recommend1BodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
