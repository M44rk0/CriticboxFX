package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.model.title.Title;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.fxml.FXML;

import static com.m44rk0.criticboxfx.App.user;

public class FavoritesPanelController {

    @FXML
    public ImageView favoritePoster;

    @FXML
    private Button deleteButton;

    @FXML
    public void setPoster(String poster){
        Image image = new Image("https://image.tmdb.org/t/p/original/" + poster, 250, 350, false, false);
        favoritePoster.setImage(image);
    }

    private Title title;
    private ViewController mainController;

    @FXML
    public void removeFavorite(){
        user.removeFavorite(title);
        mainController.showFavorites();
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
