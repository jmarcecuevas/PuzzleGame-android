package com.example.marce.luckypuzzle.di.module;

import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marce on 28/03/17.
 */

@Module
public class SignInModule {
    private SignUpOptions signUpOptions;

    public SignInModule(SignUpOptions view) {
        signUpOptions = view;
    }

    @Provides @FragmentScope public SignUpOptions provideSignInView(){
        return signUpOptions;
    }

    @Provides @FragmentScope public SignInPresenterImp providePresenter(SignUpOptions signUpOptions, SignInInteractor signInInteractor){
        return new SignInPresenterImp(signUpOptions,signInInteractor);
    }

}
