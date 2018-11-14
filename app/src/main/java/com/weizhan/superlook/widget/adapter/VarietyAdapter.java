package com.weizhan.superlook.widget.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weizhan.superlook.R;
import com.weizhan.superlook.model.bean.play.TestSeriesBean;

/**
 * Created by Administrator on 2018/9/18.
 */

public class VarietyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private VarietyAdapter.OnItemClickListener onItemClickListener;
    private VarietyAdapter.OnItemSingleSelectListener onItemSingleSelectListener;

    private TestSeriesBean testChooseBean = new TestSeriesBean();
    private Context mContext;
    public void setOnItemClickListener(VarietyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(TestSeriesBean newList) {
        if (newList != null && newList.getList().size() > 0)
        this.testChooseBean = newList;
        notifyDataSetChanged();
    }

    public VarietyAdapter(TestSeriesBean list, Context context) {
        this.testChooseBean = list;
        this.mContext = context;
    }

    public void setOnItemSingleSelectListener(VarietyAdapter.OnItemSingleSelectListener onItemSingleSelectListener) {
        this.onItemSingleSelectListener = onItemSingleSelectListener;
    }

    private int singleSelected = 0; // 默认为第一个被选中

    @Override
    public VarietyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_variety_number, parent, false);
        return new VarietyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((VarietyAdapter.MyViewHolder)holder).textView.setTag(position);//绑定
        ((VarietyAdapter.MyViewHolder)holder).textView.setText(testChooseBean.getList().get(position).getContent());
        ((MyViewHolder)holder).time.setText(testChooseBean.getList().get(position).getTimes());
        ((MyViewHolder) holder).ll_item.setTag(position);
        ((MyViewHolder) holder).ll_item.setOnClickListener(this);
        if (singleSelected == position) {
            ((MyViewHolder) holder).ll_item.setSelected(true);
            ((VarietyAdapter.MyViewHolder)holder).textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ((VarietyAdapter.MyViewHolder)holder).textView.setTextColor(Color.parseColor("#ffffff"));
            ((MyViewHolder)holder).time.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ((MyViewHolder)holder).time.setTextColor(Color.parseColor("#ffffff"));
            ((MyViewHolder)holder).ll_item.setBackgroundResource(R.drawable.bg_hotsearch);
        } else {
            ((MyViewHolder) holder).ll_item.setSelected(false);
            ((VarietyAdapter.MyViewHolder)holder).textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ((VarietyAdapter.MyViewHolder)holder).textView.setTextColor(Color.parseColor("#000000"));
            ((MyViewHolder)holder).time.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ((MyViewHolder)holder).time.setTextColor(Color.parseColor("#000000"));
            ((MyViewHolder)holder).ll_item.setBackgroundResource(R.drawable.bg_white_corner);
        }
    }

    @Override
    public int getItemCount() {
        return testChooseBean.getList().size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = (int) v.getTag();
        if (onItemSingleSelectListener != null) {
            if (singleSelected == itemPosition) {
                onItemSingleSelectListener.onSelected(itemPosition, false);
            } else {
                singleSelected = itemPosition;
                onItemSingleSelectListener.onSelected(itemPosition, true);
            }
        }
        notifyDataSetChanged();//通知刷新
    }

    //=========API=========

    /**
     * 设置默认选中项
     *
     * @param itemPositions
     */

    public void setSelected(int... itemPositions) {
        singleSelected = itemPositions[0];
        if (onItemSingleSelectListener != null) {
            onItemSingleSelectListener.onSelected(singleSelected, true);
        }
        notifyDataSetChanged();
    }


    /**
     * 获取单选模式选中Item位置
     *
     * @return
     */
    public int getSingleSelected() {
        return singleSelected;
    }

    /**
     * 获取单选项位置
     */
    public int getSingleSelectedPosition() {
        return singleSelected;
    }

    /**
     * 判断某个item位置是否被选中
     *
     * @param position
     * @return
     */
    public boolean isSelected(int position) {
        return position == singleSelected;
    }

    /**
     * 点选模式监听接口
     */
    public interface OnItemClickListener {
        /**
         * 点选模式下，点击item时回调
         *
         * @param itemPosition 点击的item位置
         */
        void onClicked(int itemPosition);
    }

    /**
     * 单选模式监听接口
     */
    public interface OnItemSingleSelectListener {
        /**
         * 单选模式下，点击Item选中时回调
         *
         * @param itemPosition 点击的item位置
         * @param isSelected   是否选中
         */
        void onSelected(int itemPosition, boolean isSelected);

    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView time;
        private LinearLayout ll_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_item);
            time = (TextView) itemView.findViewById(R.id.txt_time);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
