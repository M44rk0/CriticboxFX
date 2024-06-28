package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.controller.details.TitleDetailsController;
import com.m44rk0.criticboxfx.controller.details.TitleInfoController;
import com.m44rk0.criticboxfx.controller.favorites.FavAndWatchController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesPanelController;
import com.m44rk0.criticboxfx.controller.mainview.TabViewController;
import com.m44rk0.criticboxfx.controller.review.CreateReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewTabPaneController;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TvReview;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import com.m44rk0.criticboxfx.utils.Icon;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.Node;
import javafx.fxml.FXML;
import java.io.File;


import static com.m44rk0.criticboxfx.App.user;

public class ViewController {

    @FXML
    private Button searchButton;

    @FXML
    private ImageView critic;

    @FXML
    private VBox scrollBox;

    @FXML
    private ScrollPane scrollPage;

    @FXML
    private TextField searchField;

    private final List<Node> searchResultNodes = new ArrayList<>();
    private final Map<Title, Image> imageCache = new HashMap<>();

    private Integer detailsIsCalledFrom = 0;
    private Integer editReviewIsCalledFrom = 0;
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

    public void performSearch(){
        try {
            List<Title> searchResults;
            TitleSearcher searcher = new TitleSearcher();
            String searchParameter = searchField.getText();

            if (searchParameter.isEmpty() || searchParameter.isBlank()){
                AlertMessage.showAlert("Erro de Busca", "Digite um parâmetro de busca");
            } else {
                searchResults = searcher.search(searchParameter);
                showSearchResults(searchResults);
            }
        }
        catch (TmdbException e){
            AlertMessage.showAlert("Erro de Busca", "Erro de Busca");
        }
    }

    public void showSearchResults(List<Title> searchResults){
        try{
            searchResultNodes.clear();
            resetScrollBox();
            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("resultsTab.fxml"));
            TabPane resultsTab = tabLoader.load();
            TabViewController tabController = tabLoader.getController();
            FlowPane resultsPane = tabController.getResultsFlow();

            for (Title title : searchResults) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("titleInfo.fxml"));
                Pane movieInfoPane = loader.load();
                TitleInfoController controller = loader.getController();

                setCommonFields(controller, title);
                controller.setMainController(this);
                controller.setTitle(title);

                if (title instanceof TvShow) {
                    controller.setSeasonField(((TvShow) title).getSeasons().size() + " Temporada(s)");
                    controller.setEpisodesField(((TvShow) title).getTotalEpisodes() + " Episódio(s)");
                    controller.turnVisible();
                }

                if (user.getWatched().contains(title)) {
                    controller.setWatchedIcon(Icon.WATCHED.getPath());
                }

                Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" + title.getPosterPath(), 250, 350, false, false);

                controller.setPosterImage(posterImage);
                imageCache.put(title, posterImage);
                resultsPane.getChildren().add(movieInfoPane);
                searchResultNodes.add(movieInfoPane);
            }

            scrollBox.getChildren().add(resultsTab);

            if (searchResults.size() == 1) {
                scrollPage.setFitToHeight(true);
            }
    }
        catch (IOException e) {
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    public void restoreSearchResults(){
        try {

            scrollBox.getChildren().clear();

            if (!searchResultNodes.isEmpty()) {
                scrollPage.setFitToHeight(false);
            }
            if (searchResultNodes.size() == 1) {
                scrollPage.setFitToHeight(true);
            }

            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("resultsTab.fxml"));
            TabPane resultsTab = tabLoader.load();
            TabViewController tabController = tabLoader.getController();
            FlowPane resultsPane = tabController.getResultsFlow();

            resultsPane.getChildren().addAll(searchResultNodes);
            scrollBox.getChildren().add(resultsTab);
        }
        catch (IOException e) {
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    public void showUserReviews() {
        try {
            scrollBox.getChildren().clear();
            List<Review> userReviews = user.getReviews().reversed();
            FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("reviewsTab.fxml"));
            TabPane reviewTab = tabLoader.load();
            ReviewTabPaneController tabController = tabLoader.getController();

            FlowPane reviewFlow = tabController.getReviewsFlow();

            for (Review review : userReviews) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("userReview.fxml"));
                        Pane reviewPane = loader.load();
                        ReviewController controller = loader.getController();

                        controller.setReviewField("\"" + review.getReviewText() + "\"");
                        controller.setWatchedField(review.getReviewDate());
                        controller.setPosterImage(imageCache.get(review.getTitle()));
                        controller.setMainController(this);
                        controller.setReview(review);
                        controller.setTittleField(review.getTitle().getName());
                        controller.setSelectedRating(review.getReviewNote());

                        if (review instanceof TvReview) {
                            controller.setInfoTVField(((TvReview) review).getSeasonNumber() + "ª Temporada - " + ((TvReview) review).getEpisodeName());
                            controller.turnVisible();
                        }

                        reviewFlow.getChildren().add(reviewPane);
                }

            scrollBox.getChildren().add(reviewTab);

            if (!userReviews.isEmpty()) {
                scrollPage.setFitToHeight(false);
            }

            if (userReviews.size() == 1 || userReviews.isEmpty()) {
                scrollPage.setFitToHeight(true);
            }

        } catch (IOException e) {
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    public void showFavorites() {
        try {
            scrollPage.setVvalue(0);
            resetScrollBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("favoritesAndWatched.fxml"));
            TabPane fav = loader.load();
            FavAndWatchController controller = loader.getController();
            FlowPane favoritesFlow = controller.getFavoritesFlow();
            FlowPane watchedFlow = controller.getWatchedFlow();
            favoritesFlow.getChildren().clear();
            watchedFlow.getChildren().clear();

            List<Title> favorites = user.getFavorites().reversed();
            for (Title title : favorites) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                favoritePanelController.setMainController(this);
                favoritePanelController.setPosterImage(imageCache.get(title));
                favoritePanelController.setTitle(title);
                favoritesFlow.getChildren().add(favoritesPanel);
            }

            List<Title> watched = user.getWatched().reversed();
            for (Title title : watched) {
                FXMLLoader fpLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fpLoader.load();
                FavoritesPanelController favoritePanelController = fpLoader.getController();
                favoritePanelController.setMainController(this);
                favoritePanelController.setPosterImage(imageCache.get(title));
                favoritePanelController.setTitle(title);
                watchedFlow.getChildren().add(favoritesPanel);
            }

            if (watched.isEmpty() && favorites.isEmpty() || (watched.size() < 4 && favorites.size() < 4)) {
                scrollPage.setFitToHeight(true);
            }

            scrollBox.getChildren().add(fav);
        }
        catch (IOException e) {
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    public void showCreateReview(Title title){
        try {
            scrollPage.setVvalue(0);
            scrollPage.setFitToHeight(true);
            scrollBox.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("createReview.fxml"));
            Pane reviewPane = loader.load();
            CreateReviewController controller = loader.getController();

            controller.setTittleField(title.getName() + " (" + dateToYear(title.getReleaseDate()) + ")");
            controller.setOverviewField(title.getOverview());
            controller.setPosterImage(imageCache.get(title));

            controller.setTitle(title);
            controller.setMainController(this);

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
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    public void showTitleDetails(Title title){
        try {
            resetScrollBox();
            scrollPage.setVvalue(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("titleDetails.fxml"));
            Pane movieDetailsPane = loader.load();
            TitleDetailsController controller = loader.getController();

            setCommonFields(controller, title);

            controller.setTitle(title);
            controller.setMainController(this);
            controller.setDurationField(title.getDuration());
            controller.setGenreFlow(title.getGenres());
            controller.setDirectorFlow(title.getDirectors());
            controller.setCastFlow(title.getCast());
            controller.setWriterFlow(title.getWriters());
            controller.setProducerFlow(title.getProducers());
            controller.setArtDirectFlow(title.getArtDirection());
            controller.setSoundFlow(title.getSoundTeam());
            controller.setCameraFlow(title.getPhotographyTeam());
            controller.setVfxFlow(title.getVisualEffectsTeam());

            if(title instanceof TvShow){
                controller.hideDuration();
            }

            if(user.getFavorites().contains(title)){
                controller.setFillFavoriteStar(Icon.FILLED_STAR.getPath());
            }

            scrollBox.getChildren().add(movieDetailsPane);
        }
        catch (IOException e){
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    private void setCommonFields(CommonController controller, Title title){
        controller.setTittleField(title.getName());
        controller.setOverviewField(title.getOverview());
        controller.setPosterImage(imageCache.get(title));
        controller.setReleaseField(formatDate(title.getReleaseDate()));
    }

    private void resetScrollBox(){
        scrollBox.getChildren().clear();
        scrollPage.setFitToHeight(false);
    }

    public void setDetailsIsCalledFrom(Integer detailsIsCalledFrom) {
        this.detailsIsCalledFrom = detailsIsCalledFrom;
    }

    public Integer getDetailsIsCalledFrom() {
        return detailsIsCalledFrom;
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

    public static String formatDate(String dataString){
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dataString, input);
        return date.format(output);
    }

    public static String dateToYear(String dataString){
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy");
        LocalDate data = LocalDate.parse(dataString, formatoEntrada);
        return data.format(formatoSaida);
    }

    @FXML
    public void initialize(){
        String imagePath = "src/main/java/com/m44rk0/criticboxfx/images/Critic.png";
        Image image = new Image(new File(imagePath).toURI().toString());
        critic.setImage(image);

        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchButtonAction();
            }
        });

    }
}




