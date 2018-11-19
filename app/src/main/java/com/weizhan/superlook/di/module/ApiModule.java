package com.weizhan.superlook.di.module;

import com.weizhan.superlook.di.scope.GlobalApis;
import com.weizhan.superlook.model.api.ApiHelper;
import com.weizhan.superlook.model.api.MovieApis;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.api.RegionApis;
import com.weizhan.superlook.model.api.SeriesApis;
import com.weizhan.superlook.model.api.VarietyApis;
import com.weizhan.superlook.util.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiayiyang on 17/3/25.
 */

@Module
public class ApiModule {

    @GlobalApis
    @Provides
    RegionApis provideRegionService(@Named("RegionApi") Retrofit retrofit) {
        return retrofit.create(RegionApis.class);
    }

    @GlobalApis
    @Provides
    Recommend1Apis provideRecommend1Service(@Named("Recommend1Api") Retrofit retrofit) {
        return retrofit.create(Recommend1Apis.class);
    }

    @GlobalApis
    @Provides
    SeriesApis provideSeriesService(@Named("SeriesApi") Retrofit retrofit) {
        return retrofit.create(SeriesApis.class);
    }

    @GlobalApis
    @Provides
    VarietyApis provideVarietyService(@Named("VarietyApi") Retrofit retrofit) {
        return retrofit.create(VarietyApis.class);
    }

    @GlobalApis
    @Provides
    MovieApis provideMovieService(@Named("MovieApi") Retrofit retrofit) {
        return retrofit.create(MovieApis.class);
    }

    @GlobalApis
    @Provides
    @Named("RegionApi")
    Retrofit provideRegionRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, RegionApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("Recommend1Api")
    Retrofit provideRecommend1Retrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Recommend1Apis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("SeriesApi")
    Retrofit provideSeriesRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, SeriesApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("VarietyApi")
    Retrofit provideVarietyRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, VarietyApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("MovieApi")
    Retrofit provideMovieRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, MovieApis.HOST);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient, String url) {
        return builder.baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @GlobalApis
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @GlobalApis
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @GlobalApis
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                String sign = ApiHelper.getSign(oldRequest.url());
                //添加sign参数
                HttpUrl.Builder newBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
                        .addQueryParameter(ApiHelper.PARAM_SIGN, sign);
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(newBuilder.build())
                        .addHeader("uid", Constants.UID)
                        .build();
                return chain.proceed(newRequest);
            }
        };
        //设置拦截器
        builder.addInterceptor(interceptor);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

}
