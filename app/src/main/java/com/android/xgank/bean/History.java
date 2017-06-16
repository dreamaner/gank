package com.android.xgank.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yury on 2017/6/7.
 */

public class History extends DataSupport {
    private String createAt;
    private String content;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
