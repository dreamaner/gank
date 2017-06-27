package com.android.xgank.presenter;

import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.android.mvp.mvp.XPresent;
import com.android.mvp.net.ApiSubscriber;
import com.android.mvp.net.NetError;
import com.android.mvp.net.XApi;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.config.ThemeManage;
import com.android.xgank.bean.GankResults;
import com.android.xgank.net.Api;
import com.android.xgank.ui.fragments.HomeFragment;

import io.reactivex.Flowable;


/**
 * Created by yury on 2017/6/5.
 */

public class HomePresenter extends XPresent<HomeFragment> {

    protected static final int PAGE_SIZE = 10;

    public void loadData(String type, final int page) {
        Api.getGankService().getGankData(type, PAGE_SIZE, page)
                .compose(XApi.<GankResults>getApiTransformer())
                .compose(XApi.<GankResults>getScheduler())
                .compose(getV().<GankResults>bindToLifecycle())
                .subscribe(new ApiSubscriber<GankResults>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                    @Override
                    public void onNext(GankResults gankResults) {
                        getV().showData(page, gankResults);
                    }
                });
    }

    public void setThemeColor(@Nullable Palette palette) {
        if (palette != null) {
            int colorPrimary = ThemeManage.INSTANCE.getColorPrimary();
            // 把从调色板上获取的主题色保存在内存中
            ThemeManage.INSTANCE.setColorPrimary(palette.getDarkVibrantColor(colorPrimary));
            // 设置 AppBarLayout 的背景色
            getV().setAppBarLayoutColor(colorPrimary);
            // 设置 FabButton 的背景色
            getV().setFabButtonColor(colorPrimary);
            // 停止 FabButton 加载中动画
            getV().enableFabButton();
            getV().stopBannerLoadingAnim();
        }
    }

    public void cacheRandomImg() {
        if (!ConfigManage.INSTANCE.isShowLauncherImg()) { // 不显示欢迎妹子，也就不需要预加载了
            return;
        }
        if (ConfigManage.INSTANCE.isProbabilityShowLauncherImg()) { // 概率出现欢迎妹子
            if (Math.random() < 0.75) {
                ConfigManage.INSTANCE.setBannerURL("");
                return;
            }
        }
        Api.getGankService().getRandomBeauties(1)
                .compose(XApi.<GankResults>getApiTransformer())
                .compose(XApi.<GankResults>getScheduler())
                .compose(getV().<GankResults>bindToLifecycle())
                .subscribe(new ApiSubscriber<GankResults>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(GankResults meiziResult) {
                        if (meiziResult != null && meiziResult.getResults() != null
                                && meiziResult.getResults().size() > 0
                                && meiziResult.getResults().get(0).getUrl() != null) {
                            getV().cacheImg(meiziResult.getResults().get(0).getUrl());
                        }
                    }
                });
    }



    public void saveCacheImgUrl(String url) {
        ConfigManage.INSTANCE.setBannerURL(url);
    }

    /**
     * 或单张 Banner
     *
     * @param isRandom true：随机  false：获取最新
     */

    public void getBanner(final boolean isRandom) {
        getV().startBannerLoadingAnim();
        getV().disEnableFabButton();
        Flowable<GankResults> observable;
        if (isRandom) {
            observable = Api.getGankService().getRandomBeauties(1);
        } else {
            observable = Api.getGankService().getCategoryDate("福利", 1, 1);
        }
        observable.compose(XApi.<GankResults>getApiTransformer())
                .compose(XApi.<GankResults>getScheduler())
                .compose(getV().<GankResults>bindToLifecycle())
                .subscribe(new ApiSubscriber<GankResults>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showBannerFail("Banner 图加载失败。");
                        getV().enableFabButton();
                        getV().stopBannerLoadingAnim();
                    }

                    @Override
                    public void onNext(GankResults meiziResult) {
                        if (meiziResult != null
                                && meiziResult.getResults() != null
                                && meiziResult.getResults().size() > 0
                                && meiziResult.getResults().get(0).getUrl() != null) {
                            getV().setBanner(meiziResult.getResults().get(0).getUrl());
                            ConfigManage.INSTANCE.setPhotoHead(meiziResult.getResults().get(0).getUrl());
                        } else {
                            getV().showBannerFail("Banner 图加载失败。");
                        }
                    }
                });
    }
}
