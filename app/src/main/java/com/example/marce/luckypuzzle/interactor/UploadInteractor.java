package com.example.marce.luckypuzzle.interactor;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignUpAPIService;
import com.example.marce.luckypuzzle.io.apiServices.UploadAPIService;
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
    private UploadAPIService uploadAPIService;

    public UploadInteractor(UploadAPIService uploadAPIService){
        this.uploadAPIService=uploadAPIService;
    }

    public void uploadImage(String mediaPath){
        File file = new File(mediaPath);

        // Parsing any Media type file
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
                        //Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

            }
        });
    }
}
