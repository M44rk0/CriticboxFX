package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.App;
import com.m44rk0.criticboxfx.controller.details.TitleDetailsController;
import com.m44rk0.criticboxfx.controller.details.TitleInfoController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesController;
import com.m44rk0.criticboxfx.controller.review.ReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewCreatorController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.m44rk0.criticboxfx.App.titleDAO;

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
    private ReviewCreatorController reviewCreatorController;
    private TitleDetailsController titleDetailsController;
    private TitleInfoController titleInfoController;


    //TODO Arrumar as responsabilidades aqui, mó bagunça filho.

    // Guarda os resultados de pesquisa
    private final List<Node> searchResultNodes = new ArrayList<>();

    // Guarda as imagens de todos os títulos buscados para evitar carregar a mesma imagem várias vezes em outras telas
    public static final Map<Title, Image> titlePosterCache = new HashMap<>();

    // Guarda as imagens das temporadas de um TV show
    public static final Map<Season, Image> seasonPosterCache = new HashMap<>();

    // Busca os últimos resultados do usuário atual
    public List<Title> lastSearchedTitles = titleDAO.getLastSearchedTitles();

    // Ajusta o botão de "return" a depender de onde ele foi clicado
    // 1 == página de favoritos (return volta pra página de favoritos)
    // 2 == página de resultados (return volta pra página de resultados)
    private Integer detailsIsCalledFrom = 0;

    // "Avisa" que a tela de criação de review será para edição de uma review
    private Boolean isTheReviewEditable = false;

    // Guarda o review que vai ser editado e que será aberto na página de criação de reviews
    private Review reviewToEdit;

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
        stage.close();
        App.showLoginView(new Stage());
    }

    /**
     * Inicializa o controlador. Ao iniciar, exibe a página de favoritos se nenhum título foi buscado recentemente,
     * caso contrário, restaura os resultados da ultima busca feita pelo usuário atual.
     */
    @FXML
    public void initialize() {
        currentlyUserName.setText(CurrentlyUser.getUser().getName());
        if (lastSearchedTitles.isEmpty()) {
            showFavorites();
        } else {
            restoreSearchResults();
        }
    }

    /**
     * Realiza a busca com o parâmetro fornecido no campo de busca.
     */
    public void performSearch() {
        try {
            List<Title> searchResults;
            TitleSearcher searcher = new TitleSearcher();
            String searchParameter = searchField.getText();

            if (searchParameter.isEmpty() || searchParameter.isBlank()) {
                AlertMessage.showCommonAlert("Erro de Busca", "Digite um parâmetro de busca");
            } else {
                searchResults = searcher.search(searchParameter);
                showSearchResults(searchResults);
            }
        } catch (TmdbException e) {
            AlertMessage.showCommonAlert("Erro de Busca", "Erro de Busca");
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
     * Obtém o valor de {@code detailsIsCalledFrom}.
     *
     * @return o valor de {@code detailsIsCalledFrom}
     */
    public Integer getDetailsIsCalledFrom() {
        return detailsIsCalledFrom;
    }

    /**
     * Define o valor de {@code detailsIsCalledFrom}.
     *
     * @param detailsIsCalledFrom o valor a ser definido
     */
    public void setDetailsIsCalledFrom(Integer detailsIsCalledFrom) {
        this.detailsIsCalledFrom = detailsIsCalledFrom;
    }

    /**
     * Verifica se a review está em modo de edição.
     *
     * @return {@code true} se a review está em modo de edição, caso contrário {@code false}
     */
    public Boolean theReviewIsEditable() {
        return isTheReviewEditable;
    }

    /**
     * Define se a review está em modo de edição.
     *
     * @param theReviewEditable {@code true} se a review deve estar em modo de edição, caso contrário {@code false}
     */
    public void setIfTheReviewIsEditable(Boolean theReviewEditable) {
        isTheReviewEditable = theReviewEditable;
    }

    /**
     * Obtém o review que será editado.
     *
     * @return o review a ser editado
     */
    public Review getReviewToEdit() {
        return reviewToEdit;
    }

    /**
     * Define o review que será editado.
     *
     * @param reviewToEdit o review a ser editado
     */
    public void setReviewToEdit(Review reviewToEdit) {
        this.reviewToEdit = reviewToEdit;
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
     * Obtém a lista de nós de resultados de busca.
     *
     * @return a lista de nós de resultados de busca
     */
    public List<Node> getSearchResultNodes() {
        return searchResultNodes;
    }

    /**
     * Obtém a lista de títulos buscados recentemente.
     *
     * @return a lista de títulos buscados recentemente
     */
    public List<Title> getLastSearchedTitles() {
        return lastSearchedTitles;
    }
}





