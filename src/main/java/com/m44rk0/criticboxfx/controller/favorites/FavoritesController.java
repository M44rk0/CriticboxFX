package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

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

    private MainController mainController;

    public void showFavorites() {
        try {
            mainController.getScrollPage().setVvalue(0);
            mainController.resetScrollBox();

            List<Title> favorites = CurrentlyUser.getFavorites().reversed();
            for (Title title : favorites) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                mainController.setCommonFields(favoritePanelController, title);
                favoritesFlow.getChildren().add(favoritesPanel);
            }

            List<Title> watched = CurrentlyUser.getWatched().reversed();
            for (Title title : watched) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                mainController.setCommonFields(favoritePanelController, title);
                watchedFlow.getChildren().add(favoritesPanel);
            }

            if (watched.isEmpty() && favorites.isEmpty() || (watched.size() < 4 && favorites.size() < 4)) {
                mainController.getScrollPage().setFitToHeight(true);
            }

            mainController.getScrollBox().getChildren().add(TabPaneFavorites);
        }
        catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Favorites");
        }
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
        this.mainController = controller;
    }

}