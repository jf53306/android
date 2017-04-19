package com.example.jf.jlistview;

import android.app.Application;

/**
 * Created by jf on 17-4-19.
 */

public class App extends Application{
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }


    public static App getInstance(){
        return instance;
    }
}
