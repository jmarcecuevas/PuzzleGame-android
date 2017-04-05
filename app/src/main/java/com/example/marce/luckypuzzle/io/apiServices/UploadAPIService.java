package com.example.marce.luckypuzzle.io.apiServices;

import android.graphics.Bitmap;

import com.example.marce.luckypuzzle.model.SignUpResponse;
import com.example.marce.luckypuzzle.model.UploadResponse;
import com.example.marce.luckypuzzle.utils.URLUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by marce on 04/04/17.
 */

public interface UploadAPIService {
    @Multipart
    @POST(URLUtils.UPLOAD_IMAGE_URL)
    Call<UploadResponse> upload(@Part MultipartBody.Part file,
                                    @Part("file") RequestBody name);
}
