package com.example.marce.luckypuzzle.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.di.component.DaggerSignUpComponent;
import com.example.marce.luckypuzzle.di.module.SignUpModule;
import com.example.marce.luckypuzzle.presenter.SignUpPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;
import com.example.marce.luckypuzzle.ui.adapters.TextWatcherAdapter;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpView;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by marce on 26/03/17.
 */

public class SignUpFragment extends LuckyFragment implements SignUpView/*,View.OnClickListener*/{

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
    private Animation animShake;
    private ProgressDialog progressDialog;
    private android.support.v7.app.AlertDialog alertDialog;
    private CharSequence pictureOpions[];
    private boolean validUserName,validPassword,validEmail=true;
    private static final int TAKE_A_PHOTO=0,SELECT_FROM=1,RESULT_LOAD_IMG = 1;
    private static final int CAMERA_REQUEST = 1888;
    String imgDecodableString;
    private Uri filePath;
    private Bitmap bitmap;
    private String mediaPath;

    @Override
    public void setUpComponent() {
        DaggerSignUpComponent.builder()
                .launchComponent(((LaunchActivity)getActivity()).getComponent())
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
        pictureOpions= new CharSequence[] {getString(R.string.takeAPhoto),
                getString(R.string.selectPhoto)};
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.pleaseWait));
        alertDialog= new android.support.v7.app.AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getString(R.string.sigupStatus));
        animShake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
    }


    @OnClick({R.id.profilePicture,R.id.login_now,R.id.sign_up})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.profilePicture:
                showPictureDialog();
                break;
            case R.id.sign_up:
                /*mPresenter.signUp(userName.getText().toString(),email.getText().toString(),
                        password.getText().toString());*/
                mPresenter.signUp(userName.getText().toString(),email.getText().toString(),password.getText().toString(),mediaPath);
                break;
            case R.id.login_now:
                backToSignIn();
                break;
        }
    }

    @Override
    public void showPictureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.chooseAPhoto));
        builder.setItems(pictureOpions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case TAKE_A_PHOTO:
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        break;
                    case SELECT_FROM:
                        loadPictureFromGallery();
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void loadPictureFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
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
    public void setSuccessSignUp() {
        Toast.makeText(getActivity(),"SIGN UP SUCCESSFUL",Toast.LENGTH_LONG).show();
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
        ((LaunchActivity)getActivity()).replaceFragmentWithBackStackAnimation(SignUpOptionsFragment.class,false);
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
        try {
            // When an Image is picked
            // When an Image is picked
            if (requestCode == 0 && resultCode == getActivity().RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                profilePicture.setImageBitmap(bitmap);

                // Set the Image in ImageView for Previewing the Media

                cursor.close();


            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePicture.setImageBitmap(photo);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
