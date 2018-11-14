package com.weizhan.superlook.ui.region.series;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.region.RecyclerTitleBean;
import com.weizhan.superlook.widget.adapter.EasyAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/9/29.
 */

public class TitleRecyItemViewBinder extends ItemViewBinder<RecyclerTitleBean, TitleRecyItemViewBinder.TitleRecyViewHolder> {

    private EasyAdapter easyAdapter1;
    private EasyAdapter easyAdapter2;
    private EasyAdapter easyAdapter3;
    private TitleChooseListner titleChooseListner;

    public void setTitleChooseListner(TitleChooseListner titleChooseListner) {
        this.titleChooseListner = titleChooseListner;
    }

    private int position1 = 0;
    private int position2 = 0;
    private int position3 = 0;

    @NonNull
    @Override
    protected TitleRecyItemViewBinder.TitleRecyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_title_recyclerview, parent, false);
        return new TitleRecyItemViewBinder.TitleRecyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final TitleRecyItemViewBinder.TitleRecyViewHolder holder, @NonNull final RecyclerTitleBean item) {
        final Context context = holder.recyclerView1.getContext();
        easyAdapter1 = new EasyAdapter(item.getRecDataList1(), context);
        easyAdapter2 = new EasyAdapter(item.getRecDataList2(), context);
        easyAdapter1 = new EasyAdapter(item.getRecDataList1(), context);
        easyAdapter2 = new EasyAdapter(item.getRecDataList2(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); //把列表设置成水平滚动
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL); //把列表设置成水平滚动
        holder.recyclerView1.setLayoutManager(linearLayoutManager);
        holder.recyclerView2.setLayoutManager(linearLayoutManager2);
        holder.recyclerView1.setAdapter(easyAdapter1);
        holder.recyclerView2.setAdapter(easyAdapter2);
        easyAdapter1.setSelected(item.getPosition1());
        easyAdapter2.setSelected(item.getPosition2());
        if (item.getType() != 1) {
            holder.recyclerView3.setVisibility(View.GONE);
        } else {
            easyAdapter3 = new EasyAdapter(item.getRecDataList3(), context);
            easyAdapter3 = new EasyAdapter(item.getRecDataList3(), context);
            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
            linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL); //把列表设置成水平滚动
            holder.recyclerView3.setLayoutManager(linearLayoutManager3);
            holder.recyclerView3.setAdapter(easyAdapter3);
            easyAdapter3.setSelected(item.getPosition3());
            //监听单选
            easyAdapter3.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {

                @Override
                public void onSelected(int itemPosition, boolean isSelected) {
//                Toast.makeText(context, "selectedPosition:" + itemPosition  +" == "+ easyAdapter3.getSingleSelectedPosition(), Toast.LENGTH_SHORT).show();
                    titleChooseListner.onRecycler3Choose(itemPosition, isSelected, item.getRecDataList3().get(itemPosition));
                    position3 = itemPosition;
                }
            });
        }

        //监听单选
        easyAdapter1.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {

            @Override
            public void onSelected(int itemPosition, boolean isSelected) {
//                Toast.makeText(context, "selectedPosition:" + itemPosition  +" == "+ easyAdapter1.getSingleSelectedPosition(), Toast.LENGTH_SHORT).show();
                titleChooseListner.onRecycler1Choose(itemPosition, isSelected);
                position1 = itemPosition;
            }
        });

        //监听单选
        easyAdapter2.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {

            @Override
            public void onSelected(int itemPosition, boolean isSelected) {
//                Toast.makeText(context, "selectedPosition:" + itemPosition  +" == "+ easyAdapter2.getSingleSelectedPosition(), Toast.LENGTH_SHORT).show();
                titleChooseListner.onRecycler2Choose(itemPosition, isSelected, item.getRecDataList2().get(itemPosition));
                position2 = itemPosition;
            }
        });
    }

    static class TitleRecyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyclerView1)
        RecyclerView recyclerView1;
        @BindView(R.id.recyclerView2)
        RecyclerView recyclerView2;
        @BindView(R.id.recyclerView3)
        RecyclerView recyclerView3;
        public TitleRecyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface TitleChooseListner {
        void onRecycler1Choose(int itemPosition, boolean isSelected);
        void onRecycler2Choose(int itemPosition, boolean isSelected, String place);
        void onRecycler3Choose(int itemPosition, boolean isSelected, String style);
    }

    public void setPosition3(int position3) {
        this.position3 = position3;
        easyAdapter3.notifyDataSetChanged();
    }
}
