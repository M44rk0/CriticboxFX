package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.List;

/**
 * Controlador para gerenciar a exibição dos favoritos e assistidos na interface do usuário.
 */
public class FavoritesController implements CommonController {

    @FXML
    private TabPane TabPaneFavorites;

    @FXML
    private FlowPane favoritesFlow;

    @FXML
    private FlowPane watchedFlow;

    private MainController mainController;

    /**
     * Exibe os títulos favoritos e assistidos na interface do usuário.
     */
    public void showFavorites() {
        try {
            mainController.getScrollPage().setVvalue(0);
            mainController.resetScrollBox();

            // Obtém a lista de favoritos do usuário atual em ordem de adição.
            List<Title> favorites = CurrentlyUser.getFavorites().reversed();
            for (Title title : favorites) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                mainController.setCommonFields(favoritePanelController, title);
                favoritesFlow.getChildren().add(favoritesPanel);
            }

            // Obtém a lista de títulos assistidos do usuário atual em ordem de adição.
            List<Title> watched = CurrentlyUser.getWatched().reversed();
            for (Title title : watched) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                mainController.setCommonFields(favoritePanelController, title);
                watchedFlow.getChildren().add(favoritesPanel);
            }

            // Ajusta a altura da página de rolagem se não houver favoritos ou assistidos, ou se ambos tiverem menos de 4 títulos.
            if (watched.isEmpty() && favorites.isEmpty() || (watched.size() < 4 && favorites.size() < 4)) {
                mainController.getScrollPage().setFitToHeight(true);
            }

            // Adiciona o TabPane de favoritos à página de rolagem principal
            mainController.getScrollBox().getChildren().add(TabPaneFavorites);

        } catch (IOException e) {
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
