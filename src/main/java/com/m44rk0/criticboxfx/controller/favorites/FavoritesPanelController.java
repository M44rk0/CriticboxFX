package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.DetailsSource;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXML;

/**
 * Controlador para gerenciar o painel individual de cada pôster de favoritos e assistidos na interface do usuário.
 */
public class FavoritesPanelController implements CommonController {

    @FXML
    public ImageView favoritePoster;

    private Title title;
    private MainController mainController;

    /**
     * Exibe os detalhes do título selecionado.
     */
    @FXML
    public void showDetails() {
        mainController.showTitleDetails(title);
        mainController.getTitleDetailsController().setDetailsIsCalledFrom(DetailsSource.FAVORITES);
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
