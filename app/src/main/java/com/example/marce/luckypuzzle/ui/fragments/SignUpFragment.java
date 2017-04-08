package com.example.marce.luckypuzzle.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.di.component.DaggerSignUpComponent;
import com.example.marce.luckypuzzle.di.module.SignUpModule;
import com.example.marce.luckypuzzle.presenter.SignUpPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.HomeActivity;
import com.example.marce.luckypuzzle.ui.activities.SignUpActivity;
import com.example.marce.luckypuzzle.ui.adapters.TextWatcherAdapter;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpView;
import com.example.marce.luckypuzzle.utils.ImagePicker;
import com.example.marce.luckypuzzle.utils.SettingsManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by marce on 26/03/17.
 */

public class SignUpFragment extends LuckyFragment implements SignUpView{

    @Inject SignUpPresenterImp mPresenter;
    @BindView(R.id.login_now)TextView loginNow;
    @BindView(R.id.sign_up)Button signUp;
    @BindView(R.id.profilePicture)CircleImageView profilePicture;
    @BindView(R.id.userName)EditText userName;
    @BindView(R.id.email)EditText email;
    @BindView(R.id.password)EditText password;
    @BindView(R.id.userName_layout)TextInputLayout userNameLayout;
    @BindView(R.id.email_layout)TextInputLayout emailLayout;
    @BindView(R.id.password_layout)TextInputLayout passwordLayout;
    @BindView(R.id.uploadPhoto)FloatingActionButton uploadPhoto;
    @BindView(R.id.progressImage)ProgressBar progressImage;
    private static final int PICK_IMAGE_ID = 234;
    private SettingsManager settingsManager;
    private Bitmap photo;
    private Animation animShake;
    private ProgressDialog progressDialog;
    private android.support.v7.app.AlertDialog alertDialog;
    private boolean validUserName,validPassword,validEmail=true;

    @Override
    public void setUpComponent() {
        DaggerSignUpComponent.builder()
                .signUpActivityComponent(((SignUpActivity)getActivity()).getComponent())
                .signUpModule(new SignUpModule(this))
                .build().inject(this);
    }

    @Override
    protected int layout() {
        return R.layout.lucky_code_sign_up;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName.addTextChangedListener(new TextWatcherAdapter(userName) {
            @Override
            public void validate(EditText editText, String text) {
                mPresenter.validateUserName(userName.getText().toString());
            }
        });
        password.addTextChangedListener(new TextWatcherAdapter(password) {
            @Override
            public void validate(EditText editText, String text) {
                mPresenter.validatePassword(password.getText().toString());
            }
        });
    }

    @Override
    protected void init() {
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.pleaseWait));
        alertDialog= new android.support.v7.app.AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getString(R.string.sigupStatus));
        animShake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
    }


    @Override
    public void showPictureDialog() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
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
    public void showImageProgress() {
        progressImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImageProgress() {
        progressImage.setVisibility(View.GONE);
    }

    @Override
    public void updatePhoto() {
        profilePicture.setImageBitmap(photo);
    }

    @Override
    public void updateImageError() {
        Toast.makeText(getActivity(),R.string.updateImageError,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setEmptyImageError() {
        Toast.makeText(getActivity(),R.string.emptyImageError,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setEmptyUserNameError() {
        userName.setError(getString(R.string.emptyUserName));
        userName.setAnimation(animShake);
        userName.startAnimation(animShake);
        requestFocus(userNameLayout);
    }

    @Override
    public void setEmptyPasswordError() {
        password.setError(getString(R.string.emptyPassword));
        password.setAnimation(animShake);
        password.startAnimation(animShake);
        requestFocus(passwordLayout);
    }

    @Override
    public void setEmptyEmailError() {
        password.setError(getString(R.string.emptyEmail));
        password.setAnimation(animShake);
        password.startAnimation(animShake);
        requestFocus(passwordLayout);
    }

    @Override
    public void setValidUserName(boolean validUserName) {
        this.validUserName=validUserName;
    }

    @Override
    public void setValidEmail(boolean validEmail) {
        this.validEmail=validEmail;
    }

    @Override
    public void setSuccessSignUp(String userName) {
        Intent intent=new Intent(getActivity(),HomeActivity.class);
        intent.putExtra("userName",userName);
        intent.putExtra("photo",photo);
        settingsManager= new SettingsManager(getActivity(),userName);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public boolean isUserNameValid() {
        return this.validUserName;
    }

    @Override
    public boolean isEmailValid() {
        return this.validEmail;
    }

    @Override
    public void setInvalidUserNameError() {
        userName.setError(getString(R.string.shortUsername));
    }

    @Override
    public void setInvalidPasswordError() {
        password.setError(getString(R.string.shortPassword));
    }

    @Override
    public void setInvalidEmailError() {
        email.setError(getString(R.string.invalidEmail));
        email.setAnimation(animShake);
        email.startAnimation(animShake);
        requestFocus(emailLayout);
    }

    @Override
    public void setUserAlreadyExistsError() {
        alertDialog.setMessage(getString(R.string.invalidUsername));
        alertDialog.show();
    }

    @Override
    public void backToSignIn() {
        ((SignUpActivity)getActivity()).replaceFragmentWithBackStackAnimation(SignUpOptionsFragment.class,false);
    }

    @Override
    public void setUnknownError() {

    }

    @Override
    public void setValidPassword(boolean validPassword) {
        this.validPassword=validPassword;
    }

    @Override
    public boolean isPasswordValid() {
        return validPassword;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_ID && resultCode == getActivity().RESULT_OK){
            photo=ImagePicker.getImageFromResult(getActivity(),resultCode,data);
            mPresenter.uploadPhoto(ImagePicker.getMediaPath());
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @OnClick({R.id.uploadPhoto,R.id.login_now,R.id.sign_up})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.uploadPhoto:
                showPictureDialog();
                break;
            case R.id.sign_up:
                mPresenter.signUp(userName.getText().toString(),email.getText().toString(),
                        password.getText().toString());
                break;
            case R.id.login_now:
                backToSignIn();
                break;
        }
    }

}
