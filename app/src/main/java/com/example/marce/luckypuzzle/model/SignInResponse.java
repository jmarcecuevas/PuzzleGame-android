package com.example.marce.luckypuzzle.model;

/**
 * Created by marce on 27/03/17.
 */

public class SignInResponse {
    private boolean success;
    private String image_url;

    public void setImageURL(String image_url) {
        this.image_url = image_url;
    }

    public String getImageURL() {
        return image_url;
    }

    public void setSuccess(boolean success){
        this.success=success;
    }

    public boolean isSuccessful(){
        return success;
    }
}
