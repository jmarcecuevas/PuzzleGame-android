package com.example.marce.luckypuzzle.ui.viewModel;

/**
 * Created by marce on 27/03/17.
 */

public interface SignUpOptions {
    void setUserNameError();
    void setPasswordError();
    void setInvalidCredentialsError();
    void setSuccessLogin();
    void signUpWithFacebook();
    void signUpWithLuckyCode();
    void showProgress();
    void hideProgress();
    void cancelProgress();
}
