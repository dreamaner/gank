package com.android.xgank.net;

import com.android.xgank.bean.GankResults;
import com.android.xgank.bean.SearchResult;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yury on 2017/6/5.
 */

public interface GankService {


    @GET("data/{category}/{number}/{page}")
    Flowable<GankResults> getCategoryDate(@Path("category") String category,
                                                   @Path("number") int number,
                                                   @Path("page") int page);

    @GET("random/data/福利/{number}")
    Flowable<GankResults> getRandomBeauties(@Path("number") int number);

    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Flowable<SearchResult> getSearchResult(@Path("key") String key,
                                           @Path("count") int count,
                                           @Path("page") int page);

    @GET("data/{type}/{number}/{page}")
    Flowable<GankResults> getGankData(@Path("type") String type,
                                      @Path("number") int pageSize,
                                      @Path("page") int pageNum);
}
