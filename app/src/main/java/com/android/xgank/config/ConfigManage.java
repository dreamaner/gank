package com.android.xgank.config;

import android.content.Context;

import com.android.kit.utils.system.AppUtils;
import com.android.mvp.cache.PrefsUtils;

/**
 * ConfigManage
 * Created by dreamaner on 2017/5/15 15:03.
 */
public enum ConfigManage {
    INSTANCE;
    private final String spName = "app_config";
    private final String key_isListShowImg = "isListShowImg";
    private final String key_thumbnailQuality = "thumbnailQuality";
    private final String key_banner_url = "keyBannerUrl";
    private final String key_launcher_img_show = "keyLauncherImgShow";
    private final String key_launcher_img_probability_show = "keyLauncherImgProbabilityShow";

    private boolean isListShowImg;
    private int thumbnailQuality;
    private String bannerURL;
    private boolean isShowLauncherImg = true;
    private boolean isProbabilityShowLauncherImg;
    private boolean isFirstInstanceApp ;
    private String  photoHead ;
    public void initConfig(Context context) {
        PrefsUtils prefsUtils =  PrefsUtils.getInstance(context,spName);
        // 列表是否显示图片
        isListShowImg = prefsUtils.getBoolean(key_isListShowImg, false);
        // 缩略图质量 0：原图 1：默认 2：省流
        thumbnailQuality = prefsUtils.getInt(key_thumbnailQuality, 1);
        // Banner URL 用于加载页显示
        bannerURL = prefsUtils.getString(key_banner_url, "");
        // 启动页是否显示妹子图
        isShowLauncherImg = prefsUtils.getBoolean(key_launcher_img_show, true);
        // 启动页是否概率出现
        isProbabilityShowLauncherImg = prefsUtils.getBoolean(key_launcher_img_probability_show, true);
    }
    public void setPhotoHead(String imgUrl){
        PrefsUtils prefsUtils = PrefsUtils.getInstance(AppUtils.getContext(),spName);
        if (!prefsUtils.getString("photoHead","").equals(imgUrl))
            prefsUtils.putString("photoHead",imgUrl);
    }

    public String getPhotoHead(){
        PrefsUtils prefsUtils = PrefsUtils.getInstance(AppUtils.getContext(),spName);
        return prefsUtils.getString("photoHead",null);
    }
    public boolean isFirstComeInApp(){

        PrefsUtils prefsUtils = PrefsUtils.getInstance(AppUtils.getContext(),spName);
        isFirstInstanceApp = prefsUtils.getBoolean("isFirsrt",true);
        prefsUtils.putBoolean("isFirst",false);
        return isFirstInstanceApp;

    }
    public boolean isListShowImg() {
        return isListShowImg;
    }

    public void setListShowImg(boolean listShowImg) {

        PrefsUtils prefsUtils =  PrefsUtils.getInstance(AppUtils.getContext(),spName);

        prefsUtils.putBoolean(key_isListShowImg, listShowImg);

        isListShowImg = listShowImg;

    }

    public int getThumbnailQuality() {
        return thumbnailQuality;
    }

    public void setThumbnailQuality(int thumbnailQuality) {

        PrefsUtils prefsUtils =  PrefsUtils.getInstance(AppUtils.getContext(),spName);

        prefsUtils.putInt(key_thumbnailQuality, thumbnailQuality);

        this.thumbnailQuality = thumbnailQuality;

    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {

        PrefsUtils prefsUtils =  PrefsUtils.getInstance(AppUtils.getContext(),spName);

        prefsUtils.putString(key_banner_url, bannerURL);

        this.bannerURL = bannerURL;

    }

    public boolean isShowLauncherImg() {

        return isShowLauncherImg;
    }

    public void setShowLauncherImg(boolean showLauncherImg) {

        PrefsUtils prefsUtils =  PrefsUtils.getInstance(AppUtils.getContext(),spName);

        prefsUtils.putBoolean(key_launcher_img_show, showLauncherImg);

        isShowLauncherImg = showLauncherImg;

    }

    public boolean isProbabilityShowLauncherImg() {
        return isProbabilityShowLauncherImg;
    }

    public void setProbabilityShowLauncherImg(boolean probabilityShowLauncherImg) {

        PrefsUtils prefsUtils =  PrefsUtils.getInstance(AppUtils.getContext(),spName);

        prefsUtils.putBoolean(key_launcher_img_probability_show, probabilityShowLauncherImg);

        isProbabilityShowLauncherImg = probabilityShowLauncherImg;

    }
}
