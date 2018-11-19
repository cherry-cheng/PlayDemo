package com.weizhan.superlook.model.api;

import com.weizhan.superlook.model.bean.SeriesDataResponse;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.mine.UpdateBean;
import com.weizhan.superlook.model.bean.mine.UserBean;
import com.weizhan.superlook.model.bean.region.RegionBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Android_ZzT on 17/7/5.
 */

public interface RegionApis {

    String HOST = "http://vweizhan.test.opencodes.top:9007";

    @POST("/api/usrlogin")
    Observable<TTDataResponse<UserBean>> getUserInfo(@Body RequestBody requestBody);

    @GET("/api/getcategory")
    Observable<SeriesDataResponse<RegionBean>> getCateInfo();

    @POST("/api/needmovie")
    Observable<TTDataResponse<UserBean>> postNeedMovie(@Body RequestBody requestBody);

    @GET("/api/getappinfo")
    Observable<TTDataResponse<UpdateBean>> getUpdateInfo(@Query("channel") String channel);
}
