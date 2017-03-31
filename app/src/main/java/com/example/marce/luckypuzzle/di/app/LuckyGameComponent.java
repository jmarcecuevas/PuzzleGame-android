package com.example.marce.luckypuzzle.di.app;

import android.app.Application;
import android.content.Context;

//import com.example.marce.luckypuzzle.di.component.LaunchComponent;
//import com.example.marce.luckypuzzle.di.module.InteractorModule;
//import com.example.marce.luckypuzzle.di.module.LaunchModule;

import com.example.marce.luckypuzzle.utils.SessionManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by marce on 24/03/17.
 */

@Singleton
@Component(
        modules = {
                LuckyGameModule.class,
        }
)
public interface LuckyGameComponent {
    Context getContext();
    SessionManager sessionManager();
}
