package com.android.xgank.bean;

import com.android.mvp.event.IBus;

/**
 * Author: yury
 * Created on: 2017/6/20 14:42
 * Description:主题切换时间总线
 */
public class ThemeEvent implements IBus.IEvent {
    @Override
    public int getTag() {
        return 1;
    }
}
