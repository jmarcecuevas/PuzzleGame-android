package com.example.marce.luckypuzzle.presenter;

import android.text.TextUtils;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.io.callback.SignInCallback;
import com.example.marce.luckypuzzle.ui.viewModel.SignInView;

/**
 * Created by marce on 27/03/17.
 */

public class SignInPresenterImp extends LuckyPresenter<SignInView,SignInInteractor> implements SignInPresenter,SignInCallback {

    public SignInPresenterImp(SignInView mView,SignInInteractor mInteractor) {
        super(mView,mInteractor);
    }

    @Override
    public void validateCredentials(String email, String password) {
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
            if(TextUtils.isEmpty(email))
                getView().setUserNameError();
            if(TextUtils.isEmpty(password))
                getView().setPasswordError();
        }else{
            getView().showProgress();
            getInteractor().signIn(email,password,this);
        }
    }

    @Override
    public void onSuccessLogin() {
        getView().hideProgress();
        getView().setSuccessLogin();
    }

    @Override
    public void onFailedLogin() {
        getView().hideProgress();
        getView().setInvalidCredentialsError();
    }
}
