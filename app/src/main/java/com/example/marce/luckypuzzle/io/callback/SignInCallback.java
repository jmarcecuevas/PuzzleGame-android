package com.example.marce.luckypuzzle.io.callback;

/**
 * Created by marce on 27/03/17.
 */

public interface SignInCallback {
    void onSuccessLogin(String userName,String imageURL);
    void onFailedLogin();
}
