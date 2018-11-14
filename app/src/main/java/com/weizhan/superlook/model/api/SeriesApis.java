package com.weizhan.superlook.model.api;

import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.region.CateInfoBean;
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

    @GET("/api/home")
    Observable<SeriesDataResponse<SeriesBean>> getSeries(@Query("navid") String navid);

    @POST("/api/getlist")
    Observable<TTDataResponse<CateInfoBean>> getMoreCateInfo(@Body RequestBody requestBody);
}
