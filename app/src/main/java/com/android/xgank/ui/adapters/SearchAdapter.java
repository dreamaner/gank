package com.android.xgank.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.mvp.base.SimpleRecAdapter;
import com.android.mvp.imageloader.ILFactory;
import com.android.mvp.kit.KnifeKit;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.GankResults;
import com.android.xgank.bean.SearchResult;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.kit.ComUtil;

import butterknife.BindView;

/**
 * Created by 王小铭Style on 2017/6/13.
 */

public class SearchAdapter  extends SimpleRecAdapter<SearchResult.Item,SearchAdapter.ViewHolder>{
    public static final int TAG_VIEW = 0;

    public SearchAdapter(Context context) {
        super(context);
    }


    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {

        SearchResult.Item item = data.get(position);
        holder.ivItemImg.setImageResource(R.drawable.noimage);
        holder.tvItemTitle.setText(item.getDesc() == null?"unknow":item.getDesc());
        holder.tvItemPublisher.setText(item.getWho() == null?"unknow":item.getWho());
        holder.tvItemTime.setText(item.getPublishedAt() == null?"unknow": ComUtil.getDate(item.getPublishedAt()));
        holder.itemView.setOnClickListener(v -> {
            if (getRecItemClick() != null) {
                getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
            }
        });
    }

    @Override
    public SearchAdapter.ViewHolder newViewHolder(View itemView) {
        return new SearchAdapter.ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_home_normal;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_img)
        AppCompatImageView ivItemImg;
        @BindView(R.id.title)
        AppCompatTextView tvItemTitle;
        @BindView(R.id.publisher)
        AppCompatTextView tvItemPublisher;
        @BindView(R.id.time)
        AppCompatTextView tvItemTime;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
