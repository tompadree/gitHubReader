package com.example.tom.githubreader.presentation;

/**
 * Created by Tom on 1.11.2016..
 */


import android.app.Application;

import com.example.tom.githubreader.presentation.di.components.ApplicationComponent;
import com.example.tom.githubreader.presentation.di.components.DaggerApplicationComponent;
import com.example.tom.githubreader.presentation.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
