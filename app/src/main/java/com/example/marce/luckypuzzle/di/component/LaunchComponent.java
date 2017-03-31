package com.example.marce.luckypuzzle.di.component;

import android.content.Context;

import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.scopes.ActivityScope;
//import com.example.marce.luckypuzzle.di.module.LaunchModule;
import com.example.marce.luckypuzzle.di.module.LaunchModule;
import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by marce on 29/03/17.
 */

@ActivityScope
@Component(dependencies = LuckyGameComponent.class,modules = {
        LaunchModule.class}
)
public interface LaunchComponent extends ActivityComponent {
    void inject(LaunchActivity launchActivity);

    //Context getContext();

}

