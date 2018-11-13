package com.weizhan.superlook.ui.play;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.play.TestSeriesBean;
import com.weizhan.superlook.widget.adapter.SeriesAdapter;
import com.weizhan.superlook.widget.adapter.VarietyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/19.
 */

public class ChooseIndexItemViewBinder extends ItemViewBinder<TestSeriesBean, ChooseIndexItemViewBinder.ChooseIndexViewHolder> {

    @NonNull
    @Override
    protected ChooseIndexItemViewBinder.ChooseIndexViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.choose_item_index, parent, false);
        return new ChooseIndexItemViewBinder.ChooseIndexViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChooseIndexItemViewBinder.ChooseIndexViewHolder holder, @NonNull TestSeriesBean item) {
        final Context context = holder.seriesRecyclerView.getContext();
        GridLayoutManager layoutManager1 = new GridLayoutManager(context, 1);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.seriesRecyclerView.setLayoutManager(layoutManager1);
        holder.seriesRecyclerView.addItemDecoration(new SeriesItemDecoration());
        final SeriesAdapter seriesAdapter = new SeriesAdapter(item.getList(), context);
        holder.seriesRecyclerView.setAdapter(seriesAdapter);

        //监听单选
        seriesAdapter.setOnItemSingleSelectListener(new SeriesAdapter.OnItemSingleSelectListener() {

            @Override
            public void onSelected(int itemPosition, boolean isSelected) {
                Toast.makeText(context, "selectedPosition:" + itemPosition  +" == "+ seriesAdapter.getSingleSelectedPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class ChooseIndexViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.seriesRecyclerView)
        RecyclerView seriesRecyclerView;
        public ChooseIndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
