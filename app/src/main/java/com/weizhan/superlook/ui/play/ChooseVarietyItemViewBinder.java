package com.weizhan.superlook.ui.play;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.play.TestSeriesBean;
import com.weizhan.superlook.model.event.PartNumBean;
import com.weizhan.superlook.widget.adapter.VarietyAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/19.
 */

public class ChooseVarietyItemViewBinder extends ItemViewBinder<TestSeriesBean, ChooseVarietyItemViewBinder.VarietyItemViewHolder> {

    @NonNull
    @Override
    protected ChooseVarietyItemViewBinder.VarietyItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.choose_item_variety, parent, false);
        return new ChooseVarietyItemViewBinder.VarietyItemViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChooseVarietyItemViewBinder.VarietyItemViewHolder holder, @NonNull final TestSeriesBean item) {
        final Context context = holder.varietyRecyclerView.getContext();
        GridLayoutManager layoutManager1 = new GridLayoutManager(context, 1);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.varietyRecyclerView.setLayoutManager(layoutManager1);
        holder.varietyRecyclerView.addItemDecoration(new SeriesItemDecoration());
        final VarietyAdapter varietyAdapter = new VarietyAdapter(item, context);
        holder.varietyRecyclerView.setAdapter(varietyAdapter);

        //监听单选
        varietyAdapter.setOnItemSingleSelectListener(new VarietyAdapter.OnItemSingleSelectListener() {

            @Override
            public void onSelected(int itemPosition, boolean isSelected) {
                if (isSelected) {
                    PartNumBean partNumBean = new PartNumBean();
                    partNumBean.setUrl(item.getList().get(itemPosition).getLinkurl());
                    partNumBean.setTitle(item.getList().get(itemPosition).getContent());
                    EventBus.getDefault().post(partNumBean);
                }
            }
        });
    }

    static class VarietyItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.varietyRecyclerView)
        RecyclerView varietyRecyclerView;
        public VarietyItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
