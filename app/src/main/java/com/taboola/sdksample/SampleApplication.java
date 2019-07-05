package com.taboola.sdksample;

import android.app.Application;

import com.google.gson.Gson;
import com.taboola.android.api.TaboolaApi;
import com.taboola.android.utils.AssetUtil;

public class SampleApplication extends Application {
    private static final String APP_CONFIG_FILE_TITLE = "app_config.json";
    private AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        String appConfigString = AssetUtil.getHtmlTemplateFileContent(this, APP_CONFIG_FILE_TITLE);
        appConfig = new Gson().fromJson(appConfigString, AppConfig.class);

        TaboolaApi.getInstance()
                .init(getApplicationContext(), appConfig.getPublisher(), appConfig.getApiKey());
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }
}
