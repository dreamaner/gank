package com.android.xgank.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yury on 2017/6/7.
 */

public class MoreEntity extends DataSupport {
    private String type;
    private int resId;

    public MoreEntity(String type, int resId) {
        this.type = type;
        this.resId = resId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


    @Override
    public String toString() {
        return "MoreEntity{" +
                "type='" + type + '\'' +
                ", resId=" + resId +
                '}';
    }
}
