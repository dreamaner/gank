package com.android.xgank.config;

/**
 * ThemeManage
 * 主题色管理类
 * Created by dreamaner on 2017/5/15 15:03.
 */
public enum ThemeManage {
    INSTANCE;

    private int colorPrimary;

    public void initColorPrimary(int colorPrimary) {
        setColorPrimary(colorPrimary);
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }


}
