package com.m44rk0.criticboxfx.utils;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Title;
import javafx.scene.image.Image;

/**
 * A interface {@code CommonController} define métodos comuns para controladores que lidam com a exibição e
 * manipulação de informações sobre títulos.
 *
 * <p>Esta interface pode ser implementada por controladores que precisam gerenciar a exibição de informações como
 * título, visão geral, imagem do pôster e data de lançamento, além de integrar-se com um controlador principal.</p>
 */
public interface CommonController {

    /**
     * Define o título a ser exibido.
     *
     * @param title O objeto {@link Title} a ser exibido.
     */
    void setTitle(Title title);

    /**
     * Define o texto do campo de título.
     *
     * @param title O texto do título a ser exibido no campo de entrada.
     */
    void setTitleField(String title);

    /**
     * Define o texto do campo de visão geral.
     *
     * @param overview O texto da visão geral a ser exibido no campo de entrada.
     */
    void setOverviewField(String overview);

    /**
     * Define a imagem do pôster a ser exibida.
     *
     * @param posterImage A imagem do pôster a ser exibida.
     */
    void setPosterImage(Image posterImage);

    /**
     * Define o texto do campo de data de lançamento.
     *
     * @param releaseDate A data de lançamento a ser exibida no campo de entrada.
     */
    void setReleaseField(String releaseDate);

    /**
     * Define o controlador principal associado.
     *
     * @param controller O objeto {@link MainController} que deve ser associado a este controlador.
     */
    void setMainController(MainController controller);

}

