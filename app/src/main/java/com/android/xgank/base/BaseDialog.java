package com.android.xgank.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.ButterKnife;
import com.android.xgank.R;

/**
 * Created by yury on 2017/6/5.
 */

public abstract class BaseDialog extends Dialog {
    Context mContext;

    public BaseDialog(Context context, int layoutId) {
        this(context, layoutId, R.style.MyDialog);
    }

    public BaseDialog(Context context, int layoutId, int styleId) {
        super(context, styleId);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null);
        this.setContentView(view);
        ButterKnife.bind(this);
    }

}
