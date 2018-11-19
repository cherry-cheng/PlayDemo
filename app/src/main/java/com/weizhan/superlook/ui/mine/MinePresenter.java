package com.weizhan.superlook.ui.mine;

import android.util.Log;

import com.common.base.AbsBasePresenter;
import com.google.gson.Gson;
import com.weizhan.superlook.model.api.RegionApis;
import com.weizhan.superlook.model.bean.TTDataResponse;
import com.weizhan.superlook.model.bean.mine.PostUserBean;
import com.weizhan.superlook.model.bean.mine.UpdateBean;
import com.weizhan.superlook.model.bean.mine.UserBean;
import com.weizhan.superlook.util.Constants;
import com.weizhan.superlook.util.SpUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.common.util.Utils.getContext;

/**
 * Created by Android_ZzT on 17/7/6.
 */

public class MinePresenter extends AbsBasePresenter<MineContract.View> {

    private static final String TAG = MinePresenter.class.getSimpleName();

    private RegionApis mRegionApis;

    @Inject
    public MinePresenter(RegionApis regionApis) {
        mRegionApis = regionApis;
    }

    @Override
    public void loadData() {
    }

    public void verifyUserInfo(PostUserBean postUserBean) {
        String json = new Gson().toJson(postUserBean);
        Log.i("cyh222", "json = " + json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        mRegionApis.getUserInfo(requestBody)
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
//                        mView.onDataUpdated(items);
                        SpUtils.putBoolean(getContext(), "isLogin", true);
                        SpUtils.putString(getContext(), "iconurl", userBean.getHeaderimg());
                        SpUtils.putString(getContext(), "name", userBean.getUsername());
                        SpUtils.putString(getContext(), "uid", userBean.getUid());
                        Log.e("cyh112", "uid = " + userBean.getUid());
                        Constants.UID = userBean.getUid();
                        mView.onDataUpdated();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
                        mView.showLoadFailed();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }


    public void getUpdateInfo(String channel) {
        mRegionApis.getUpdateInfo(channel)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<TTDataResponse<UpdateBean>, UpdateBean>() {
                    @Override
                    public UpdateBean apply(@NonNull TTDataResponse<UpdateBean> regionShow) throws Exception {
                        UpdateBean updateBean = regionShow.getBody();
                        return updateBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void onNext(@NonNull UpdateBean updateBean) {
                        //决定弹框还是提示
                        Log.e("cyh777", "linkurl = " + updateBean.getLinkurl());
                        mView.updateInfo(updateBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
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
