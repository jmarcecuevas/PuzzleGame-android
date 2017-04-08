package com.example.marce.luckypuzzle.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.di.component.DaggerSignUpOptionsComponent;
import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.HomeActivity;
import com.example.marce.luckypuzzle.ui.activities.SignUpActivity;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by marce on 24/03/17.
 */

public class SignUpOptionsFragment extends LuckyFragment implements FacebookCallback<LoginResult>, SignUpOptions/*View.OnClickListener*/ {

    @Inject Context context;
    @Inject SignInPresenterImp mPresenter;
    @BindView(R.id.userName)EditText userName;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.userName_layout) TextInputLayout userNameLayout;
    @BindView(R.id.password_layout)TextInputLayout passwordLayout;
    @BindView(R.id.sign_in)Button signIn;
    @BindView(R.id.facebook)Button facebook;
    @BindView(R.id.luckyCode)Button luckycode;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    private Animation animShake;
    private AlertDialog alertDialog;


    @Override
    public void setUpComponent() {
        DaggerSignUpOptionsComponent.builder()
                .signUpActivityComponent(((SignUpActivity)getActivity()).getComponent())
                .signInModule(new SignInModule(this)).build().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
    }

    @Override
    protected int layout() {
        return R.layout.sign_up_options;
    }

    @Override
    protected void init() {
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.pleaseWait));
        alertDialog= new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getString(R.string.signInStatus));
        animShake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
    }

    @Override
    public void setUserNameError() {
        userName.setError(getString(R.string.enterAnUserName));
        userName.setAnimation(animShake);
        userName.startAnimation(animShake);
        requestFocus(userNameLayout);
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.enterAPassword));
        password.setAnimation(animShake);
        password.startAnimation(animShake);
        requestFocus(userNameLayout);
    }

    @Override
    public void setInvalidCredentialsError() {
        alertDialog.setMessage(getString(R.string.failedLogin));
        alertDialog.show();
    }

    @Override
    public void setSuccessLogin(String userName,String imageURL) {
        Intent intent= new Intent(getActivity(),HomeActivity.class);
        intent.putExtra("userName",userName);
        intent.putExtra("imageURL",imageURL);
        startActivity(intent);
    }

    @Override
    public void signUpWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(SignUpOptionsFragment.this, Arrays.asList("public_profile", "email"));
    }

    @Override
    public void signUpWithLuckyCode() {
        ((SignUpActivity)getActivity()).replaceFragmentWithBackStackAnimation(SignUpFragment.class,false);
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

    @OnClick({R.id.luckyCode,R.id.facebook,R.id.sign_in})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.luckyCode:
                signUpWithLuckyCode();
                break;
            case R.id.facebook:
                signUpWithFacebook();
                break;
            case R.id.sign_in:
                mPresenter.validateCredentials(userName.getText().toString(), password.getText().toString());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        mPresenter.requestFacebookUserData(loginResult);
    }

    @Override
    public void onCancel() {
        Toast.makeText(getActivity(),"CANCEL",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_LONG).show();
    }
}
