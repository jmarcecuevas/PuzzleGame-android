package com.example.marce.luckypuzzle.model;

/**
 * Created by marce on 02/04/17.
 */

public class EmailResponse {
    private boolean emailAlreadyExists;
    private String userName;
    private String image_url;

    public String getImageURL(){return image_url;}
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    private void setImageURL(String image_url){this.image_url=image_url;}
    public boolean doesEmailExists(){
        return emailAlreadyExists;
    }

    public void setEmailAlreadyExists(boolean emailAlreadyExists){
        this.emailAlreadyExists=emailAlreadyExists;
    }
}
