package com.m44rk0.criticboxfx;

import com.m44rk0.criticboxfx.model.review.ReviewDAO;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.model.title.TitleDAO;
import com.m44rk0.criticboxfx.model.user.User;
import com.m44rk0.criticboxfx.model.user.UserDAO;
import info.movito.themoviedbapi.tools.TmdbException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage stage) throws IOException, SQLException, TmdbException {

        TitleSearcher titleSearcher = new TitleSearcher();

        userDAO.addUser(userMarco);
        userDAO.addUser(userBryan);
        userMarco.setReviews(userDAO.getReviewsByUser(userMarco));
        userMarco.setFavorites(userDAO.getFavoritesByUser(userMarco));
        userMarco.setWatched(userDAO.getWatchedByUser(userMarco));

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("controller/criticbox.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static UserDAO userDAO = new UserDAO();
    public static TitleDAO titleDAO = new TitleDAO();
    public static ReviewDAO reviewDAO = new ReviewDAO();

    public static User userMarco = new User("Marco", "mmrk", "12345");
    public static User userBryan = new User("Bryan", "bryan", "12345");

    public static void main(String[] args){
        launch();
    }
}