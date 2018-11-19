package com.weizhan.superlook;

import android.app.ActivityManager;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.common.app.DaggerAppComponent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.weizhan.superlook.di.component.ActivityComponent;
import com.weizhan.superlook.di.component.ApiComponent;
import com.weizhan.superlook.di.component.DaggerActivityComponent;
import com.weizhan.superlook.di.component.DaggerApiComponent;
import com.weizhan.superlook.di.component.DaggerFragmentComponent;
import com.weizhan.superlook.di.component.FragmentComponent;
import com.weizhan.superlook.di.module.ApiModule;
import com.weizhan.superlook.di.module.FragmentModule;
import com.weizhan.superlook.model.bean.recommend1.KeyWordBean;
import com.weizhan.superlook.model.bean.recommend1.RecommendBean;
import com.weizhan.superlook.util.Constants;
import com.weizhan.superlook.util.RealmHelper;
import com.weizhan.superlook.util.SpUtils;
import com.weizhan.superlook.widget.CustomBitmapMemoryCacheParamsSupplier;
import com.common.app.ActivityLifecycleManager;
import com.common.app.AppComponent;
import com.common.util.Utils;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class App extends Application {

    private static App sInstance;

    public static synchronized App getInstance() {
        return sInstance;
    }

    private static AppComponent sAppComponent;

    private static List<RecommendBean.Banner> data;

    public static List<RecommendBean.Banner> getData() {
        return data;
    }

    public static void setData(List<RecommendBean.Banner> data) {
        App.data = data;
    }

    private List<KeyWordBean> keyWordBeans = new ArrayList<KeyWordBean>();

    public List<KeyWordBean> getKeyWordBeans() {
        return keyWordBeans;
    }

    public void setKeyWordBeans(List<KeyWordBean> keyWordBeans) {
        this.keyWordBeans = keyWordBeans;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Constants.UID = SpUtils.getString(this, "uid", "1");
        registerActivityLifecycleCallbacks(new ActivityLifecycleManager());
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.NONE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 在debug=false时，即线上环境时，上述异常会被捕获并回调ExceptionHandler
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 建议在该回调处上传至我们的Crash监测服务器
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
        initFresco();
        if (sAppComponent == null) {
            sAppComponent = DaggerAppComponent.create();
        }
        //初始化工具类
        Utils.init(this);

        initRealm();
//        Realm.init(getApplicationContext());


        UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
    }

    //各个平台的配置
    {
        //微信
        PlatformConfig.setWeixin("wx597c11ccc9f481c9", "587a637aaccd2c840e2bc07dd5d78e14");
        //新浪微博(第三个参数为回调地址)
        PlatformConfig.setSinaWeibo("31361926", "326e38d6e1a1ae50f0f4c19d9649d4ec","https://api.weibo.com/oauth2/default.html");
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .schemaVersion(1)
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public void initFresco() {
        //当内存紧张时采取的措施
        MemoryTrimmableRegistry memoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
        memoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
                Log.d("Fresco", String.format("onCreate suggestedTrimRatio : %d", suggestedTrimRatio));
                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                        ) {
                    //清除内存缓存
                    Fresco.getImagePipeline().clearMemoryCaches();
                }
            }
        });
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
//                .setBitmapMemoryCacheParamsSupplier(new DefaultBitmapMemoryCacheParamsSupplier((ActivityManager) getSystemService(ACTIVITY_SERVICE)))
//                .setBitmapMemoryCacheParamsSupplier(new DefaultEncodedMemoryCacheParamsSupplier())
                .setBitmapMemoryCacheParamsSupplier(new CustomBitmapMemoryCacheParamsSupplier((ActivityManager) getSystemService(ACTIVITY_SERVICE)))
                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }

    public AppComponent getAppComponent() {
        return sAppComponent;
    }

    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .apiComponent(getApiComponent())
                .build();
    }

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .apiComponent(getApiComponent())
                .fragmentModule(new FragmentModule())
                .build();
    }

    private ApiComponent getApiComponent() {
        return DaggerApiComponent.builder()
                .appComponent(getAppComponent())
                .apiModule(new ApiModule())
                .build();
    }
}
