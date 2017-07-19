package com.myregistry.homestore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private static String BUNDLE_NAME = "bundle";

    public void launchNewActivity(Class<?> classL){
        launchNewActivity(classL, null);
    }

    public void launchNewActivity(Class<?> classL, Bundle bundle){
        Intent intent = new Intent(this, classL);
        if(bundle!=null) intent.putExtra(BUNDLE_NAME, bundle);
        startActivity(intent);
    }

    public Bundle getBundle(){
        return getIntent().getBundleExtra(BUNDLE_NAME);
    }
}
