package com.example.marce.luckypuzzle.ui.viewModel;

/**
 * Created by marce on 27/03/17.
 */

public interface SignInView {
    void setUserNameError();
    void setPasswordError();
    void setInvalidCredentialsError();
    void setSuccessLogin();
    void goToRegister();
    void showProgress();
    void hideProgress();
    void cancelProgress();
}
