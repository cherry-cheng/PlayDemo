package com.weizhan.superlook.di.component;

import com.weizhan.superlook.di.module.ApiModule;
import com.weizhan.superlook.di.scope.GlobalApis;
import com.weizhan.superlook.model.api.MovieApis;
import com.weizhan.superlook.model.api.Recommend1Apis;
import com.weizhan.superlook.model.api.RegionApis;
import com.weizhan.superlook.model.api.SeriesApis;
import com.weizhan.superlook.model.api.VarietyApis;
import com.common.app.AppComponent;

import dagger.Component;

/**
 * Created by Android_ZzT on 17/6/15.
 */

@Component(dependencies = AppComponent.class, modules = ApiModule.class)
@GlobalApis
public interface ApiComponent {

    RegionApis regionApis();

    Recommend1Apis recommend1Apis();

    SeriesApis seriesApis();

    MovieApis movieApis();

    VarietyApis varietyApis();
}
