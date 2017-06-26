package com.android.xgank.bean;

import com.android.mvp.event.IBus;
import com.android.mvp.net.IModel;
import com.android.xgank.model.BaseModel;

/**
 * Author: yury
 * Created on: 2017/6/20 14:42
 * Description:缩略图切换
 */
public class ShowImgEvent extends BaseModel implements IBus.IEvent {
    @Override
    public int getTag() {
        return 1;
    }

}
