package com.android.xgank.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by yury on 2017/6/7.
 */

public class MoreEntity extends DataSupport implements Serializable{
    private String type;
    private String resId;

    public MoreEntity(String type, String resId) {
        this.type = type;
        this.resId = resId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
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
