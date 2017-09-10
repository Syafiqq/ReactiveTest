package com.github.syafiqq.reactivetest.controller;

import android.app.Application;
import android.content.Context;
import com.github.syafiqq.reactivetest.BuildConfig;
import timber.log.Timber;

public class App extends Application
{
    @Override public void onCreate()
    {
        super.onCreate();
        this.initializeTimber();
    }

    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(context);

        // The following line triggers the initialization of ACRA
    }

    private void initializeTimber()
    {
        if(BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.d("initializeTimber");
    }
}

