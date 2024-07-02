package com.m44rk0.criticboxfx;

import com.m44rk0.criticboxfx.model.review.FilmReview;
import com.m44rk0.criticboxfx.model.review.Review;
import com.m44rk0.criticboxfx.model.review.TvReview;
import com.m44rk0.criticboxfx.model.search.Search;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.user.User;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Date;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage stage) throws IOException, TmdbException {

        TitleSearcher titleSearcher = new TitleSearcher();

        user.addFavorite(titleSearcher.searchMovieById(24428));  // Vingadores
        user.addFavorite(titleSearcher.searchMovieById(99861));  // Vingadores: Guerra de Ultron
        user.addFavorite(titleSearcher.searchMovieById(299536)); // Vingadores Guerra Infinita
        user.addFavorite(titleSearcher.searchTvShowById(1399));  // Game of Thrones
        user.addFavorite(titleSearcher.searchTvShowById(94997)); // Casa do Dragão

        user.addReview(new FilmReview(titleSearcher.searchMovieById(299536), 4, new Date(), "Uma Guerra Infinita?"));
        user.addReview(new FilmReview(titleSearcher.searchMovieById(24428), 5, new Date(), "Primeiro Vingadores"));
        user.addReview(new TvReview(titleSearcher.searchTvShowById(1399), 4, new Date(), "Boa", 6, "Batalha dos Bastardos"));

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("controller/criticbox.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static User user = new User("Marco", "mmrk", "12345");

    public static void main(String[] args){
        launch();
    }
}