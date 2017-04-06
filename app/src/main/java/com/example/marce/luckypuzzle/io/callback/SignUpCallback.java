package com.example.marce.luckypuzzle.io.callback;

/**
 * Created by marce on 02/04/17.
 */

public interface SignUpCallback {
    void onSuccessSignUp();
    void onUserAlreadyExists();
    void onImageError();
    void onEmptyImageError();
    void onImageSuccess();
    void onUnknownError();
}
