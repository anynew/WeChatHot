package com.anynew.wechathot.app;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by anynew on 2016/10/25.
 */

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        QbSdk.initX5Environment(this,null);
    }



    public static App getInstance() {
        return app;
    }

}
