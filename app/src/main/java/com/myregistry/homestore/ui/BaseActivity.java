package com.myregistry.homestore.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.myregistry.homestore.R;
import com.myregistry.homestore.presenter.Presenter;

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

    public void launchNewActivity(Class<?> classL, Bundle bundle, int code){
        Intent intent = new Intent(this, classL);
        if(bundle!=null) intent.putExtra(BUNDLE_NAME, bundle);
        startActivityForResult(intent, code);
    }

    public Bundle getBundle(){
        return getIntent().getBundleExtra(BUNDLE_NAME);
    }

    public void generateSnack(View main, Presenter presenter){
        Snackbar snackbar = Snackbar.make(main, R.string.snack_no_internet_title, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RETRY", view -> {
            try {
                presenter.getItems();
                snackbar.dismiss();
            } catch (Exception e){
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
