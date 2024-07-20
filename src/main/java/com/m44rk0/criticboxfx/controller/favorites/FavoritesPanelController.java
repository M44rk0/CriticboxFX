package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXML;

public class FavoritesPanelController implements CommonController {

    @FXML
    public ImageView favoritePoster;

    private Title title;
    private MainController mainController;

    @FXML
    public void showDetails(){
        mainController.showTitleDetails(title);
        mainController.setDetailsIsCalledFrom(2);
    }

    @Override
    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public void setTitleField(String title) {

    }

    @Override
    public void setOverviewField(String overview) {

    }

    @Override
    public void setPosterImage(Image posterImage) {
        favoritePoster.setImage(posterImage);
    }

    @Override
    public void setReleaseField(String releaseDate) {

    }

    @Override
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
}
