package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.ViewController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXML;

public class FavoritesPanelController implements CommonController {

    @FXML
    public ImageView favoritePoster;

    private Title title;
    private ViewController mainController;

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

    @FXML
    public void setPosterImage(Image poster){
        favoritePoster.setImage(poster);
    }

    @Override
    public void setTitleField(String title) {

    }

    @Override
    public void setOverviewField(String overview) {

    }

    @Override
    public void setReleaseField(String releaseDate) {

    }
}
