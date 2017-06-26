package com.android.xgank.bean;

/**
 * Created by yury on 2017/6/5.
 */

public class Constant {

    //routerparms
    public static final String INTENT_FLAG = "flag";
    public static final String INTENT_FLAG_POSITION = "position";
    public static final String INTENT_FLAG_IDS = "ids";
    public static final String INTENT_FLAG_TYPE = "type";
    public static final String INTENT_FLAG_URLS = "urls";
    public static final String INTENT_FLAG_ITEM = "item";
    public static final String INTENT_FLAG_FAV = "fav";
    public static final String INTENT_FLAG_SEARCH = "search";

    //gank type
    public static final String ANDROID="Android";
    public static final String IOS="iOS";
    public static final String WEB="前端";
    public static final String PHOTO="福利";
    public static final String VIDEO="休息视频";
    public static final String EXPANDRES="拓展资源";
    public static final String RECOMMEND="瞎推荐";
    public static final String APP="App";

    //navigation
    public static final String HOME="home";
    public static final String MORE="more";
    public static final String ME="me";

    //toolbar title
    public static final String TOOLBAR_FAV_ACTIVITY = "我的收藏";
    public static final String TOOLBAR_PHOTO_ACTIVITY = "图片详情";
    public static final String TOOLBAR_SETTING_ACTIVITY = "设置";
    public static final String TOOLBAR_MORE_FRAGMENT = "更多";
    public static final String TOOLBAR_OWN_FRAGMENT = "我";

    //收藏操作
    public static final String FAV_SUCCESS = "收藏成功";
    public static final String FAV_DEFEAT = "收藏失败";
    public static final String UNFAV_SUCCESS = "取消收藏";
    public static final String UNFAV_DEFEAT = "取消收藏失败";

    //SwitchCompat选中tip
    public static final String SHOW_LANUCH_PHOTO_TIP = "萌萌的妹子真可耐";
    public static final String DISMISS_LANUCH_PHOTO_TIP = "可能是个假汉子";
    public static final String PROBABILITY_SHOW = "偶尔亮瞎一次眼O(∩_∩)O哈哈~";
    public static final String NOT_PROBABILITY_SHOW = "每次都要亮瞎眼(*^__^*) 嘻嘻……";

    //适配器类型
    public static final int CONTENT_IMG    = 1;
    public static final int CONTENT_NO_IMG = 2;
    public static final int CONTENT_VIDEO  = 3;
    public static final int CONTENT_NORMAL = 4;
}
