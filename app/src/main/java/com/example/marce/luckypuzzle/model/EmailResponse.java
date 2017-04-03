package com.example.marce.luckypuzzle.model;

/**
 * Created by marce on 02/04/17.
 */

public class EmailResponse {
    private boolean emailAlreadyExists;
    private String userName;

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public boolean doesEmailExists(){
        return emailAlreadyExists;
    }

    public void setEmailAlreadyExists(boolean emailAlreadyExists){
        this.emailAlreadyExists=emailAlreadyExists;
    }
}
