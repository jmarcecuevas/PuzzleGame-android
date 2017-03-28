package com.example.marce.luckypuzzle.ui.viewModel;

import com.example.marce.luckypuzzle.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by marce on 24/03/17.
 */

public interface UserView extends LEView {
    void signInWithLuckyCode();
    void signInWithGoogle();
}
