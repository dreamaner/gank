package com.android.xgank.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.kit.utils.toast.Toasty;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.bean.Favorite;
import com.android.xgank.bean.GankResults;
import com.android.xgank.bean.SearchResult;
import com.android.xgank.presenter.WebPresenter;
import com.android.xgank.ui.adapters.PhotoAdapter;
import java.util.ArrayList;

public class PhotoActivity extends XActivity<WebPresenter> {

    @BindView(R.id.photo_vp)
    ViewPager photoVp;
    @BindView(R.id.gank_photo_pos_tv)
    TextView gankPhotoPosTv;
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.fab_web_favorite)
    FloatingActionButton mFloatingActionButton;
    private ArrayList<String> mDatas;
    private int curPos;
    private PhotoAdapter photoAdapter;
    public boolean isForResult;// 是否回传结果
    public GankResults.Item item;
    public Favorite fav;
    public SearchResult.Item search;

    public int flag ;
    @Override
    public void initData(Bundle savedInstanceState) {
        setUpToolBar(true, toolbar, "图片详情");
        initIntentData();
        initView();
        //getP().init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    public WebPresenter newP() {
        return new WebPresenter();
    }

    private void initIntentData() {
        Intent intent = getIntent();
        mDatas = intent.getStringArrayListExtra("urls");
        curPos = intent.getIntExtra("position", 0);
    }

    private void initView() {
        photoAdapter = new PhotoAdapter(this, mDatas);
        photoVp.setAdapter(photoAdapter);
        photoVp.setCurrentItem(curPos);
        gankPhotoPosTv.setText(String.valueOf(curPos + 1) + "/" + String.valueOf(mDatas.size()));
        photoVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                gankPhotoPosTv.setText(
                    String.valueOf(position + 1) + "/" + String.valueOf(mDatas.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public Favorite getFavorite() {
        if (flag == 1) {
            fav = new Favorite();

            fav.setGank_id(item.get_id());
            fav.setImages(item.getImages());
            fav.setCreatedAt(item.getCreatedAt());
            fav.setDesc(item.getDesc());
            fav.setPublishedAt(item.getPublishedAt());
            fav.setSource(item.getSource());
            fav.setType(item.getType());
            fav.setUrl(item.getUrl());
            fav.setUsed(item.getUsed());
            fav.setWho(item.getWho());
        } else if (flag == 3) {
            fav = new Favorite();

            fav.setGank_id(search.getGanhuo_id());
            fav.setWho(search.getWho());
            fav.setUrl(search.getUrl());
            fav.setType(search.getType());
            fav.setDesc(search.getDesc());
            fav.setCreatedAt(search.getPublishedAt());
            fav.setPublishedAt(search.getPublishedAt());
        }

        return fav;
    }
    /**
     *
     * @return
     */
    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_photo;
    }

    public void downLoadImg() {

    }

    public void shareTo() {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareTo();
                break;
            case R.id.action_down:
                downLoadImg();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @OnClick(R.id.fab_web_favorite)
    public void favorite() {
        getP().favoriteGank();
    }

    public void setFavoriteState(boolean isFavorite) {
        if (isFavorite) {
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite);
        } else {
            mFloatingActionButton.setImageResource(R.drawable.ic_unfavorite);
        }
        isForResult = !isFavorite;
    }

    public void showTips(String msg) {

        switch (msg) {
            case "收藏成功":
                Toasty.success(this, msg).show();
                break;
            case "收藏失败":
                Toasty.error(this, msg).show();
                break;
            case "取消收藏":
                Toasty.info(this, msg).show();
                break;
            case "取消收藏失败":
                Toasty.error(this, msg).show();
                break;
        }
    }
}
