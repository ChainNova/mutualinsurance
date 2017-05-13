package com.pingan.demo.controller;

/**
 * Created by guolidong752 on 17/5/8.
 */

public class UserController {
    private static UserController instance;

    private String user;
    private String pass;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
