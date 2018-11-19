package com.weizhan.superlook.ui.search.need;

import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.google.gson.Gson;
import com.weizhan.superlook.model.api.RegionApis;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.mine.NeedMoviePost;
import com.weizhan.superlook.model.bean.mine.UserBean;
import com.weizhan.superlook.util.SpUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jiayiyang on 17/3/25.
 */

public class NeedMoviePresenter extends AbsBasePresenter<NeedMovieContract.View> implements NeedMovieContract.Presenter {

    private static final String TAG = NeedMoviePresenter.class.getSimpleName();

    private RegionApis mRegionApis;

    @Inject
    public NeedMoviePresenter(RegionApis regionApis) {
        mRegionApis = regionApis;
    }


    @Override
    public void loadData() {

    }

    public void RequestMovie(NeedMoviePost needMoviePost) {
        String json = new Gson().toJson(needMoviePost);
        Log.i("cyh555", "json = " + json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        mRegionApis.postNeedMovie(requestBody)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<TTDataResponse<UserBean>, UserBean>() {
                    @Override
                    public UserBean apply(@NonNull TTDataResponse<UserBean> regionShow) throws Exception {
                        UserBean userBean = regionShow.getBody();
                        return userBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void onNext(@NonNull UserBean userBean) {
                        //成功
                        mView.onSuccess();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onFail();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    @Override
    public void releaseData() {

    }
}
