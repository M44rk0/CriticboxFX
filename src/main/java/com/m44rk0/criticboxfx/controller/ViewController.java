package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.model.*;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovieLists;
import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.fxml.FXML;
import java.util.List;
import java.io.File;

import static com.m44rk0.criticboxfx.App.user;

public class ViewController {

    @FXML
    private ImageView critic;

    @FXML
    private ScrollPane ScrollPage;

    @FXML
    private VBox scrollBox;

    @FXML
    private Button favoriteButton;

    @FXML
    private Button friendsButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button reviewButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    private final List<Node> searchResultNodes = new ArrayList<>();
    private final List<Node> favoritesNodes = new ArrayList<>();
    private final double scrollPageCurrentValue = 0.0;

    @FXML
    public void searchButtonResults() {
        restoreSearchResults();
    }

    @FXML
    void searchButton() throws TmdbException {
        performSearch();
    }

    public void performSearch() throws TmdbException {

        List<Title> searchResults;
        MovieSearcher searcher = new MovieSearcher();
        String searchParameter = searchField.getText();

        if(searchParameter.isEmpty() || searchParameter.isBlank()){

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Erro de Busca");
            alert.setContentText("Digite um parametro para a busca seu animal!");
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.showAndWait();
        }
        else {
            searchResults = searcher.search(searchParameter);
            showSearchResults(searchResults);
        }
    }

    public void restoreSearchResults() {

        scrollBox.getChildren().clear();

        if(!searchResultNodes.isEmpty()) {
            ScrollPage.setFitToHeight(false);
        }
        if(searchResultNodes.size() == 1 ){
            ScrollPage.setFitToHeight(true);
        }

        scrollBox.getChildren().addAll(searchResultNodes);
    }

    public void showSearchResults(List<Title> searchResults) {

        scrollBox.getChildren().clear();
        searchResultNodes.clear();
        ScrollPage.setFitToHeight(false);

        for(Title title : searchResults) {
            try {
                String fillStar = "M17.562 21.56a1 1 0 0 1-.465-.116L12 18.764l-5.097 2.68a1 1 0 0 1-1.45-1.053l.973-5.676-4.124-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.549-5.164a1.04 1.04 0 0 1 1.793 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.705l-4.124 4.02.974 5.676a1 1 0 0 1-.985 1.169Z";
                FXMLLoader loader = new FXMLLoader(getClass().getResource("movieInfo.fxml"));
                Pane movieInfoPane = loader.load();
                MovieInfoController controller = loader.getController();
                ArrayList<String> favoriteTittles = user.getFavoritesNames();

                controller.setMainController(this);
                controller.setTitle(title);

                controller.setTittleField(title.getName());
                controller.setOverviewField(title.getOverview());
                controller.setPosterImage(title.getPosterPath());
                controller.setReleaseField(title.getReleaseDate());

                if(title instanceof TvShow){
                    controller.setSeasonField(String.valueOf(((TvShow) title).getSeasons().size()) + " Temporada(s)");
                    controller.setEpisodesField(String.valueOf(((TvShow) title).getTotalEpisodes()) + " Episódio(s)");
                    controller.turnVisible();
                }

                if(favoriteTittles.contains(title.getName())){
                    controller.setFillFavoriteStar(fillStar);
                }

                scrollBox.getChildren().add(movieInfoPane);
                searchResultNodes.add(movieInfoPane);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(searchResults.size() == 1){
            ScrollPage.setFitToHeight(true);
        }
    }

    public void showTitleReview(Title title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userReview.fxml"));
            Pane reviewPane = loader.load();
            UserReviewController controller = loader.getController();

            controller.setPosterImage(title.getPosterPath());
            controller.setOverviewField(title.getOverview());
            controller.setTittleField(title.getName());
            controller.setReleaseField(title.getReleaseDate());
            controller.setViewController(this);
            controller.setTitle(title);

            if (title instanceof TvShow tvShow) {
                controller.setSeasonBox(tvShow.getAllSeasons());
                if (!tvShow.getSeasons().isEmpty()) {
                    controller.setEpisodeBox(tvShow.getSeasons().getFirst().getEpisodeList());
                }
                controller.turnVisible();
            }

            ScrollPage.setFitToHeight(false);
            ScrollPage.setVvalue(0);

            scrollBox.getChildren().clear();
            scrollBox.getChildren().add(reviewPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showMovieDetails(Title title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("movieDetails.fxml"));
            Pane movieDetailsPane = loader.load();
            MovieDetailsController controller = loader.getController();

            controller.setMainController(this);
            controller.setTittleField(title.getName());
            controller.setOverviewField(title.getOverview());
            controller.setPosterImage(title.getPosterPath());
            controller.setDurationField(title.getDuration());
            controller.setReleaseField(title.getReleaseDate());
            controller.setGenreFlow(title.getGenres());
            controller.setDirectorFlow(title.getDirectors());
            controller.setCastFlow(title.getCast());
            controller.setWriterFlow(title.getWriters());
            controller.setProducerFlow(title.getProducers());
            controller.setArtDirectFlow(title.getArtDirection());
            controller.setSoundFlow(title.getSoundTeam());
            controller.setCameraFlow(title.getPhotographyTeam());
            controller.setVfxFlow(title.getVisualEffectsTeam());

            ScrollPage.setFitToHeight(false);
            ScrollPage.setVvalue(0);

            scrollBox.getChildren().clear();
            scrollBox.getChildren().add(movieDetailsPane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFavoriteDetails(Title title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("favoriteDetails.fxml"));
            Pane movieDetailsPane = loader.load();
            FavoriteDetailsController controller = loader.getController();

            controller.setMainController(this);
            controller.setTittleField(title.getName());
            controller.setOverviewField(title.getOverview());
            controller.setPosterImage(title.getPosterPath());
            controller.setDurationField(title.getDuration());
            controller.setReleaseField(title.getReleaseDate());
            controller.setGenreFlow(title.getGenres());
            controller.setDirectorFlow(title.getDirectors());
            controller.setCastFlow(title.getCast());
            controller.setWriterFlow(title.getWriters());
            controller.setProducerFlow(title.getProducers());
            controller.setArtDirectFlow(title.getArtDirection());
            controller.setSoundFlow(title.getSoundTeam());
            controller.setCameraFlow(title.getPhotographyTeam());
            controller.setVfxFlow(title.getVisualEffectsTeam());

            ScrollPage.setFitToHeight(false);
            scrollBox.getChildren().clear();
            scrollBox.getChildren().add(movieDetailsPane);

            ScrollPage.setVvalue(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFavoritesUI() {
        showFavorites();
    }

    public void restoreFavorites() {
        scrollBox.getChildren().clear();
        scrollBox.getChildren().addAll(favoritesNodes);
    }

    public void showFavorites() {
        try {
            List<Title> favorites = user.getFavorites();

            scrollBox.getChildren().clear();
            ScrollPage.setFitToHeight(false);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("favorites.fxml"));
            FlowPane favoritePane = loader.load();
            favoritePane.setPrefHeight(745);

            for (Title title : favorites) {

                FXMLLoader fvLoader = new FXMLLoader(getClass().getResource("favoritePanel.fxml"));
                Pane favoritePanel = fvLoader.load();
                FavoritePanelController controller = fvLoader.getController();
                Image image = new Image("https://image.tmdb.org/t/p/original/" + title.getPosterPath(), 250, 350, false, false);

                controller.setMainController(this);
                controller.setPoster(image);
                controller.setTitle(title);
                favoritePane.getChildren().add(favoritePanel);
            }

            if (favorites.size() < 4) {
                ScrollPage.setFitToHeight(true);
            }

            scrollBox.getChildren().add(favoritePane);
            favoritesNodes.add(favoritePane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMovie(String movieName) throws TmdbException {
        MovieSearcher searcher = new MovieSearcher();
        List<Title> movieResult = searcher.searchMovie(movieName);
        showSearchResults(movieResult);
    }

    public void showTvShow(String tvName) throws TmdbException {
        MovieSearcher searcher = new MovieSearcher();
        List<Title> tvResult = searcher.searchTvShow(tvName);
        showSearchResults(tvResult);
    }

    public void showHomePage(){
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            AnchorPane homePane = loader.load();
            HomePageController controller = loader.getController();

            TmdbApi apiKey = new TmdbApi(new MovieSearcher().getAPI_KEY());
            TmdbMovieLists ml = apiKey.getMovieLists();
            var upcoming = ml.getNowPlaying("pt-BR", 1, "BR").getResults();
            var popular = ml.getPopular("pt-BR", 1, "BR").getResults();
            var toprated = ml.getTopRated("pt-BR", 1, "BR").getResults();

            for(Movie movie : upcoming){

                FXMLLoader hpLoader = new FXMLLoader(getClass().getResource("homePanel.fxml"));
                ImageView onCinema = hpLoader.load();
                HomePanelController fvController = hpLoader.getController();

                Image image = new Image("https://image.tmdb.org/t/p/original/" + movie.getPosterPath(), 250, 350, false, false);
                fvController.setHomePagePoster(image);

                controller.getOnCinemaBox().getChildren().add(onCinema);

            }

            for(Movie movie : popular){

                FXMLLoader hpLoader = new FXMLLoader(getClass().getResource("homePanel.fxml"));
                ImageView onCinema = hpLoader.load();
                HomePanelController fvController = hpLoader.getController();

                Image image = new Image("https://image.tmdb.org/t/p/original/" + movie.getPosterPath(), 250, 350, false, false);
                fvController.setHomePagePoster(image);

                controller.getMostPopularBox().getChildren().add(onCinema);

            }

            for(Movie movie : toprated){

                FXMLLoader hpLoader = new FXMLLoader(getClass().getResource("homePanel.fxml"));
                ImageView onCinema = hpLoader.load();
                HomePanelController fvController = hpLoader.getController();

                Image image = new Image("https://image.tmdb.org/t/p/original/" + movie.getPosterPath(), 250, 350, false, false);
                fvController.setHomePagePoster(image);
                controller.getMostReviewBox().getChildren().add(onCinema);

            }

            scrollBox.getChildren().add(homePane);


        } catch (IOException | TmdbException e) {
            e.printStackTrace();
        }

    }

    public void showUserReviews() {
        try {
            ArrayList<Review> userReviews = user.getReviews();

            scrollBox.getChildren().clear();
            ScrollPage.setFitToHeight(false);

            for (Review review : userReviews) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("review.fxml"));
                Pane reviewPane = loader.load();
                ReviewController controller = loader.getController();
                controller.setReviewField("\"" + review.getReviewText() + "\"");
                controller.setWatchedField(review.getReviewDate());
                controller.setPosterImage(review.getTitle().getPosterPath());
                controller.setTittleField(review.getTitle().getName());
                controller.setStarColors(review.getReviewNote());

                if (review instanceof TvReview) {
                    controller.setInfoTVField(((TvReview) review).getSeasonNumber() + "ª Temporada - " + ((TvReview) review).getEpisodeName());
                    controller.turnVisible();
                }

                scrollBox.getChildren().add(reviewPane);

                if(!userReviews.isEmpty()) {
                    ScrollPage.setFitToHeight(false);
                }
                if(userReviews.size() == 1 ){
                    ScrollPage.setFitToHeight(true);
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize(){
        String imagePath = "src/main/java/com/m44rk0/criticboxfx/images/CriticTest2.png";
        Image image = new Image(new File(imagePath).toURI().toString());
        critic.setImage(image);
    }
}




