package com.android.xgank.ui.activitys;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.kit.utils.other.IntentUtils;
import com.android.kit.utils.system.AndroidUtils;
import com.android.kit.utils.toast.SnackbarUtils;
import com.android.kit.utils.toast.ToastUtils;
import com.android.kit.utils.toast.Toasty;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.log.XLog;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.bean.Favorite;
import com.android.xgank.bean.GankResults;
import com.android.xgank.bean.SearchResult;
import com.android.xgank.presenter.PhotoPresenter;
import com.android.xgank.presenter.WebPresenter;
import com.android.xgank.ui.adapters.PhotoAdapter;
import com.android.xgank.ui.fragments.HomeFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoActivity extends XActivity<PhotoPresenter> {

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
    public ArrayList<String> ids;
    public int flag ;
    public String ID;
    @Override
    public void initData(Bundle savedInstanceState) {
        setUpToolBar(true, toolbar, "图片详情");

        initIntentData();
        initView();
        getP().init();
        getP().showFavState();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    public PhotoPresenter newP() {
        return new PhotoPresenter();
    }

    private void initIntentData() {
        Intent intent = getIntent();
        mDatas = intent.getStringArrayListExtra("urls");
        ids = intent.getStringArrayListExtra("ids");
        curPos = intent.getIntExtra("position", 0);
        flag = intent.getIntExtra("flag",0);

    }
    public void hideFavoriteFab() {
        mFloatingActionButton.setVisibility(View.GONE);

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
                XLog.i("---",photoVp.getCurrentItem()+"");
                XLog.i("---",mDatas.size()+"");
                getP().init();
                gankPhotoPosTv.setText(
                    String.valueOf(position + 1) + "/" + String.valueOf(mDatas.size()));
                getP().showFavState();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public Favorite getFavorite() {
        curPos = photoVp.getCurrentItem();
        ID = ids.get(photoVp.getCurrentItem());
        if (flag == 1) {
            item = (GankResults.Item) getIntent().getSerializableExtra("item");

            fav = new Favorite();

            fav.setType(item.getType());

            fav.setGank_id(ids.get(curPos));

            fav.setUrl(mDatas.get(curPos));

        } else if (flag == 3) {
            search = (SearchResult.Item) getIntent().getSerializableExtra("search");

            fav = new Favorite();

            fav.setType(search.getType());

            fav.setGank_id(ids.get(curPos));

            fav.setUrl(mDatas.get(curPos));

        }else {
            fav = (Favorite) getIntent().getSerializableExtra("fav");

            fav.setType(fav.getType());

            fav.setGank_id(ids.get(curPos));

            fav.setUrl(mDatas.get(curPos));
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
        Glide.with(this).load(mDatas.get(photoVp.getCurrentItem())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                photoSave(resource,ID);
            }
        });
    }
    private void photoSave(Bitmap  bitmap, String ids){
        File file =new File(Environment.getExternalStorageDirectory(),"XGank");
        if (!file.exists())
            file.mkdirs();
        File image = new File(file.getPath(),ids+".jpg");
        try{
            if (!image.exists()){
                image.createNewFile();
            }else{
                Toast.makeText(this,"该图片已经保存",Toast.LENGTH_SHORT).show();
                return;
            }
            FileOutputStream out  = new  FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            Toast.makeText(this,"成功保存到："+image.getPath(),Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
        }
    }
    public void shareTo() {

        Glide.with(this).load(mDatas.get(photoVp.getCurrentItem())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource==null||createUri(resource)==null){
                    Toast.makeText(PhotoActivity.this,"创建分享失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = createUri(resource);
                Intent intent = IntentUtils.getShareImageIntent("",uri);
                startActivity(intent);

            }
        });
    }
    private Uri createUri(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), "XGank");
        if (!file.exists()) file.mkdirs();
        File image = new File(file.getPath(), "share.jpg");
        try {
            if (!image.exists()) {
                image.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            String filePath = "file:" + image.getAbsolutePath();
            return Uri.parse(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shareImg:
                 shareTo();
                 break;
            case R.id.action_down:
                 downLoadImg();
                 break;
            case android.R.id.home:
                 onBackPressed();
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
