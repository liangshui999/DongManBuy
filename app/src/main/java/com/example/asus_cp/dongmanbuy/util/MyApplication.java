package com.example.asus_cp.dongmanbuy.util;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by asus-cp on 2016-05-20.
 */
public class MyApplication extends Application {
    private static Context context;
    private static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        requestQueue= Volley.newRequestQueue(context);
    }
    public static Context getContext(){
        return context;
    }

    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
