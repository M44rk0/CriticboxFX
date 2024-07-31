package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.App;
import com.m44rk0.criticboxfx.controller.details.TitleDetailsController;
import com.m44rk0.criticboxfx.controller.details.TitleInfoController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesController;
import com.m44rk0.criticboxfx.controller.review.ReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewCreatorController;
import com.m44rk0.criticboxfx.model.user.CurrentlyUser;
import com.m44rk0.criticboxfx.service.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.m44rk0.criticboxfx.service.Search.titlePosterCache;

/**
 * Controlador principal da aplicação, responsável por gerenciar as interações com a interface do usuário.
 * Inclui métodos para realizar buscas, exibir resultados de busca, exibir favoritos, criar e editar avaliações,
 * e gerenciar a navegação entre diferentes telas da aplicação.
 */
public class MainController {

    @FXML
    private VBox scrollBox;

    @FXML
    private ScrollPane scrollPage;

    @FXML
    private TextField searchField;

    @FXML
    private Label currentlyUserName;

    private Stage stage;

    private ReviewController reviewController;

    private TitleInfoController titleInfoController;

    private ReviewCreatorController reviewCreatorController;

    private TitleDetailsController titleDetailsController;

    /**
     * Ação do botão de busca. Realiza a busca com o parâmetro fornecido no campo de busca.
     */
    @FXML
    private void searchButtonAction() {
        performSearch();
    }

    /**
     * Ação do botão de restauração de busca. Restaura os resultados de busca salvos.
     */
    @FXML
    private void restoreSearchButtonAction() {
        restoreSearchResults();
    }

    /**
     * Ação do botão de reviews. Exibe a tela com as reviews feitas pelo usuário.
     */
    @FXML
    private void reviewButtonAction() {
        showUserReviews();
    }

    /**
     * Ação do botão de favoritos. Exibe a tela com os favoritos do usuário.
     */
    @FXML
    private void favoritesButtonAction() {
        showFavorites();
    }

    /**
     * Ação do botão de retorno ao login. Fecha a janela atual e exibe a tela de login.
     */
    @FXML
    private void returnToLogin() {
        titleInfoController.clearResultNodes();
        stage.close();
        App.showLoginView(new Stage());
    }

    /**
     * Inicializa o controlador. Ao iniciar, exibe a página de favoritos se nenhum título foi buscado recentemente,
     * caso contrário, restaura os resultados da ultima busca feita pelo usuário atual.
     */
    @FXML
    public void initialize(){
        currentlyUserName.setText(CurrentlyUser.getUser().getName());
        restoreSearchResults();
    }

    /**
     * Realiza a busca com o parâmetro fornecido no campo de busca.
     */
    public void performSearch() {
        String searchParameter = searchField.getText();

        if (searchParameter.isEmpty() || searchParameter.isBlank()) {
            AlertMessage.showCommonAlert("Erro de Busca", "Digite um parâmetro de busca");
        } else {

            showLoadingScreen();

            CompletableFuture.supplyAsync(() -> {
                try {
                    TitleSearcher searcher = new TitleSearcher();
                    return searcher.search(searchParameter);
                } catch (TmdbException e) {
                    Platform.runLater(() -> AlertMessage.showCommonAlert("Erro de Busca", "Erro de Busca"));
                    return null;
                }
            }).thenAccept(searchResults -> Platform.runLater(() -> {
                if (searchResults != null) {
                    showSearchResults(searchResults);
                }
            }));
        }
    }

    /**
     * Método para carregar o Loading após uma busca.
     */
    private void showLoadingScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("details/searchLoading.fxml"));
            Pane pane = loader.load();
            scrollBox.getChildren().clear();
            scrollBox.getChildren().addAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            fadeIn.play();


        } catch (IOException e) {
            AlertMessage.showErrorAlert("UI Error", "Erro ao carregar o Splash");
        }
    }

    /**
     * Exibe os resultados da busca na tela.
     *
     * @param searchResults a lista de resultados de busca a ser exibida
     */
    public void showSearchResults(List<Title> searchResults) {
        try {
            if (titleInfoController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleInfo.fxml"));
                loader.load();
                titleInfoController = loader.getController();
                titleInfoController.setMainController(this);
            }
            titleInfoController.showSearchResults(searchResults);

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Results");
        }
    }

    /**
     * Restaura os resultados de busca salvos.
     */
    public void restoreSearchResults() {
        try {
            if (titleInfoController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleInfo.fxml"));
                loader.load();
                titleInfoController = loader.getController();
                titleInfoController.setMainController(this);
            }
            titleInfoController.restoreCachedSearchResults();

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Results");
        }
    }

    /**
     * Exibe a tela de reviews feitas pelo usuário.
     */
    public void showUserReviews() {
        try {
            if (reviewController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("review/userReview.fxml"));
                loader.load();
                reviewController = loader.getController();
                reviewController.setMainController(this);
            }
            reviewController.showUserReviews();

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro ao iniciar o FXML do Review");
        }
    }

    /**
     * Exibe a tela de favoritos e assistidos pelo usuário.
     */
    public void showFavorites() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("favorites/favoritesAndWatched.fxml"));
            loader.load();
            FavoritesController favoritesController = loader.getController();
            favoritesController.setMainController(this);
            favoritesController.showFavorites();

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Favorites");
        }
    }

    /**
     * Exibe a tela de criação de avaliações para o título fornecido.
     *
     * @param title o título para o qual a avaliação será criada
     */
    public void showCreateReview(Title title) {
        try {
            if (reviewCreatorController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("review/createReview.fxml"));
                loader.load();
                reviewCreatorController = loader.getController();
                reviewCreatorController.setMainController(this);
            }
            reviewCreatorController.showCreateReview(title);

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Favorites");
        }
    }

    /**
     * Exibe a tela de detalhes de um título.
     *
     * @param title o título para o qual os detalhes serão exibidos
     */
    public void showTitleDetails(Title title) {
        try {
            if (titleDetailsController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleDetails.fxml"));
                loader.load();
                titleDetailsController = loader.getController();
                titleDetailsController.setMainController(this);
            }
            titleDetailsController.showTitleDetails(title);

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Details");
        }
    }

    /**
     * Define os campos comuns em todos os controladores dado um título.
     *
     * @param controller o controlador onde os campos serão definidos
     * @param title o título que será usado para definir os campos
     */
    public void setCommonFields(CommonController controller, Title title) {
        controller.setMainController(this); // <--
        controller.setTitle(title);
        controller.setTitleField(title.getName());
        controller.setOverviewField(title.getOverview());
        controller.setPosterImage(titlePosterCache.get(title));
        controller.setReleaseField(formatDate(title.getReleaseDate()));
    }

    /**
     * Reseta a página principal, limpando os conteúdos e ajustando o ScrollPane.
     */
    public void resetScrollBox() {
        scrollPage.setFitToHeight(false);
        scrollBox.getChildren().clear();
    }

    /**
     * Define o estágio atual da aplicação.
     *
     * @param stage o estágio a ser definido
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Formata a data do formato "yyyy-MM-dd" para o formato "dd-MM-yyyy".
     *
     * @param data a data no formato "yyyy-MM-dd"
     * @return a data no formato "dd-MM-yyyy"
     */
    private String formatDate(String data) {
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(data, input);
        return date.format(output);
    }

    /**
     * Obtém o VBox que contém os resultados de busca.
     *
     * @return o VBox que contém os resultados de busca
     */
    public VBox getScrollBox() {
        return scrollBox;
    }

    /**
     * Obtém o ScrollPane que contém a página de resultados.
     *
     * @return o ScrollPane que contém a página de resultados
     */
    public ScrollPane getScrollPage() {
        return scrollPage;
    }

    /**
     * Obtém o campo de texto para pesquisa.
     *
     * @return o campo de texto para pesquisa
     */
    public TextField getSearchField() {
        return searchField;
    }

    /**
     * Obtém o controlador da tela de criação de reviews.
     * <p>
     * Este método carrega o FXML para a tela de criação de reviews se ainda não estiver carregado.
     * Se ocorrer um erro durante o carregamento do FXML, um alerta comum é exibido.
     * </p>
     *
     * @return O controlador da tela de criação de reviews, que é uma instância de {@link ReviewCreatorController}.
     */
    public ReviewCreatorController getReviewCreatorController() {
        try {
            if (reviewCreatorController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("review/createReview.fxml"));
                loader.load();
                reviewCreatorController = loader.getController();
                reviewCreatorController.setMainController(this);
            }

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML do Review Creator");
        }

        return reviewCreatorController;
    }

    /**
     * Obtém o controlador da tela de detalhes do título.
     * <p>
     * Este método carrega o FXML para a tela de detalhes do título se ainda não estiver carregado.
     * Se ocorrer um erro durante o carregamento do FXML, um alerta comum é exibido.
     * </p>
     *
     * @return O controlador da tela de detalhes do título, que é uma instância de {@link TitleDetailsController}.
     */
    public TitleDetailsController getTitleDetailsController() {
        try {
            if (titleDetailsController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleDetails.fxml"));
                loader.load();
                titleDetailsController = loader.getController();
                titleDetailsController.setMainController(this);
            }

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML do Title Details");
        }

        return titleDetailsController;
    }
}





