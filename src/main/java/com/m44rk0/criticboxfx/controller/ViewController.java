package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.controller.details.TitleDetailsController;
import com.m44rk0.criticboxfx.controller.details.TitleInfoController;
import com.m44rk0.criticboxfx.controller.favorites.FavoriteDetailsController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesPanelController;
import com.m44rk0.criticboxfx.controller.review.CreateReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewController;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TvReview;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonFields;
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
    private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/";

    @FXML
    public void searchButtonResults(){
        restoreSearchResults();
    }

    @FXML
    void searchButton() throws TmdbException{
        performSearch();
    }

    public void performSearch() throws TmdbException{

        List<Title> searchResults;
        TitleSearcher searcher = new TitleSearcher();
        String searchParameter = searchField.getText();

        if(searchParameter.isEmpty() || searchParameter.isBlank()){
            AlertMessage.showAlert("Erro de Busca", "Digite um parâmetro de busca");
        }
        else {
            searchResults = searcher.search(searchParameter);
            showSearchResults(searchResults);
        }
    }

    public void restoreSearchResults(){

        scrollBox.getChildren().clear();

        if(!searchResultNodes.isEmpty()){
            ScrollPage.setFitToHeight(false);
        }
        if(searchResultNodes.size() == 1 ){
            ScrollPage.setFitToHeight(true);
        }

        scrollBox.getChildren().addAll(searchResultNodes);
    }

    public void showSearchResults(List<Title> searchResults){

        searchResultNodes.clear();
        resetScrollBox();

        for(Title title : searchResults){
            try {
                String fillStar = "M17.562 21.56a1 1 0 0 1-.465-.116L12 18.764l-5.097 2.68a1 1 0 0 1-1.45-1.053l.973-5.676-4.124-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.549-5.164a1.04 1.04 0 0 1 1.793 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.705l-4.124 4.02.974 5.676a1 1 0 0 1-.985 1.169Z";
                FXMLLoader loader = new FXMLLoader(getClass().getResource("titleInfo.fxml"));
                Pane movieInfoPane = loader.load();
                TitleInfoController controller = loader.getController();
                ArrayList<String> favoriteTittles = user.getFavoritesNames();

                setCommonFields(controller, title);

                controller.setMainController(this);
                controller.setTitle(title);

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
            catch (IOException e){
                e.printStackTrace();
            }
        }

        if(searchResults.size() == 1){
            ScrollPage.setFitToHeight(true);
        }
    }

    public void showCreateReview(Title title){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createReview.fxml"));
            Pane reviewPane = loader.load();
            CreateReviewController controller = loader.getController();

            setCommonFields(controller, title);

            controller.setTitle(title);
            controller.setMainController(this);


            if (title instanceof TvShow tvShow){
                controller.setSeasonBox(tvShow.getAllSeasons());
                if (!tvShow.getSeasons().isEmpty()) {
                    controller.setEpisodeBox(tvShow.getSeasons().getFirst().getEpisodeList());
                }
                controller.turnVisible();
            }

            resetScrollBox();
            ScrollPage.setVvalue(0);
            scrollBox.getChildren().add(reviewPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showTitleDetails(Title title){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("titleDetails.fxml"));
            Pane movieDetailsPane = loader.load();
            TitleDetailsController controller = loader.getController();
            resetScrollBox();
            ScrollPage.setVvalue(0);

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

            scrollBox.getChildren().add(movieDetailsPane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showFavoriteDetails(Title title){
        try {
            resetScrollBox();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("favoriteDetails.fxml"));
            Pane movieDetailsPane = loader.load();
            FavoriteDetailsController controller = loader.getController();

            setCommonFields(controller, title);

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

            scrollBox.getChildren().add(movieDetailsPane);

            ScrollPage.setVvalue(0);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showFavorites(){
            try {

                resetScrollBox();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("favorites.fxml"));
                FavoritesController controller = loader.getController();
                FlowPane favoritesPane = loader.load();

                List<Title> favorites = user.getFavorites();

                for (Title title : favorites) {
                    FXMLLoader fvLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                    Pane favoritesPanel = fvLoader.load();
                    FavoritesPanelController favoritePanelController = fvLoader.getController();

                    favoritePanelController.setMainController(this);
                    favoritePanelController.setPoster(title.getPosterPath());
                    favoritePanelController.setTitle(title);

                    favoritesPane.getChildren().add(favoritesPanel);
                }

                if (favorites.size() < 4) {
                    ScrollPage.setFitToHeight(true);
                }

                scrollBox.getChildren().add(favoritesPane);

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void showMovie(String movieName) throws TmdbException{
        TitleSearcher searcher = new TitleSearcher();
        List<Title> movieResult = searcher.searchMovie(movieName);
        showSearchResults(movieResult);
    }

    public void showTvShow(String tvName) throws TmdbException{
        TitleSearcher searcher = new TitleSearcher();
        List<Title> tvResult = searcher.searchTvShow(tvName);
        showSearchResults(tvResult);
    }

    public void showUserReviews(){
            try {
                ArrayList<Review> userReviews = user.getReviews();

                for (Review review : userReviews){

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userReview.fxml"));
                    Pane reviewPane = loader.load();
                    ReviewController controller = loader.getController();

                    controller.setReviewField("\"" + review.getReviewText() + "\"");
                    controller.setWatchedField(review.getReviewDate());
                    controller.setPosterImage(review.getTitle().getPosterPath());
                    controller.setTittleField(review.getTitle().getName());
                    controller.setStarColors(review.getReviewNote());

                    if (review instanceof TvReview){
                        controller.setInfoTVField(((TvReview) review).getSeasonNumber() + "ª Temporada - " + ((TvReview) review).getEpisodeName());
                        controller.turnVisible();
                    }

                    scrollBox.getChildren().add(reviewPane);

                    if(!userReviews.isEmpty()){
                        ScrollPage.setFitToHeight(false);
                    }
                    if(userReviews.size() == 1 ){
                        ScrollPage.setFitToHeight(true);
                    }

                }
                resetScrollBox();
            }
            catch (IOException e){
                e.printStackTrace();
            }
    }

    private void setCommonFields(CommonFields controller, Title title){
        controller.setTittleField(title.getName());
        controller.setOverviewField(title.getOverview());
        controller.setPosterImage(title.getPosterPath());
        controller.setReleaseField(title.getReleaseDate());
    }

    private void resetScrollBox(){
        scrollBox.getChildren().clear();
        ScrollPage.setFitToHeight(false);
    }

    @FXML
    public void initialize(){
        String imagePath = "src/main/java/com/m44rk0/criticboxfx/images/CriticTest2.png";
        Image image = new Image(new File(imagePath).toURI().toString());
        critic.setImage(image);
    }
}




