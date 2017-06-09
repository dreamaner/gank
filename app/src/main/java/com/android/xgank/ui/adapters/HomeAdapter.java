package com.android.xgank.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.kit.utils.check.FieldUtils;
import com.android.mvp.base.SimpleRecAdapter;
import com.android.mvp.imageloader.ILFactory;
import com.android.mvp.kit.KnifeKit;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.GankResults;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.kit.ComUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Dreamaner on 2017/5/15.
 */

public class HomeAdapter extends SimpleRecAdapter<GankResults.Item, HomeAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_home_normal;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final GankResults.Item item = data.get(position);

        switch (item.getType()){

            case Constant.VIDEO:

                holder.ivPart.setVisibility(View.GONE);
                holder.llItem.setVisibility(View.VISIBLE);
                holder.ivItemImg.setImageResource(R.drawable.video_icon);

                break;
            case Constant.PHOTO:
                holder.llItem.setVisibility(View.GONE);
                holder.ivPart.setVisibility(View.VISIBLE);
                ILFactory.getLoader().loadNet(holder.ivPart, item.getUrl(), null);
                break;
            default:
//               if (ConfigManage.INSTANCE.isListShowImg()) { // 列表显示图片
                holder.llItem.setVisibility(View.VISIBLE);
                holder.ivPart.setVisibility(View.GONE);
                String quality = "";
                if (item.getImages() != null && item.getImages().size() > 0) {
                    switch (ConfigManage.INSTANCE.getThumbnailQuality()) {
                        case 0: // 原图
                            quality = "?imageView2/0/w/400";
                            break;
                        case 1: // 默认
                            quality = "?imageView2/0/w/280";
                            break;
                        case 2: // 省流
                            quality = "?imageView2/0/w/190";
                            break;
                    }
//                    Picasso.with(mContext).setIndicatorsEnabled(true);//显示指示器
                    ILFactory.getLoader().loadNet(holder.ivItemImg,item.getImages().get(0)+quality,null);

                } else { // 图片 URL 不存在
//                    holder.ivItemImg.setVisibility(View.GONE);
                    ILFactory.getLoader().loadResource(holder.ivItemImg,R.drawable.noimage,null);
                }
//            } else { // 列表不显示图片
//                holder.ivItemImg.setVisibility(View.GONE);
//            }
                break;
        }
         holder.tvItemTitle.setText(item.getDesc() == null?"unknow":item.getDesc());
         holder.tvItemPublisher.setText(item.getWho() == null?"unknow":item.getWho());
         holder.tvItemTime.setText(item.getPublishedAt() == null?"unknow":ComUtil.getDate(item.getPublishedAt()));
         holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_part)
        ImageView ivPart;
        @BindView(R.id.iv_item_img)
        AppCompatImageView ivItemImg;
        @BindView(R.id.title)
        AppCompatTextView tvItemTitle;
        @BindView(R.id.publisher)
        AppCompatTextView tvItemPublisher;
        @BindView(R.id.time)
        AppCompatTextView tvItemTime;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}