package com.m44rk0.criticboxfx.controller;

import com.m44rk0.criticboxfx.controller.details.TitleDetailsController;
import com.m44rk0.criticboxfx.controller.details.TitleInfoController;
import com.m44rk0.criticboxfx.controller.favorites.FavAndWatchController;
import com.m44rk0.criticboxfx.controller.favorites.FavoritesPanelController;
import com.m44rk0.criticboxfx.controller.review.CreateReviewController;
import com.m44rk0.criticboxfx.controller.review.ReviewController;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TvReview;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import com.m44rk0.criticboxfx.utils.CommonController;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

import java.util.List;
import java.io.File;

import static com.m44rk0.criticboxfx.App.user;

public class ViewController {

    @FXML
    private ImageView critic;

    @FXML
    private VBox scrollBox;

    @FXML
    private ScrollPane ScrollPage;

    @FXML
    private TextField searchField;

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

    private final String fillStar = "M17.562 21.56a1 1 0 0 1-.465-.116L12 18.764l-5.097 2.68a1 1 0 0 1-1.45-1.053l.973-5.676-4.124-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.549-5.164a1.04 1.04 0 0 1 1.793 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.705l-4.124 4.02.974 5.676a1 1 0 0 1-.985 1.169Z";
    private final List<Node> searchResultNodes = new ArrayList<>();
    private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/";
    private Integer detailsIsCalledFrom = 0;

    @FXML
    public void searchButtonResults(){
        restoreSearchResults();
    }

    @FXML
    void searchButton() throws TmdbException{
        performSearch();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("titleInfo.fxml"));
                Pane movieInfoPane = loader.load();
                TitleInfoController controller = loader.getController();
                ArrayList<Title> favorites = user.getFavorites();

                setCommonFields(controller, title);

                controller.setMainController(this);
                controller.setTitle(title);

                if(title instanceof TvShow){
                    controller.setSeasonField(String.valueOf(((TvShow) title).getSeasons().size()) + " Temporada(s)");
                    controller.setEpisodesField(String.valueOf(((TvShow) title).getTotalEpisodes()) + " Episódio(s)");
                    controller.turnVisible();
                }

                if(favorites.contains(title)){
                    controller.setFillFavoriteStar(fillStar);
                }

                if(user.getWatched().contains(title)){
                    String watchedEye = "M10.94,6.08A6.93,6.93,0,0,1,12,6c3.18,0,6.17,2.29,7.91,6a15.23,15.23,0,0,1-.9,1.64,1,1,0,0,0-.16.55,1,1,0,0,0,1.86.5,15.77,15.77,0,0,0,1.21-2.3,1,1,0,0,0,0-.79C19.9,6.91,16.1,4,12,4a7.77,7.77,0,0,0-1.4.12,1,1,0,1,0,.34,2ZM3.71,2.29A1,1,0,0,0,2.29,3.71L5.39,6.8a14.62,14.62,0,0,0-3.31,4.8,1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20a9.26,9.26,0,0,0,5.05-1.54l3.24,3.25a1,1,0,0,0,1.42,0,1,1,0,0,0,0-1.42Zm6.36,9.19,2.45,2.45A1.81,1.81,0,0,1,12,14a2,2,0,0,1-2-2A1.81,1.81,0,0,1,10.07,11.48ZM12,18c-3.18,0-6.17-2.29-7.9-6A12.09,12.09,0,0,1,6.8,8.21L8.57,10A4,4,0,0,0,14,15.43L15.59,17A7.24,7.24,0,0,1,12,18Z";
                    controller.setWatchedIcon(watchedEye);
                }

                scrollBox.getChildren().add(movieInfoPane);
                searchResultNodes.add(movieInfoPane);
            }
            catch (IOException e){
                AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
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

            controller.setTittleField(title.getName() + " (" + dateToYear(title.getReleaseDate()) + ")");
            controller.setOverviewField(title.getOverview());
            controller.setPosterImage(title.getPosterPath());

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
            ScrollPage.setFitToHeight(true);
            scrollBox.getChildren().add(reviewPane);

        } catch (IOException e) {
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
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

            if(user.getFavorites().contains(title)){
                controller.setFillFavoriteStar(fillStar);
            }

            scrollBox.getChildren().add(movieDetailsPane);
        }
        catch (IOException e){
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    public void showFavorites(){
        try {
            resetScrollBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("favoritesAndWatched.fxml"));
            TabPane fav = loader.load();
            FavAndWatchController controller = loader.getController();
            FlowPane favoritesFlow = controller.getFavoritesFlow();
            FlowPane watchedFlow = controller.getWatchedFlow();
            favoritesFlow.getChildren().clear();
            watchedFlow.getChildren().clear();

            List<Title> favorites = user.getFavorites().reversed();
            for (Title title : favorites){
                FXMLLoader fvLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fvLoader.load();
                FavoritesPanelController favoritePanelController = fvLoader.getController();
                favoritePanelController.setMainController(this);
                favoritePanelController.setPoster(title.getPosterPath());
                favoritePanelController.setTitle(title);
                favoritesPanel.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.WHITE, 2.0, 0.89, 0.0, 0.0));
                favoritesFlow.getChildren().add(favoritesPanel);
            }

            List<Title> watched = user.getWatched().reversed();
            for (Title title : watched){
                FXMLLoader fvLoader = new FXMLLoader(getClass().getResource("favoritesPanel.fxml"));
                Pane favoritesPanel = fvLoader.load();
                FavoritesPanelController favoritePanelController = fvLoader.getController();

                favoritePanelController.setMainController(this);
                favoritePanelController.setPoster(title.getPosterPath());
                favoritePanelController.setTitle(title);
                favoritesPanel.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.WHITE, 2.0, 0.89, 0.0, 0.0));
                watchedFlow.getChildren().add(favoritesPanel);
            }

            if(watched.isEmpty() && favorites.isEmpty() || (watched.size() < 4 && favorites.size() < 4)){
                ScrollPage.setFitToHeight(true);
            };

            scrollBox.getChildren().add(fav);
        }
        catch (IOException e){
            AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
        }
    }

    public void showUserReviews(){
            try {
                resetScrollBox();
                List<Review> userReviews = user.getReviews().reversed();

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
            }
            catch (IOException e){
                AlertMessage.showAlert("Erro de Inicialização", "Erro no carregamento do FXML");
            }
    }

    public void showMovie(String movieName){
        try {
            TitleSearcher searcher = new TitleSearcher();
            List<Title> movieResult = searcher.searchMovie(movieName);
            showSearchResults(movieResult);
        }
        catch (TmdbException e){
            AlertMessage.showAlert("Erro de Busca", "Erro de Busca");
        }
    }

    public void showTvShow(String tvName){
        try {
            TitleSearcher searcher = new TitleSearcher();
            List<Title> tvResult = searcher.searchTvShow(tvName);
            showSearchResults(tvResult);
        }
        catch (TmdbException e){
            AlertMessage.showAlert("Erro de Busca", "Erro de Busca");
        }
    }

    private void setCommonFields(CommonController controller, Title title){
        controller.setTittleField(title.getName());
        controller.setOverviewField(title.getOverview());
        controller.setPosterImage(title.getPosterPath());
        controller.setReleaseField(formatDate(title.getReleaseDate()));
    }

    private void resetScrollBox(){
        scrollBox.getChildren().clear();
        ScrollPage.setFitToHeight(false);
    }

    public void setDetailsIsCalledFrom(Integer detailsIsCalledFrom) {
        this.detailsIsCalledFrom = detailsIsCalledFrom;
    }

    public Integer getDetailsIsCalledFrom() {
        return detailsIsCalledFrom;
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
        String imagePath = "src/main/java/com/m44rk0/criticboxfx/images/CriticTest.png";
        Image image = new Image(new File(imagePath).toURI().toString());
        critic.setImage(image);
    }
}




