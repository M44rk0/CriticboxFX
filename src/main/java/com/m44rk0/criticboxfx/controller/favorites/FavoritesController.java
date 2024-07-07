package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;

public class FavoritesController implements CommonController {

    @FXML
    private TabPane TabPaneFavorites;

    @FXML
    private FlowPane favoritesFlow;

    @FXML
    private FlowPane watchedFlow;

    @FXML
    private Tab favoritesTab;

    @FXML
    private Tab watchedTab;

    public TabPane getTabPaneFavorites() {
        return TabPaneFavorites;
    }

    public FlowPane getFavoritesFlow() {
        return favoritesFlow;
    }

    public FlowPane getWatchedFlow() {
        return watchedFlow;
    }

    public Tab getFavoritesTab() {
        return favoritesTab;
    }

    public Tab getWatchedTab() {
        return watchedTab;
    }

    @Override
    public void setTitle(Title title) {

    }

    @Override
    public void setTitleField(String title) {

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

    @Override
    public void setMainController(MainController controller) {

    }


}