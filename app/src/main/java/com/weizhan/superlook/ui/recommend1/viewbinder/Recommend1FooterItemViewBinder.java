package com.weizhan.superlook.ui.recommend1.viewbinder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.util.ToastUtils;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.event.RangeRefresh;
import com.weizhan.superlook.ui.region.series.SeriesRActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/5.
 */

public class Recommend1FooterItemViewBinder extends ItemViewBinder<Recommend1FooterItemViewBinder.Recommend1Footer, Recommend1FooterItemViewBinder.Recommend1FooterViewHolder> {

    @NonNull
    @Override
    protected Recommend1FooterViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_more, parent, false);
        return new Recommend1FooterViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull Recommend1FooterViewHolder holder, @NonNull final Recommend1Footer item) {
        final Context context = holder.tvMore.getContext();
        final int position = holder.getPosition();
        Log.i("cyh444", "position = " + position);
//        String region = holder.tvMore.getContext().getString(R.string.more_format, item.getRecommend1());
        holder.tvMore.setText(item.getRecommend1());
        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeriesRActivity.class);
                intent.putExtra("which", 2);
                context.startActivity(intent);
            }
        });

        holder.change_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据局部刷新
                ToastUtils.showLongToast("局部刷新test");
                RangeRefresh rangeRefresh = new RangeRefresh();
                rangeRefresh.setId(item.getId());
                rangeRefresh.setType(item.getType());
                rangeRefresh.setPosition(position);
                rangeRefresh.setLast_page(item.getLast_page());
                EventBus.getDefault().post(rangeRefresh);
            }
        });
    }

    static class Recommend1FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.change_tv)
        TextView change_tv;

        public Recommend1FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class Recommend1Footer {

        private String region;
        private int last_page;
        private int id;
        private int type;

        public String getRecommend1() {
            return region;
        }

        public void setRecommend1(String region) {
            this.region = region;
        }

        public int getId() {
            return id;
        }

        public int getType() {
            return type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }
    }
}
