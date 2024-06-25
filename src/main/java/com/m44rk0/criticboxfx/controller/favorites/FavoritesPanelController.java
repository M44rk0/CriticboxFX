package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXML;

import static com.m44rk0.criticboxfx.App.main;
import static com.m44rk0.criticboxfx.App.user;

public class FavoritesPanelController implements CommonController {

    @FXML
    public ImageView favoritePoster;

    private Title title;
    private ViewController mainController;

    @FXML
    public void setPoster(Image poster){
        favoritePoster.setImage(poster);
    }

    @FXML
    public void removeFavorite(){
        user.removeFavorite(title);
        mainController.showFavorites();
    }

    @FXML
    public void showDetails(){
        mainController.showTitleDetails(title);
        mainController.setDetailsIsCalledFrom(2);
    }

    public void setTitle(Title title){
        this.title = title;
    }

    public void setMainController(ViewController mainController) {
        this.mainController = mainController;
    }


    @Override
    public void setTittleField(String title) {

    }

    @Override
    public void setOverviewField(String overview) {

    }

    @Override
    public void setPosterImage(Image posterImage) {

    }

    @Override
    public void setReleaseField(String releaseDate) {

    }
}
