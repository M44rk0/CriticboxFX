package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.App;
import com.m44rk0.criticboxfx.controller.details.TitleDetailsController;
import com.m44rk0.criticboxfx.controller.details.TitleInfoController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesPanelController;
import com.m44rk0.criticboxfx.controller.mainview.ViewTabController;
import com.m44rk0.criticboxfx.controller.review.ReviewCreatorController;
import com.m44rk0.criticboxfx.controller.review.ReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewTabController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.review.EpisodeReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.TitleDetails;
import com.m44rk0.criticboxfx.model.title.Season;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.Icon;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.File;

import static com.m44rk0.criticboxfx.App.titleDAO;

public class MainController {

    @FXML
    private ImageView critic;

    @FXML
    private VBox scrollBox;

    @FXML
    private ScrollPane scrollPage;

    @FXML
    private TextField searchField;

    private Stage stage;

    //guarda os resultados de pesquisa
    private final List<Node> searchResultNodes = new ArrayList<>();

    //guarda as imagens de todos os titulos buscados pra evitar carregar a mesma imagem várias vezes em outras telas
    public static final Map<Title, Image> titlePosterCache = new HashMap<>();
    public static final Map<Season, Image> seasonPosterCache = new HashMap<>();
    public List<Title> lastSearchedTitles = titleDAO.getLastSearchedTitles();

    //gambiarra pra ajustar o botão de "return" a depender de onde ele foi clicado
    //1 == página de favoritos (return volta pra página de favoritos)
    //2 == página de resultados (return volta pra página de resultados)
    private Integer detailsIsCalledFrom = 0;

    //gambiarra pra "avisar" que a tela de criação de review será para edição de uma review
    //2 == editar uma review
    private Integer editReviewIsCalledFrom = 0;

    //guarda o review que vai ser editado e que será aberto na página de criação de reviews
    private Review reviewToEdit;

    @FXML
    private void searchButtonAction(){
        performSearch();
    }

    @FXML
    private void restoreSearchButtonAction(){
        restoreSearchResults();
    }

    @FXML
    private void reviewButtonAction(){
        showUserReviews();
    }

    @FXML
    private void favoritesButtonAction(){
        showFavorites();
    }

    @FXML
    private void returnToLogin(){
        stage.close();
        App.showLoginView(new Stage());
    }

    //realiza a busca
    public void performSearch(){
        try {
            List<Title> searchResults;
            TitleSearcher searcher = new TitleSearcher();
            String searchParameter = searchField.getText();

            if (searchParameter.isEmpty() || searchParameter.isBlank()){
                AlertMessage.showCommonAlert("Erro de Busca", "Digite um parâmetro de busca");
            } else {
                searchResults = searcher.search(searchParameter);
                showSearchResults(searchResults);
            }
        }
        catch (TmdbException e){
            AlertMessage.showCommonAlert("Erro de Busca", "Erro de Busca");
        }
    }

    //exibe os resultados da busca na tela
    public void showSearchResults(List<Title> searchResults){
        try{
            resetScrollBox();
            searchResultNodes.clear();

            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("details/resultsTab.fxml"));
            TabPane resultsTab = tabLoader.load();
            ViewTabController tabController = tabLoader.getController();
            tabController.setResultTabText("Resultados para: " + "\"" + searchField.getText() + "\"");
            FlowPane resultsPane = tabController.getResultsFlow();

            if(!lastSearchedTitles.isEmpty()) {
                titleDAO.clearLastResults();
            }

            for (Title title : searchResults) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleInfo.fxml"));
                Pane movieInfoPane = loader.load();
                TitleInfoController controller = loader.getController();

                setCommonFields(controller, title);

                if (title instanceof TvShow) {
                    controller.setSeasonField(((TvShow) title).getSeasons().size() + " Temporada(s)");
                    controller.setEpisodesField(((TvShow) title).getTotalEpisodes() + " Episódio(s)");
                    controller.turnVisible();
                }

                if (CurrentlyUser.getWatched().contains(title)) {
                    controller.setWatchedIcon(Icon.WATCHED.getPath());
                }

                resultsPane.getChildren().add(movieInfoPane);

                searchResultNodes.add(movieInfoPane);

                if (!titleDAO.getAllTitleIds().contains(title.getTitleId())){
                    titleDAO.addTitle(title);
                 }

                titleDAO.addTitleToLastResults(title);
            }

            scrollBox.getChildren().add(resultsTab);

            scrollPage.setFitToHeight(searchResults.size() == 1);

            if(searchResults.isEmpty()){
                tabController.setResultTabText("Nenhum resultado existente para: " + "\"" + searchField.getText() + "\"");
                scrollPage.setFitToHeight(true);
            }

    }
        catch (IOException | SQLException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Results");
        }
    }

    //restaura os resultados de busca salvos pra não ser preciso realizar outra busca ao voltar para a tela de resultados
    public void restoreSearchResults(){
        try {
            resetScrollBox();
            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("details/resultsTab.fxml"));
            TabPane resultsTab = tabLoader.load();
            ViewTabController tabController = tabLoader.getController();
            FlowPane resultsPane = tabController.getResultsFlow();

            if (searchResultNodes.isEmpty()) {
                for (Title title : lastSearchedTitles) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleInfo.fxml"));
                    Pane movieInfoPane = loader.load();
                    TitleInfoController controller = loader.getController();

                    setCommonFields(controller, title);

                    if (title instanceof TvShow) {
                        controller.setSeasonField(((TvShow) title).getSeasons().size() + " Temporada(s)");
                        controller.setEpisodesField(((TvShow) title).getTotalEpisodes() + " Episódio(s)");
                        controller.turnVisible();
                    }

                    if (CurrentlyUser.getWatched().contains(title)) {
                        controller.setWatchedIcon(Icon.WATCHED.getPath());
                    }

                    resultsPane.getChildren().add(movieInfoPane);
                    searchResultNodes.add(movieInfoPane);
                }
                scrollBox.getChildren().add(resultsTab);
            }
            else {
                resultsPane.getChildren().addAll(searchResultNodes);
                scrollBox.getChildren().add(resultsTab);
            }

            scrollPage.setFitToHeight(searchResultNodes.size() == 1 || searchResultNodes.isEmpty());

        }
        catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Results");
        }
    }

    //exibe a tela de reviews feitas pelo usuário
    public void showUserReviews() {
        try {
            resetScrollBox();
            scrollPage.setVvalue(0);

            List<Review> userReviews = CurrentlyUser.getReviews().reversed();
            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("review/reviewsTab.fxml"));
            TabPane reviewTab = tabLoader.load();
            ReviewTabController tabController = tabLoader.getController();

            FlowPane reviewFlow = tabController.getReviewsFlow();

            for (Review review : userReviews) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("review/userReview.fxml"));
                Pane reviewPane = loader.load();
                ReviewController controller = loader.getController();

                controller.setReview(review);
                controller.setMainController(this);
                controller.setTitleField(review.getTitle().getName());
                controller.setPosterImage(titlePosterCache.get(review.getTitle()));
                controller.setReviewField("\"" + review.getReviewText() + "\"");
                controller.setWatchedField(review.getReviewDate());
                controller.setSelectedRating(review.getReviewNote());

                if (review instanceof EpisodeReview episodeReview) {
                    int seasonNumber = episodeReview.getSeasonNumber();
                    String episodeName = episodeReview.getEpisodeName();
                    TvShow tvShow = (TvShow) review.getTitle();
                    Season season = tvShow.getSeasons().stream()
                            .filter(s -> s.getSeasonNumber() == seasonNumber)
                            .findFirst()
                            .orElse(null);

                    if (season != null && tvShow.getSeasons().size() > 1) {
                        if (!seasonPosterCache.containsKey(episodeReview.getSeason())) {
                            Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                                    season.getSeasonPosterPath(), 250, 350, false, false);
                            controller.setPosterImage(posterImage);
                            seasonPosterCache.put(episodeReview.getSeason(), posterImage);
                        }
                        else {
                            controller.setPosterImage(seasonPosterCache.get(episodeReview.getSeason()));
                        }
                    }
                    else {
                        controller.setPosterImage(titlePosterCache.get(tvShow));
                    }

                    controller.setInfoTVField(seasonNumber + "ª Temporada - " + episodeName);
                    controller.turnVisible();
                }

                reviewFlow.getChildren().add(reviewPane);
            }

            scrollBox.getChildren().add(reviewTab);
            scrollPage.setFitToHeight(userReviews.size() <= 1);

        }
        catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML do UserReview");
        }
    }

    //exibe a tela de favoritos e assistidos pelo usuário
    public void showFavorites() {
        try {
            scrollPage.setVvalue(0);
            resetScrollBox();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("favorites/favoritesAndWatched.fxml"));
            TabPane fav = loader.load();
            FavoritesController controller = loader.getController();
            FlowPane favoritesFlow = controller.getFavoritesFlow();
            FlowPane watchedFlow = controller.getWatchedFlow();
            favoritesFlow.getChildren().clear();
            watchedFlow.getChildren().clear();

            List<Title> favorites = CurrentlyUser.getFavorites().reversed();
            for (Title title : favorites) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favorites/favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                setCommonFields(favoritePanelController, title);
                favoritesFlow.getChildren().add(favoritesPanel);
            }

            List<Title> watched = CurrentlyUser.getWatched().reversed();
            for (Title title : watched) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favorites/favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                setCommonFields(favoritePanelController, title);
                watchedFlow.getChildren().add(favoritesPanel);
            }

            if (watched.isEmpty() && favorites.isEmpty() || (watched.size() < 4 && favorites.size() < 4)) {
                scrollPage.setFitToHeight(true);
            }

            scrollBox.getChildren().add(fav);
        }
        catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Favorites");
        }
    }

    //exibe a tela de criação de review de um título
    public void showCreateReview(Title title){
        try {
            scrollPage.setVvalue(0);
            scrollBox.getChildren().clear();
            scrollPage.setFitToHeight(true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("review/createReview.fxml"));
            Pane reviewPane = loader.load();
            ReviewCreatorController controller = loader.getController();

            setCommonFields(controller, title);
            controller.setTitleField(title.getName() + " (" + dateToYear(title.getReleaseDate()) + ")");

            //verifica se a review que está sendo exibida é uma review nova ou uma edição
            if(getEditReviewIsCalledFrom() == 2){
                controller.setCurrentRating(reviewToEdit.getReviewNote());
                controller.setText(reviewToEdit.getReviewText());
                controller.setSelectedRating(reviewToEdit.getReviewNote());
                scrollBox.getChildren().add(reviewPane);
            }
            else {
                if (title instanceof TvShow tvShow) {
                    controller.setSeasonBox(tvShow.getAllSeasons());
                    if (!tvShow.getSeasons().isEmpty()) {
                        controller.setEpisodeBox(tvShow.getSeasons().getFirst().getEpisodeList());
                    }
                    controller.turnVisible();
                }

                scrollBox.getChildren().add(reviewPane);
            }

        } catch (IOException e) {
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML do CreateReview");
        }
    }

    public void showTitleDetails(Title title){
        try {
            resetScrollBox();
            scrollPage.setVvalue(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("details/titleDetails.fxml"));
            Pane movieDetailsPane = loader.load();
            TitleDetailsController controller = loader.getController();

            setCommonFields(controller, title);
            TitleDetails details = new TitleDetails(title);

            controller.setDurationField(title.getDuration());
            controller.setGenreFlow(details.getGenres());
            controller.setDirectorFlow(details.getDirectors());
            controller.setCastFlow(details.getCast());
            controller.setWriterFlow(details.getWriters());
            controller.setProducerFlow(details.getProducers());
            controller.setArtDirectFlow(details.getArtDirection());
            controller.setSoundFlow(details.getSoundTeam());
            controller.setCameraFlow(details.getPhotographyTeam());
            controller.setVfxFlow(details.getVisualEffectsTeam());

            if(title instanceof TvShow){
                controller.hideDuration();
                controller.setSeasonBox(((TvShow) title).getAllSeasons());
                if (!((TvShow) title).getSeasons().isEmpty()) {
                    controller.setEpisodeBox(((TvShow) title).getSeasons().getFirst().getEpisodeList());
                }
                controller.turnVisible();
            }

            if(CurrentlyUser.getFavorites().contains(title)){
                controller.setFillFavoriteStar(Icon.FILLED_STAR.getPath());
            }

            scrollBox.getChildren().add(movieDetailsPane);
        }
        catch (IOException e){
            AlertMessage.showCommonAlert("Erro de Inicialização", "Erro no carregamento do FXML dos Details");
        }
    }

    //função pra setar os campos comuns em todos os controladores dado um título
    private void setCommonFields(CommonController controller, Title title){
        controller.setMainController(this);
        controller.setTitle(title);
        controller.setTitleField(title.getName());
        controller.setOverviewField(title.getOverview());
        controller.setPosterImage(titlePosterCache.get(title));
        controller.setReleaseField(formatDate(title.getReleaseDate()));
    }

    //função pra resetar a página principal
    private void resetScrollBox(){
        scrollPage.setFitToHeight(false);
        scrollBox.getChildren().clear();
    }

    public Integer getDetailsIsCalledFrom() {
        return detailsIsCalledFrom;
    }

    public void setDetailsIsCalledFrom(Integer detailsIsCalledFrom) {
        this.detailsIsCalledFrom = detailsIsCalledFrom;
    }

    public Integer getEditReviewIsCalledFrom() {
        return editReviewIsCalledFrom;
    }

    public void setEditReviewIsCalledFrom(Integer editReviewIsCalledFrom) {
        this.editReviewIsCalledFrom = editReviewIsCalledFrom;
    }

    public Review getReviewToEdit() {
        return reviewToEdit;
    }

    public void setReviewToEdit(Review reviewToEdit) {
        this.reviewToEdit = reviewToEdit;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    private String formatDate(String data){
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(data, input);
        return date.format(output);
    }

    private String dateToYear(String data){
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("yyyy");
        LocalDate date = LocalDate.parse(data, input);
        return date.format(output);
    }

    @FXML
    public void initialize(){

        critic.setImage(new Image(new File("src/main/resources/com/m44rk0/criticboxfx/images/Critic.png").toURI().toString()));

        if(lastSearchedTitles.isEmpty()){
            showFavorites();
        }
        else {
            restoreSearchResults();
        }

    }
}




