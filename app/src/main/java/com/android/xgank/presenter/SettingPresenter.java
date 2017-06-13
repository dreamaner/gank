package com.android.xgank.presenter;

import android.content.Context;

import com.android.kit.utils.cache.CacheUtils;
import com.android.kit.utils.io.PackageUtils;
import com.android.mvp.mvp.XPresent;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.config.ThemeManage;
import com.android.xgank.ui.App;
import com.android.xgank.ui.activitys.SettingActivity;

/**
 * Created by yury on 2017/6/8.
 */

public class SettingPresenter extends XPresent<SettingActivity> {



    private boolean mSwitchSettingInitState;

    private int mTvImageQualityContentInitState;




    public void init() throws Exception {
        getV().showCacheSize(CacheUtils.getTotalCacheSize(getV().getBaseContext()));
        // 设置 View 界面的主题色
        getV().setSwitchCompatsColor(ThemeManage.INSTANCE.getColorPrimary());
        // 初始化开关显示状态
        getV().changeSwitchState(ConfigManage.INSTANCE.isListShowImg());
        getV().changeIsShowLauncherImgSwitchState(ConfigManage.INSTANCE.isShowLauncherImg());
        getV().changeIsAlwaysShowLauncherImgSwitchState(ConfigManage.INSTANCE.isProbabilityShowLauncherImg());

        setImageQualityChooseIsEnable(ConfigManage.INSTANCE.isListShowImg());
        setIsLauncherAlwaysShowImgEnable(ConfigManage.INSTANCE.isShowLauncherImg());

        getV().setAppVersionNameInTv(PackageUtils.getAppVersionName(App.getInstance()));
        setThumbnailQuality(ConfigManage.INSTANCE.getThumbnailQuality());

        mSwitchSettingInitState = ConfigManage.INSTANCE.isListShowImg();
        mTvImageQualityContentInitState = ConfigManage.INSTANCE.getThumbnailQuality();
    }


    public boolean isThumbnailSettingChanged() {
        return mSwitchSettingInitState != ConfigManage.INSTANCE.isListShowImg()
                || mTvImageQualityContentInitState > ConfigManage.INSTANCE.getThumbnailQuality();
    }




    public void saveIsListShowImg(boolean isListShowImg) {
        ConfigManage.INSTANCE.setListShowImg(isListShowImg);
        setImageQualityChooseIsEnable(isListShowImg);
    }


    public void saveIsLauncherShowImg(boolean isLauncherShowImg) {
        ConfigManage.INSTANCE.setShowLauncherImg(isLauncherShowImg);
        setIsLauncherAlwaysShowImgEnable(isLauncherShowImg);
        if (isLauncherShowImg) {
            getV().setShowLauncherTip("没有妹子太寂寞");
        } else {
            getV().setShowLauncherTip("基佬怎么会需要妹子");
        }
    }


    public void saveIsLauncherAlwaysShowImg(boolean isLauncherAlwaysShowImg) {
        ConfigManage.INSTANCE.setProbabilityShowLauncherImg(isLauncherAlwaysShowImg);
        if (isLauncherAlwaysShowImg) {
            getV().setAlwaysShowLauncherTip("偶尔来个惊喜就行");
        } else {
            getV().setAlwaysShowLauncherTip("我每次都要幸临，没毛病");
        }
    }

    private void setIsLauncherAlwaysShowImgEnable(boolean isEnable) {
        if (isEnable) {
            getV().setLauncherImgProbabilityEnable();
        } else {
            getV().setLauncherImgProbabilityUnEnable();
        }
    }

    private void setImageQualityChooseIsEnable(boolean isEnable) {
        if (isEnable) {
            getV().setImageQualityChooseEnable();
        } else {
            getV().setImageQualityChooseUnEnable();
        }
    }


    public int getColorPrimary() {
        return ThemeManage.INSTANCE.getColorPrimary();
    }


    public int getThumbnailQuality() {
        return ConfigManage.INSTANCE.getThumbnailQuality();
    }


    public void setThumbnailQuality(int quality) {
        ConfigManage.INSTANCE.setThumbnailQuality(quality);
        getV().setThumbnailQualityInfo(quality);
    }


    public void cleanCache(Context context) {

        try {
            getV().showSuccessTip("清理了"+CacheUtils.getTotalCacheSize(context)+"缓存");
            CacheUtils.clearAllCache(context);
            ConfigManage.INSTANCE.setBannerURL("");
            getV().showCacheSize(CacheUtils.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
