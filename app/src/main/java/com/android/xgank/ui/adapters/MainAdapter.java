package com.android.xgank.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.kit.utils.operate.RandomUtils;
import com.android.mvp.imageloader.ILFactory;
import com.android.mvp.imageloader.ILoader;
import com.android.mvp.recycleview.RecyclerAdapter;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.GankResults;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.kit.ComUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import java.text.ParseException;

/**
 * Author: yury
 * Created on: 2017/6/26 13:31
 * Description:多类型适配器
 */
public class MainAdapter extends RecyclerAdapter<GankResults.Item, RecyclerView.ViewHolder> {
    public static final int TAG_VIEW = 0;
    public String quality = "";
    private SparseArray heightArray;

    public MainAdapter(Context context) {
        super(context);
        heightArray = new SparseArray();
    }

    @Override
    public int getItemViewType(int position) {
        GankResults.Item item = data.get(position);
        if (item.getType().equals(Constant.PHOTO)) {
            return Constant.CONTENT_IMG;
        }else if (item.getType().equals(Constant.VIDEO)){
            return Constant.CONTENT_VIDEO;
        }else if (item.getImages() != null&&
             item.getImages().size() > 0&&
            !item.getType().equals(Constant.PHOTO)&&
            !item.getType().equals(Constant.VIDEO)){
            return Constant.CONTENT_NORMAL;
        }else {
            return Constant.CONTENT_NO_IMG;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.CONTENT_IMG) {
            View imgView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.content_img, null);
            return new ImgViewHolder(imgView);
        }else if (viewType == Constant.CONTENT_NO_IMG) {
            View textView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.content_no_img, null);
            return new NoImgViewHolder(textView);
        }else if (viewType == Constant.CONTENT_VIDEO) {
            View videoView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.content_video, null);
            return new VideoViewHolder(videoView);
        }else if (viewType == Constant.CONTENT_NORMAL){
            View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.content_normal, null);
            return new NormalViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GankResults.Item item = data.get(position);
        if (holder instanceof ImgViewHolder) {
            ImgViewHolder imgViewHolder = (ImgViewHolder) holder;
            imgViewHolder.girl.setVisibility(View.VISIBLE);
            if (isShowThumbnailQuality())
               setPhotos(imgViewHolder,item,imgViewHolder.girl);
            else
                imgViewHolder.girl.setVisibility(View.GONE);
        }
        if (holder instanceof NoImgViewHolder) {
            NoImgViewHolder noImgViewHolder = (NoImgViewHolder) holder;

            noImgViewHolder.author.setText(item.getWho() == null ? "unknow" : item.getWho());

            noImgViewHolder.title.setText(item.getDesc() == null ? "unknow" : item.getDesc());
            try {
                noImgViewHolder.time.setText(item.getPublishedAt() == null ? "unknow" : ComUtil.getDateByPublishTime(item.getPublishedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (holder instanceof  NormalViewHolder) {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;

            normalViewHolder.headerImg.setVisibility(View.VISIBLE);
            normalViewHolder.author.setText(item.getWho() == null ? "unknow" : item.getWho());
            normalViewHolder.title.setText(item.getDesc() == null ? "unknow" : item.getDesc());
            if (isShowThumbnailQuality())
                ILFactory.getLoader().loadNet(normalViewHolder.headerImg, item.getImages().get(0)+getQuality(), new ILoader.Options(R.drawable.loading_img,R.drawable.noimage));
            else
                normalViewHolder.headerImg.setVisibility(View.GONE);
            try {
                normalViewHolder.time.setText(item.getPublishedAt() == null ? "unknow" : ComUtil.getDateByPublishTime(item.getPublishedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (holder instanceof  VideoViewHolder){

                VideoViewHolder videoViewHolder = (VideoViewHolder)holder;

                videoViewHolder.headerImg.setVisibility(View.VISIBLE);

                videoViewHolder.author.setText(item.getWho() == null?"unknow":item.getWho());

                videoViewHolder.title.setText(item.getDesc() == null?"unknow":item.getDesc());
                if (!isShowThumbnailQuality())
                    videoViewHolder.headerImg.setVisibility(View.GONE);
                try {
                    videoViewHolder.time.setText(item.getPublishedAt() == null?"unknow":ComUtil.getDateByPublishTime(item.getPublishedAt()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

        }
        holder.itemView.setOnClickListener(v -> {
            if (getRecItemClick() != null) {
                getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
            }
        });
    }
    public String getQuality(){
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
        return quality;
    }
    public boolean isShowThumbnailQuality(){
        if (ConfigManage.INSTANCE.isListShowImg())
            return true;
        else
            return false;
    }
    public class ImgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.girl)
        ImageView girl;

        ImgViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class NoImgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.author)
        AppCompatTextView author;
        @BindView(R.id.time)
        AppCompatTextView time;

        NoImgViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header_img)
        AppCompatImageView headerImg;
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.author)
        AppCompatTextView author;
        @BindView(R.id.time)
        AppCompatTextView time;

        VideoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.header_img)
        AppCompatImageView headerImg;
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.author)
        AppCompatTextView author;
        @BindView(R.id.time)
        AppCompatTextView time;

        NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setPhotos(RecyclerView.ViewHolder helper, GankResults.Item item, ImageView photoIv){

        int position=helper.getLayoutPosition();
        if (heightArray.get(position)==null){
            Glide.with(context)
                .load(item.getUrl()+getQuality())
                .into(new SimpleTarget() {
                    @Override
                    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
                        LinearLayout.LayoutParams layoutParams=
                            (LinearLayout.LayoutParams) photoIv.getLayoutParams();
                        int height= RandomUtils.getRandom(500,800);
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
        ILFactory.getLoader().loadNet(photoIv,item.getUrl()+getQuality(),new ILoader.Options(R.drawable.loading_img,R.drawable.noimage));

    }
}
