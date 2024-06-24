package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;

public class FavAndWatchController implements CommonController {

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
    public void setTittleField(String title) {

    }

    @Override
    public void setOverviewField(String overview) {

    }

    @Override
    public void setPosterImage(String posterPath) {

    }

    @Override
    public void setReleaseField(String releaseDate) {

    }
}