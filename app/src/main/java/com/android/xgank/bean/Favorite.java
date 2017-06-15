package com.android.xgank.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yury on 2017/6/14.
 */

public class Favorite extends DataSupport implements Serializable{


    /**
     * _id : 593e60a9421aa92c769a8c65
     * createdAt : 2017-06-12T17:36:41.29Z
     * desc : 使用ReactNative开发的GitBook阅读器，可以查看在线的书籍信息，在线阅读和下载，目前仅适配了Android端，iOS待适配。
     * images : ["http://img.gank.io/ff815faf-d555-435f-a733-f991d4636453"]
     * publishedAt : 2017-06-15T13:55:57.947Z
     * source : web
     * type : 前端
     * url : https://github.com/le0zh/gitbook-reader-rn/blob/master/README.md
     * used : true
     * who : null
     */

    private String gank_id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String getGank_id() {
        return gank_id;
    }

    public void setGank_id(String gank_id) {
        this.gank_id = gank_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
