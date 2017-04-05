package com.example.marce.luckypuzzle.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marce on 04/04/17.
 */

public class UploadResponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}