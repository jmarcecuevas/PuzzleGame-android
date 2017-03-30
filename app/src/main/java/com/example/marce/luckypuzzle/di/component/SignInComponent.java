package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.common.ActivityScope;
//import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.common.FragmentScope;
import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.presenter.SignInPresenter;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;
import com.example.marce.luckypuzzle.ui.fragments.SignInFragment;

import dagger.Subcomponent;

/**
 * Created by marce on 28/03/17.
 */

@FragmentScope
@Subcomponent(modules = {
        SignInModule.class}
)
public interface SignInComponent{
    void inject(SignInFragment signInFragment);
    //SignInPresenterImp getSignInPresenter();

    /*LoginPresenterImp getLoginPresenter();
    RegisterPresenterImp getRegisterPresenter();*/
}
