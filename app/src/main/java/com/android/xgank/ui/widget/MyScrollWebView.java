package com.android.xgank.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.android.mvp.log.XLog;


/**
 * Created by zhanghao on 2017/5/17.
 */

public class MyScrollWebView extends WebView{


    private static final String TAG = "MyScrollWebView";

    private MyScrollListener scrollListener;


    public void setScrollListener(MyScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public MyScrollWebView(Context context) {
        super(context);
    }


    public MyScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int dy=t-oldt;

        XLog.d("l : ",l);
        XLog.d("t : ",t);
        XLog.d("oldl : ",oldl);
        XLog.d("oldt : ",oldt);

        if (scrollListener!=null)
            scrollListener.onScroll(dy);
    }

    public  interface  MyScrollListener{
         void onScroll(int dy);
    }



}
