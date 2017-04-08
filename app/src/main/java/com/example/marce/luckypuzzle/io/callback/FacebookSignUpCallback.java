package com.example.marce.luckypuzzle.io.callback;

/**
 * Created by marce on 08/04/17.
 */

public interface FacebookSignUpCallback {
    void onSuccessSignUpFB(String userName,String email,String imageURL);
    void onUnknownErrorFB();
}

