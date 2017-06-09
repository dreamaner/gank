package com.android.xgank.listener;


import com.android.xgank.ui.widget.MyScrollWebView;

/**
 * ConfigManage
 * Created by dreamaner on 2017/5/15 15:03.
 */
public abstract class WebViewScrollListener implements MyScrollWebView.MyScrollListener{

    private boolean isHide=false;


    @Override
    public void onScroll(int dy) {
        if (dy>20 && !isHide){
            hideToolbar();
            isHide=true;
        }else if (dy<-10 && isHide){
            showToolbar();
            isHide=false;
        }

    }


    public abstract void hideToolbar();
    public abstract void showToolbar();
}
