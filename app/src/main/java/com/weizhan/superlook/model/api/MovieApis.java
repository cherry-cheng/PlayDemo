package com.weizhan.superlook.model.api;
import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.series.SeriesBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/9/7.
 */

public interface MovieApis {
    String HOST = "http://vweizhan.test.opencodes.top:9007";
    @GET("/api/home")
    Observable<SeriesDataResponse<SeriesBean>> getMovie(@Query("navid") String navid);
}
