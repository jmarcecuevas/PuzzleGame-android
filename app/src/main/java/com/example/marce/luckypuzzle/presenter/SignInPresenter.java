package com.example.marce.luckypuzzle.presenter;

import com.facebook.login.LoginResult;

/**
 * Created by marce on 27/03/17.
 */

public interface SignInPresenter {
    void validateCredentials(String email, String password);
    void requestFacebookUserData(LoginResult result);
}
