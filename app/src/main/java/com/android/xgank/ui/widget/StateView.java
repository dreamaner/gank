package com.android.xgank.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Author: yury
 * Created on: 2017/6/27 15:37
 * Description:
 */
public class StateView extends LinearLayout {

    public StateView(Context context,int resId) {
        super(context);
        setView(context,resId);
    }

    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setView(Context context,int resId){
        inflate(context, resId, this);
    }
}
