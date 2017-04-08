package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.module.HomeActivityModule;
import com.example.marce.luckypuzzle.di.module.SignUpActivityModule;
import com.example.marce.luckypuzzle.di.scopes.ActivityScope;
import com.example.marce.luckypuzzle.ui.activities.HomeActivity;
import com.example.marce.luckypuzzle.ui.activities.SignUpActivity;

import dagger.Component;

/**
 * Created by marce on 08/04/17.
 */

@ActivityScope
@Component(dependencies = LuckyGameComponent.class,modules = {
        HomeActivityModule.class}
)
public interface HomeComponent extends ActivityComponent {
    void inject(HomeActivity homeActivity);
}

