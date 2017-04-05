package com.example.marce.luckypuzzle.interactor;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignUpAPIService;
import com.example.marce.luckypuzzle.io.apiServices.UploadAPIService;
import com.example.marce.luckypuzzle.io.callback.SignUpCallback;
import com.example.marce.luckypuzzle.model.SignUpResponse;
import com.example.marce.luckypuzzle.model.UploadResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marce on 04/04/17.
 */

public class UploadInteractor {
    private static String TAG= UploadAPIService.class.getSimpleName();
    private SignUpAPIService signUpAPIService;
    private UploadAPIService uploadAPIService;

    public UploadInteractor(SignUpAPIService signUpAPIService,UploadAPIService uploadAPIService){
        this.uploadAPIService=uploadAPIService;
        this.signUpAPIService=signUpAPIService;
    }

    public void uploadImage(final String userName, final String email, final String password, String mediaPath, final SignUpCallback signUpCallback){
        final File file = new File(mediaPath);
        final String image_url="http://luckycode.esy.es/puzzle/images";
        //final String imageURL="http:"

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<UploadResponse> call = uploadAPIService.upload(fileToUpload, filename);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                UploadResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Log.e("SI","IMAGEN");
                        signUp(userName,email,image_url,password,signUpCallback);
                    } else {
                        signUpCallback.onImageError();
                    }
                } else {
                    assert serverResponse != null;
                    signUpCallback.onUnknownError();
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
            }
        });
    }

    public void signUp(String userName, String email, String mediaPath,String password, final SignUpCallback signUpCallback){
        Call<SignUpResponse> call= signUpAPIService.inserUser(userName,email,mediaPath,password);
        call.enqueue(new retrofit2.Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.isSuccessful()){
                        Log.i(TAG,"SUCCESSFUL REGISTER");
                        signUpCallback.onSuccessSignUp();
                    }
                    else if(mResponse.doesUserExist()){
                        Log.i(TAG,"USERNAME ALREADY EXISTS");
                        signUpCallback.onUserAlreadyExists();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.e(TAG,"UNKNOWN ERROR");
                t.printStackTrace();
                signUpCallback.onUnknownError();
            }
        });
    }
}
