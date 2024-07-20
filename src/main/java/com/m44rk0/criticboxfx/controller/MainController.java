package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.App;
import com.m44rk0.criticboxfx.controller.details.TitleDetailsController;
import com.m44rk0.criticboxfx.controller.details.TitleInfoController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesController;
import com.m44rk0.criticboxfx.controller.review.ReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewCreatorController;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.m44rk0.criticboxfx.App.titleDAO;

public class MainController {

    @FXML
    private final ImageView critic = new ImageView(new File("src/main/resources/com/m44rk0/criticboxfx/images/Critic.png").toURI().toString());

    @FXML
    private VBox scrollBox;

    @FXML
    private ScrollPane scrollPage;

    @FXML
    private TextField searchField;

    private Stage stage;
    private ReviewController reviewController;
    private ReviewCreatorController reviewCreatorController;
    private TitleDetailsController titleDetailsController;
    private TitleInfoController titleInfoController;

    //guarda os resultados de pesquisa
    private final List<Node> searchResultNodes = new ArrayList<>();

    //guarda as imagens de todos os titulos buscados pra evitar carregar a mesma imagem várias vezes em outras telas
    public static final Map<Title, Image> titlePosterCache = new HashMap<>();

    //guarda as imagens das temporadas de um tvshow
    public static final Map<Season, Image> seasonPosterCache = new HashMap<>();

    //busca os ultimos resultados do usuário atual
    public List<Title> lastSearchedTitles = titleDAO.getLastSearchedTitles();

    //ajusta o botão de "return" a depender de onde ele foi clicado
    //1 == página de favoritos (return volta pra página de favoritos)
    //2 == página de resultados (return volta pra página de resultados)
    private Integer detailsIsCalledFrom = 0;

    //"avisa" que a tela de criação de review será para edição de uma review
    private Boolean isTheReviewEditable = false;

    //guarda o review que vai ser editado e que será aberto na página de criação de reviews
    private Review reviewToEdit;

    @FXML
    private void searchButtonAction() {
        performSearch();
    }

    @FXML
    private void restoreSearchButtonAction() {
        restoreSearchResults();
    }

    @FXML
    private void reviewButtonAction() {
        showUserReviews();
    }

    @FXML
    private void favoritesButtonAction() {
        showFavorites();
    }

    @FXML
    private void returnToLogin() {
        stage.close();
        App.showLoginView(new Stage());
    }

    @FXML
    public void initialize() {
        if (lastSearchedTitles.isEmpty()) {
            showFavorites();
        } else {
            restoreSearchResults();
        }
    }

    //realiza a busca
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

    //exibe os resultados da busca na tela
    public void showSearchResults(List<Title> searchResults) {
        try {
            if(titleInfoController == null){
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

    //restaura os resultados de busca salvos
    public void restoreSearchResults() {
        try {
            if(titleInfoController == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleInfo.fxml"));
                loader.load();
                titleInfoController = loader.getController();
                titleInfoController.setMainController(this);
            }
            titleInfoController.restoreSearchResults();

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Results");
        }
    }

    //exibe a tela de reviews feitas pelo usuário
    public void showUserReviews() {
            try {
                if(reviewController == null) {
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

    //exibe a tela de favoritos e assistidos pelo usuário
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

    //exibe a tela de criar reviews
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

    //exibe a tela de detalhes de um titulo
    public void showTitleDetails(Title title) {
        try {
            if(titleDetailsController == null) {
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

    //função pra setar os campos comuns em todos os controladores dado um título
    public void setCommonFields(CommonController controller, Title title) {
        controller.setMainController(this); // <--
        controller.setTitle(title);
        controller.setTitleField(title.getName());
        controller.setOverviewField(title.getOverview());
        controller.setPosterImage(titlePosterCache.get(title));
        controller.setReleaseField(formatDate(title.getReleaseDate()));
    }

    //função pra resetar a página principal
    public void resetScrollBox() {
        scrollPage.setFitToHeight(false);
        scrollBox.getChildren().clear();
    }

    public Integer getDetailsIsCalledFrom() {
        return detailsIsCalledFrom;
    }

    public void setDetailsIsCalledFrom(Integer detailsIsCalledFrom) {
        this.detailsIsCalledFrom = detailsIsCalledFrom;
    }

    public Boolean theReviewIsEditable() {
        return isTheReviewEditable;
    }

    public void setIfTheReviewIsEditable(Boolean theReviewEditable) {
        isTheReviewEditable = theReviewEditable;
    }

    public Review getReviewToEdit() {
        return reviewToEdit;
    }

    public void setReviewToEdit(Review reviewToEdit) {
        this.reviewToEdit = reviewToEdit;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String formatDate(String data) {
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(data, input);
        return date.format(output);
    }

    public VBox getScrollBox() {
        return scrollBox;
    }

    public ScrollPane getScrollPage() {
        return scrollPage;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public List<Node> getSearchResultNodes() {
        return searchResultNodes;
    }

    public List<Title> getLastSearchedTitles() {
        return lastSearchedTitles;
    }

}




