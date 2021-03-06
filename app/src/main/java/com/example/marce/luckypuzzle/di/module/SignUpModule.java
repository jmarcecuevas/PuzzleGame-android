package com.example.marce.luckypuzzle.di.module;

import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
import com.example.marce.luckypuzzle.interactor.UploadInteractor;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.presenter.SignUpPresenterImp;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpView;
import com.example.marce.luckypuzzle.utils.SessionManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marce on 02/04/17.
 */

@Module
public class SignUpModule {
    private SignUpView signUpView;

    public SignUpModule(SignUpView view) {
        signUpView = view;
    }

    @Provides
    @FragmentScope
    public SignUpView provideSignUpView(){
        return signUpView;
    }

//    @Provides @FragmentScope public SignUpPresenterImp providePresenter(SignUpView signUpView, SignUpInteractor signUpInteractor){
//        return new SignUpPresenterImp(signUpView,signUpInteractor);
//    }

    @Provides @FragmentScope public SignUpPresenterImp providePresenter(SignUpView signUpView, UploadInteractor uploadInteractor, SessionManager sessionManager){
        return new SignUpPresenterImp(signUpView,uploadInteractor,sessionManager);
    }

}