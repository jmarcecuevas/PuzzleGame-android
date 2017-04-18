package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.module.HomeActivityModule;
import com.example.marce.luckypuzzle.di.scopes.ActivityScope;
import com.example.marce.luckypuzzle.interactor.GameInteractor;
import com.example.marce.luckypuzzle.ui.activities.ChoosePictureActivity;
import com.example.marce.luckypuzzle.ui.activities.HomeActivity;

import dagger.Component;

/**
 * Created by marce on 18/04/17.
 */

@ActivityScope
@Component(dependencies = LuckyGameComponent.class)

public interface ChooseActivityComponent extends ActivityComponent {
    void inject(ChoosePictureActivity choosePictureActivity);
}
