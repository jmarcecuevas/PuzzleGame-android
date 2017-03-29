package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.common.ActivityScope;
//import com.example.marce.luckypuzzle.di.module.LaunchModule;
import com.example.marce.luckypuzzle.di.module.LaunchModule;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;

import dagger.Subcomponent;

/**
 * Created by marce on 29/03/17.
 */

@ActivityScope
@Subcomponent(modules = {
        LaunchModule.class}
)
public interface LaunchComponent{
    void inject(LaunchActivity launchActivity);
    /*void inject(RegisterActivity registerActivity);
    LoginPresenterImp getLoginPresenter();
    RegisterPresenterImp getRegisterPresenter();*/
}

