package com.example.marce.luckypuzzle.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
//import com.example.marce.luckypuzzle.di.component.DaggerSignInFragmentComponent;
import com.example.marce.luckypuzzle.di.component.DaggerSignInComponent;
import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.io.callback.SignInCallback;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by marce on 26/03/17.
 */

public class SignInFragment extends LuckyFragment implements SignUpOptions,View.OnClickListener{

    @Inject Context context;
    @Inject SignInPresenterImp mPresenter;
    private CallbackManager callbackManager;
    private Animation animShake;
    private TextView userName,password;
    private TextInputLayout userNameLayout,passwordLayout;
    private Button signIn;
    private LoginButton facebook;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private LaunchActivity activity;
    private SignInInteractor signInInteractor;

    @Override
    public void setUpComponent() {
        DaggerSignInComponent.builder()
                .launchComponent(((LaunchActivity)getActivity()).getComponent())
                .signInModule(new SignInModule(this))
                .build().inject(this);
    }

    @Override
    protected int layout() {
        return R.layout.sign_up_options;
    }

    @Override
    protected void setUi(View v) {
        userNameLayout= (TextInputLayout) v.findViewById(R.id.userName_layout);
        passwordLayout= (TextInputLayout) v.findViewById(R.id.password_layout);
        userName= (TextView) v.findViewById(R.id.userName);
        password= (TextView) v.findViewById(R.id.password);
        signIn= (Button) v.findViewById(R.id.sign_in);
        facebook= (LoginButton) v.findViewById(R.id.facebook);
    }

    @Override
    protected void init() {
        progressDialog= new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.pleaseWait));
        animShake = AnimationUtils.loadAnimation(context,R.anim.shake);
    }

    @Override
    protected void populate() {

    }

    @Override
    protected void setListeners() {
        facebook.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context,String.valueOf(v.getId()),Toast.LENGTH_SHORT).show();
        switch (v.getId()){
//            case R.id.register_now:
//                goToRegister();
//                break;
            /*case R.id.facebook:
                LoginManager.getInstance().logInWithReadPermissions(SignInFragment.this,Arrays.asList("public_profile", "email"));
                break;*/
            case R.id.sign_in:
                mPresenter.validateCredentials(userName.getText().toString(),password.getText().toString());
                break;
        }
    }

    @Override
    public void setUserNameError() {
        userName.setError("Valid input required");
        userName.setAnimation(animShake);
        userName.startAnimation(animShake);
        requestFocus(userNameLayout);
    }

    @Override
    public void setPasswordError() {
        password.setError("Valid input required");
        password.setAnimation(animShake);
        password.startAnimation(animShake);
        requestFocus(passwordLayout);
    }

    @Override
    public void setInvalidCredentialsError() {
        alertDialog.setMessage(getString(R.string.failedLogin));
        alertDialog.show();
    }

    @Override
    public void setSuccessLogin() {
        /*sessionManager.createLoginSession(userName.getText().toString(),password.getText().toString());
        settingsManager= new SettingsManager(this,userName.getText().toString());
        sharedPreferences=getSharedPreferences(userName.getText().toString(),MODE_PRIVATE);
        startActivity(new Intent(this, MainActivity.class));
        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();*/
        Log.e("TODO","OK");
    }

    @Override
    public void signUpWithFacebook() {

    }

    @Override
    public void signUpWithLuckyCode() {

    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void cancelProgress() {
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.cancel();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
