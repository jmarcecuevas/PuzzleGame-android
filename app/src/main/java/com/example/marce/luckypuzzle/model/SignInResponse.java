package com.example.marce.luckypuzzle.model;

/**
 * Created by marce on 27/03/17.
 */

public class SignInResponse {
    private boolean success;

    public void setSuccess(boolean success){
        this.success=success;
    }

    public boolean isSuccessful(){
        return success;
    }
}
