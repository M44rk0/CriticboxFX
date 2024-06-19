package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.model.Title;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.fxml.FXML;

import static com.m44rk0.criticboxfx.App.user;

public class FavoritePanelController {

    @FXML
    public ImageView favoritePoster;

    @FXML
    private Button deleteButton;

    @FXML
    public void setPoster(Image image){
        favoritePoster.setImage(image);
    }

    private Title title;
    private ViewController mainController;

    @FXML
    public void removeFavorite(){
        user.removeFavorite(title);
        mainController.updateFavoritesUI();
    }

    @FXML
    public void showDetails(){
        mainController.showFavoriteDetails(title);
    }

    public void setTitle(Title title){
        this.title = title;
    }

    public void setMainController(ViewController mainController) {
        this.mainController = mainController;
    }


}
