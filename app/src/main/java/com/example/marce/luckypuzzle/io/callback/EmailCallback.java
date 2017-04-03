package com.example.marce.luckypuzzle.io.callback;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by marce on 02/04/17.
 */

public interface EmailCallback {
    void onEmailAlreadyExists(GoogleSignInAccount acc, String userName);
    void onEmailOK(GoogleSignInAccount acc);
}
