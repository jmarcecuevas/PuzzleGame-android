package com.example.marce.luckypuzzle.io.callback;

/**
 * Created by marce on 02/04/17.
 */

public interface SignUpCallback {
    void onSuccessSignUp(String userName,String password);
    void onUserAlreadyExists();
    void onImageError();
    void onEmptyImageError();
    void onImageSuccess();
    void onUnknownError();
}
