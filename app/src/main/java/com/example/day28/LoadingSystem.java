package com.example.day28;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingSystem {
    Activity activity;
    AlertDialog alertDialog;

    public LoadingSystem(Activity activity) {
        this.activity = activity;
    }
    public void startLoadingSystem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.loadingsystem,null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }
    public void endLoadingsystem(){
        alertDialog.dismiss();
    }
}
