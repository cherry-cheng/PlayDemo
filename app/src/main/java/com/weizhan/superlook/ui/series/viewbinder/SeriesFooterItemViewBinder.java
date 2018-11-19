package com.weizhan.superlook.ui.series.viewbinder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.region.CatePost;
import com.weizhan.superlook.model.event.SeriesRangeRefresh;
import com.weizhan.superlook.ui.region.series.SeriesRActivity;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/5.
 */

public class SeriesFooterItemViewBinder extends ItemViewBinder<SeriesFooterItemViewBinder.SeriesFooter, SeriesFooterItemViewBinder.SeriesFooterViewHolder> {

    @NonNull
    @Override
    protected SeriesFooterItemViewBinder.SeriesFooterViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_more, parent, false);
        return new SeriesFooterItemViewBinder.SeriesFooterViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull SeriesFooterItemViewBinder.SeriesFooterViewHolder holder, @NonNull final SeriesFooterItemViewBinder.SeriesFooter item) {
        final Context context = holder.tvMore.getContext();
        final int position = holder.getPosition();
        holder.tvMore.setText(item.getSeries());
        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeriesRActivity.class);
                CatePost catePost = new CatePost();
                catePost.setPlaces(item.getPlace());
                catePost.setStyles("0");
                catePost.setType(2);
                catePost.setYears("0");
                catePost.setHot_type("0");
                intent.putExtra("catepost", catePost);
                context.startActivity(intent);
            }
        });

        holder.change_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据局部刷新
                SeriesRangeRefresh seriesRangeRefresh = new SeriesRangeRefresh();
                seriesRangeRefresh.setId(item.getId());
                seriesRangeRefresh.setType(item.getType());
                seriesRangeRefresh.setPosition(position);
                seriesRangeRefresh.setLast_page(item.getLast_page());
                seriesRangeRefresh.setStyle_name(item.getStyle_name());
                seriesRangeRefresh.setPlace(item.getPlace());
                EventBus.getDefault().post(seriesRangeRefresh);
            }
        });
    }

    static class SeriesFooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.change_tv)
        TextView change_tv;

        public SeriesFooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SeriesFooter {

        private String region;
        private int last_page;
        private int id;
        private int type;
        private String place;
        private String style_name;

        public String getStyle_name() {
            return style_name;
        }

        public void setStyle_name(String style_name) {
            this.style_name = style_name;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getLast_page() {
            return last_page;
        }

        public int getId() {
            return id;
        }

        public int getType() {
            return type;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getSeries() {
            return region;
        }

        public void setSeries(String region) {
            this.region = region;
        }
    }
}
