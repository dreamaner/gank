package com.android.xgank.model;


import com.android.mvp.net.IModel;

/**
 * Created by Dreamaner on 2017/5/15.
 */

public class BaseModel implements IModel {

    protected boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return error;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
