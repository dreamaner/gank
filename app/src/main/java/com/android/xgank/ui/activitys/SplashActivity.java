package com.android.xgank.ui.activitys;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;

import com.android.kit.view.immersion.ImmersionBar;
import com.android.mvp.mvp.XActivity;
import com.android.mvp.router.Router;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class SplashActivity extends XActivity {

    @BindView(R.id.img_launcher_welcome)
    AppCompatImageView mImageView;
    private boolean isResume;

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public void initData(Bundle bundle) {
        ImmersionBar.with(this).transparentBar().init();
        imgInit();
    }

    public void imgInit(){

        if (!ConfigManage.INSTANCE.isShowLauncherImg()) {
            goHomeActivity();
            return;
        }
        String imgCacheUrl = ConfigManage.INSTANCE.getBannerURL();
        if (!TextUtils.isEmpty(imgCacheUrl)) {
            loadImg(imgCacheUrl);
        } else {
            goHomeActivity();
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void loadImg(String url) {
        try {
            Picasso.with(this)
                    .load(url)
                    .into(mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isResume) {
                                        finish();
                                        return;
                                    }
                                    goHomeActivity();
                                }
                            }, 4000);
                        }

                        @Override
                        public void onError() {
                            goHomeActivity();
                        }
                    });
        } catch (Exception e) {
            goHomeActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
    }

    public void goHomeActivity() {
        Router.newIntent(this)
                .to(MainActivity.class)
                .anim(android.R.anim.fade_in, android.R.anim.fade_out)
                .launch();
        Router.pop(this);
    }

}
