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
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by marce on 24/03/17.
 */

public class SignUpOptionsFragment extends LuckyFragment implements FacebookCallback<LoginResult>, SignUpOptions,View.OnClickListener {

    @Inject Context context;
    @Inject SignInPresenterImp mPresenter;
    private CallbackManager callbackManager;
    private EditText userName,password;
    private TextInputLayout userNameLayout,passwordLayout;
    private Button signIn,luckycode,facebook;
    private ProgressDialog progressDialog;
    private Animation animShake;
    private AlertDialog alertDialog;


    @Override
    public void setUpComponent() {
        DaggerSignUpOptionsComponent.builder()
                .launchComponent(((LaunchActivity)getActivity()).getComponent())
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
    protected void setUi(View v) {
        userName= (EditText) v.findViewById(R.id.userName);
        password= (EditText) v.findViewById(R.id.password);
        userNameLayout= (TextInputLayout) v.findViewById(R.id.userName_layout);
        passwordLayout= (TextInputLayout) v.findViewById(R.id.password_layout);
        signIn= (Button) v.findViewById(R.id.sign_in);
        facebook= (Button) v.findViewById(R.id.facebook);
        luckycode= (Button) v.findViewById(R.id.luckyCode);
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
    protected void populate() {
    }

    @Override
    protected void setListeners() {
        luckycode.setOnClickListener(this);
        facebook.setOnClickListener(this);
        signIn.setOnClickListener(this);
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
    public void setSuccessLogin() {
        Toast.makeText(context,"Login successful",Toast.LENGTH_LONG).show();
    }

    @Override
    public void signUpWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(SignUpOptionsFragment.this, Arrays.asList("public_profile", "email"));
    }

    @Override
    public void signUpWithLuckyCode() {
        ((LaunchActivity)getActivity()).replaceFragmentWithBackStackAnimation(SignUpFragment.class,false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.luckyCode:
                signUpWithLuckyCode();
                break;
            case R.id.facebook:
                signUpWithFacebook();
                break;
            case R.id.sign_in:
                mPresenter.validateCredentials(userName.getText().toString(),password.getText().toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        String firstName = null;
        if (Profile.getCurrentProfile() != null) {
            Profile profile = Profile.getCurrentProfile();
             firstName = profile.getFirstName();
            String lastName = profile.getLastName();
            String photoUrl = profile.getProfilePictureUri(200, 200).toString();
        }
        Toast.makeText(getActivity(),firstName,Toast.LENGTH_LONG).show();
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
