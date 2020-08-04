package com.gyst.droplet;
import android.app.Application;


import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Droplet extends Application {
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
}
