package com.android.xgank.bean;

import com.android.xgank.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * SearchResult
 * Created by dreamaner on 2016/12/19 17:00.
 */
public class SearchResult extends BaseModel{


    private List<SearchResult.Item> results;

    public List<SearchResult.Item> getResults() {
        return results;
    }

    public void setResults(List<SearchResult.Item> results) {
        this.results = results;
    }

    @Override
    public boolean isNull() {
        return results == null || results.isEmpty();
    }


    public static class Item implements Serializable {


        /**
         * desc : 美美美：新加坡JKAI携手美女《太阳的后裔》中文版Always
         * ganhuo_id : 573c877c6776591ca681f8c0
         * publishedAt : 2016-05-20T10:05:09.959000
         * readability :
         * type : 休息视频
         * url : http://v.youku.com/v_show/id_XMTU3MjUwMDkxMg==.html
         * who : lxxself
         */

        private String desc;
        private String ganhuo_id;
        private String publishedAt;
        private String readability;
        private String type;
        private String url;
        private String who;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGanhuo_id() {
            return ganhuo_id;
        }

        public void setGanhuo_id(String ganhuo_id) {
            this.ganhuo_id = ganhuo_id;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getReadability() {
            return readability;
        }

        public void setReadability(String readability) {
            this.readability = readability;
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

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
