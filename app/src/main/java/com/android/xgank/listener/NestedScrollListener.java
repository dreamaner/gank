package com.android.xgank.listener;

import android.support.v4.widget.NestedScrollView;

/**
 * ConfigManage
 * Created by dreamaner on 2017/5/15 15:03.
 */

public abstract class NestedScrollListener implements NestedScrollView.OnScrollChangeListener {

    private static final int SCROLL_DISTANCE=35;
    private int totalScrollDistance;


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int dy=scrollY-oldScrollY;
        if (totalScrollDistance>SCROLL_DISTANCE){
            hideToolBar();
            totalScrollDistance=0;
        }else if(totalScrollDistance<-SCROLL_DISTANCE){
            showToolBar();
            totalScrollDistance=0;
        }
        if (dy>0||dy<0)
            totalScrollDistance+=dy;

    }

    public abstract void hideToolBar();
    public abstract void showToolBar();

}
