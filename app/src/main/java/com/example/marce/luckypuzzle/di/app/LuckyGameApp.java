package com.example.marce.luckypuzzle.di.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by marce on 24/03/17.
 */

public class LuckyGameApp extends Application {
    private LuckyGameComponent luckyComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGraph();
    }

    /**
     * The object graph contains all the instances of the objects
     * that resolve a dependency
     * */
    private void setupGraph() {
        luckyComponent= DaggerLuckyGameComponent.builder()
                .luckyGameModule(new LuckyGameModule(this))
                .build();
    }

    public static LuckyGameComponent getLuckyComponent(Context context){
        return getApp(context).luckyComponent;
    }

    public static LuckyGameApp getApp(Context context){
        return (LuckyGameApp) context.getApplicationContext();
    }


}
