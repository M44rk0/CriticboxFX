package com.m44rk0.criticboxfx.controller.favorites;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.CommonController;
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
        mainController.setDetailsIsCalledFrom(2);
    }

    /**
     * Define o título associado a este painel.
     *
     * @param title O título a ser definido.
     */
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

    /**
     * Define a imagem do pôster.
     *
     * @param posterImage A imagem do pôster a ser definida.
     */
    @Override
    public void setPosterImage(Image posterImage) {
        favoritePoster.setImage(posterImage);
    }

    @Override
    public void setReleaseField(String releaseDate) {
    }

    /**
     * Define o controlador principal.
     *
     * @param controller O controlador principal.
     */
    @Override
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
}
