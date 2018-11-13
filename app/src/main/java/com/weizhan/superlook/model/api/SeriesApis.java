package com.weizhan.superlook.model.api;

import com.weizhan.superlook.model.bean.DataListResponse;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1;
import com.weizhan.superlook.model.bean.recommend1.AppRecommend1Show;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.model.bean.region.CateInfoBean;
import com.weizhan.superlook.model.bean.series.AppSeries;
import com.weizhan.superlook.model.bean.series.AppSeriesShow;
import com.weizhan.superlook.model.bean.series.SeriesBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/9/7.
 */

public interface SeriesApis {
    String HOST = "http://vweizhan.test.opencodes.top:9007";

    /**
     * 获取分区列表
     * http://app.bilibili.com/x/v2/show/index?appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&ts=1493711039000&sign=01cfab07e67d3520363d82636296dc8b
     */
    @GET("/x/v2/show/index")
    Observable<DataListResponse<AppSeriesShow>> getSeriesShow(@Query("appkey") String appkey,
                                                              @Query("build") String build,
                                                              @Query("mobi_app") String mobi_app,
                                                              @Query("platform") String platform,
                                                              @Query("ts") String ts
    );

    @GET("/api/home")
    Observable<SeriesDataResponse<SeriesBean>> getSeries(@Query("navid") String navid);

    @POST("/api/getcategorylist")
    Observable<TTDataResponse<CateInfoBean>> getMoreCateInfo(@Body RequestBody requestBody);
}
