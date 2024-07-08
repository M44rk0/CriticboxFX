package com.m44rk0.criticboxfx.controller.user;

import com.m44rk0.criticboxfx.model.user.User;

public class CurrentlyUserController {

    private static User user;


    public void addFavorite(){

    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentlyUserController.user = user;
    }
}
