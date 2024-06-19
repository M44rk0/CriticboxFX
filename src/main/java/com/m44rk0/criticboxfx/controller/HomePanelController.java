package com.m44rk0.criticboxfx.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomePanelController {

    @FXML
    private ImageView homePagePoster;

    @FXML
    public void setHomePagePoster(Image image){
        homePagePoster.setImage(image);
    }

}
