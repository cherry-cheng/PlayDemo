package com.weizhan.superlook.model.api;

import com.weizhan.superlook.model.bean.DataListResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;

import com.weizhan.superlook.model.bean.play.PlayInfoBean;
import com.weizhan.superlook.model.bean.play.PlayMoreInfoBean;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1Show;
import com.weizhan.superlook.model.bean.recommend1.ChangeBean;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/9/5.
 */

public interface Recommend1Apis {

    String HOST = "http://vweizhan.test.opencodes.top:9007";

    /**
     * 获取分区列表
     * http://app.bilibili.com/x/v2/show/index?appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&ts=1493711039000&sign=01cfab07e67d3520363d82636296dc8b
     */
    @GET("/x/v2/show/index")
    Observable<DataListResponse<AppRecommend1Show>> getRecommend1Show(@Query("appkey") String appkey,
                                                                      @Query("build") String build,
                                                                      @Query("mobi_app") String mobi_app,
                                                                      @Query("platform") String platform,
                                                                      @Query("ts") String ts
    );

    @GET("/api/home")
    Observable<TTDataResponse<RecommendBean>> getRecommend(@Query("navid") String navid);

    //电影
    @GET("/api/movieinfo")
    Observable<TTDataResponse<PlayInfoBean>> getPlayInfo(@Query("id") int id,
                                                         @Query("type") int type);

    //电视剧。综艺
    @GET("/api/movieinfo")
    Observable<TTDataResponse<PlayMoreInfoBean>> getPlayMoreInfo(@Query("id") int id,
                                                             @Query("type") int type);
    @GET("/api/homechange")
    Observable<TTDataResponse<ChangeBean>> getChangeInfo(@Query("id") int id,
                                                         @Query("type") int type,
                                                         @Query("page") int page);
}
