package com.anynew.wechathot.app;

import android.app.Application;

/**
 * Created by anynew on 2016/10/25.
 */

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }



    public static App getInstance() {
        return app;
    }

}
