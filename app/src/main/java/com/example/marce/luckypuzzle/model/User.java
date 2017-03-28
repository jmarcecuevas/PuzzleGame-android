package com.example.marce.luckypuzzle.model;

/**
 * Created by marce on 24/03/17.
 */

public class User {
    private long id;
    private String email;
    private String userName;
    private boolean active;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public boolean getActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active=active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
