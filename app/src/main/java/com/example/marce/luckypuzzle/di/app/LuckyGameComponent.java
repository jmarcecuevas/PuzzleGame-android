package com.example.marce.luckypuzzle.di.app;

import android.app.Application;
import android.content.Context;

import com.example.marce.luckypuzzle.di.module.InteractorModule;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by marce on 24/03/17.
 */

@Singleton
@Component(
        modules = {
                LuckyGameModule.class,
                InteractorModule.class
        }
)
public interface LuckyGameComponent {

    void inject(Application luckyGameApplication);

    /** Subcomponents **/
//    LogRegComponent plus(LogRegModule logRegModule);
//    MainComponent plus(MainModule mapModule);
//    MainLoginComponent plus(MainLoginModule mainLoginModule);
//    SettingsComponent plus(SettingsModule settingsModule);

    Context getContext();
    SignInInteractor getSignInInteractor();
//    RegisterInteractor getRegisterInteractor();
//    MainInteractor getMainInteractor();
    //MainLoginInteractor getMainLoginInteractor();
}
