package com.m44rk0.criticboxfx;

import com.m44rk0.criticboxfx.dao.ReviewDAO;
import com.m44rk0.criticboxfx.dao.TitleDAO;
import com.m44rk0.criticboxfx.model.user.User;
import com.m44rk0.criticboxfx.dao.UserDAO;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage stage) throws IOException{

        user.setReviews(userDAO.getReviewsByUser(user));
        user.setFavorites(userDAO.getFavoritesByUser(user));
        user.setWatched(userDAO.getWatchedByUser(user));

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("controller/mainview/criticbox.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static UserDAO userDAO = new UserDAO();
    public static TitleDAO titleDAO = new TitleDAO();
    public static ReviewDAO reviewDAO = new ReviewDAO();

    public static User user = new User("Marco", "mmrk", "12345");

    public static void main(String[] args){
        launch();
    }
}