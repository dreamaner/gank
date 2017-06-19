package com.android.xgank.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.kit.utils.check.FieldUtils;
import com.android.kit.utils.operate.RandomUtils;
import com.android.kit.utils.screen.ScreenUtils;
import com.android.mvp.base.SimpleRecAdapter;
import com.android.mvp.imageloader.ILFactory;
import com.android.mvp.kit.KnifeKit;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.GankResults;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.kit.ComUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Dreamaner on 2017/5/15.
 */

public class HomeAdapter extends SimpleRecAdapter<GankResults.Item, HomeAdapter.ViewHolder> {
    private SparseArray heightArray;

    public static final int TAG_VIEW = 0;

    public HomeAdapter(Context context) {
        super(context);
        heightArray = new SparseArray();

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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final GankResults.Item item = data.get(position);

        switch (item.getType()){

            case Constant.VIDEO:

                holder.ivPart.setVisibility(View.GONE);
                holder.llItem.setVisibility(View.VISIBLE);
                holder.ivItemImg.setImageResource(R.drawable.video_icon);

                break;
            case Constant.PHOTO:
                holder.ivPart.setVisibility(View.VISIBLE);

                holder.llItem.setVisibility(View.GONE);

                setPhotos(holder,item,holder.ivPart);
                break;
            default:
               if (ConfigManage.INSTANCE.isListShowImg()) { // 列表显示图片
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
            } else { // 列表不显示图片
                holder.ivItemImg.setVisibility(View.GONE);
            }
                break;
        }
         holder.tvItemTitle.setText(item.getDesc() == null?"unknow":item.getDesc());
         holder.tvItemPublisher.setText(item.getWho() == null?"unknow":item.getWho());
        try {
            holder.tvItemTime.setText(item.getPublishedAt() == null?"unknow":ComUtil.getDateByPublishTime(item.getPublishedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(v -> {
             if (getRecItemClick() != null) {
                 getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
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
    private void setPhotos(HomeAdapter.ViewHolder helper, GankResults.Item item, ImageView photoIv){

        int position=helper.getLayoutPosition();
        if (heightArray.get(position)==null){
            Glide.with(context)
                .load(item.getUrl())
                .into(new SimpleTarget() {
                    @Override
                    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
                        LinearLayout.LayoutParams layoutParams=
                            (LinearLayout.LayoutParams) photoIv.getLayoutParams();
                        int height=RandomUtils.getRandom(500,800);
                        layoutParams.height=height;
                        photoIv.setLayoutParams(layoutParams);
                        heightArray.put(position,height);
                    }

                });
        }else{
            int height= (int) heightArray.get(position);
            LinearLayout.LayoutParams layoutParams=
                (LinearLayout.LayoutParams) photoIv.getLayoutParams();
            layoutParams.height=height;
            photoIv.setLayoutParams(layoutParams);
        }
        ILFactory.getLoader().loadNet(photoIv,item.getUrl(),null);

    }


}
