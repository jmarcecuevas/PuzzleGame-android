package com.example.marce.luckypuzzle.presenter;

import android.graphics.Bitmap;
import android.widget.EditText;

/**
 * Created by marce on 29/03/17.
 */

public interface SignUpPresenter {
    void validateUserName(String userName);
    void validatePassword(String password);
//    void signUp(String userName,String email,String password);
    void signUp(String userName,String email,String password);
    void uploadPhoto(String mediaPath);


    /*void registerReceiver();
    void unRegisterReceiver();*/
}
