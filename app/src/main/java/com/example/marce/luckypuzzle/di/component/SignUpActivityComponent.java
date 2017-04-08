package com.example.marce.luckypuzzle.di.component;

import android.content.Context;

import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.module.SignUpActivityModule;
import com.example.marce.luckypuzzle.di.scopes.ActivityScope;
//import com.example.marce.luckypuzzle.di.module.SignUpActivityModule;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
import com.example.marce.luckypuzzle.interactor.UploadInteractor;
import com.example.marce.luckypuzzle.model.Facebook;
import com.example.marce.luckypuzzle.ui.activities.SignUpActivity;
import com.example.marce.luckypuzzle.utils.SessionManager;

import dagger.Component;

/**
 * Created by marce on 29/03/17.
 */

@ActivityScope
@Component(dependencies = LuckyGameComponent.class,modules = {
        SignUpActivityModule.class}
)
public interface SignUpActivityComponent extends ActivityComponent {
    void inject(SignUpActivity signUpActivity);

    Context getContext();
    SessionManager getSessionManager();
    SignInInteractor getSignInInteractor();
    SignUpInteractor getSignUpInteractor();
    UploadInteractor getUploadInteractor();
    Facebook getFacebook();
}

