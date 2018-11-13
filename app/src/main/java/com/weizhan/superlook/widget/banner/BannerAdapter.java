package com.weizhan.superlook.widget.banner;

import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class BannerAdapter<T, V extends View> extends PagerAdapter {

    public static int INVALID_ID = 0;

    protected List<T> data = new ArrayList<>();

    public BannerAdapter() {}

    public BannerAdapter(List<T> data, boolean isCirculate) {
        setData(data, isCirculate);
    }

    @Override
    public int getCount() {
        return data == null ? 0: data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int layoutId = getLayoutId();
        V itemView;
        TextView title;
        if (layoutId == INVALID_ID) {
            itemView = getItemView();
            title = getTitleView();
        } else {
            itemView = (V) LayoutInflater.from(container.getContext()).inflate(layoutId, null);
            title = new TextView(container.getContext());
        }
        if (itemView == null) {
            throw new RuntimeException("itemView can not be null,check getLayoutId or getItemView");
        }
        if (itemView == null) {
            throw new RuntimeException("itemView can not be null,check getLayoutId or getItemView");
        }
        bindData(itemView, title, data.get(position));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((V) object);
    }

    public void setData(List<T> data, boolean isCirculate) {
        this.data.clear();
        this.data.addAll(data);
        if (isCirculate) {
            T first = data.get(0);
            T last = data.get(data.size() - 1);
            this.data.add(data.size(), first);
            this.data.add(0, last);
        }
        notifyDataSetChanged();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract V getItemView();

    protected abstract TextView getTitleView();

    protected abstract void bindData(V itemView, TextView title, T item);

}
