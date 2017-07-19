package com.myregistry.homestore;

import android.app.Application;

import io.realm.Realm;

public class HomestoreApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
