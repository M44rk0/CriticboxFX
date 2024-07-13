package com.m44rk0.criticboxfx;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.controller.login.LoginController;
import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.dao.ReviewDAO;
import com.m44rk0.criticboxfx.dao.TitleDAO;
import com.m44rk0.criticboxfx.dao.UserDAO;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static UserDAO userDAO = new UserDAO();
    public static TitleDAO titleDAO = new TitleDAO();
    public static ReviewDAO reviewDAO = new ReviewDAO();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        showLoginView(stage);
    }

    public static void showLoginView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/login/login.fxml"));
            Scene sceneLogin = new Scene(loader.load());
            LoginController loginController = loader.getController();
            loginController.setStage(stage);
            stage.setResizable(false);
            stage.setScene(sceneLogin);
            stage.show();
        }
        catch (IOException e) {
            AlertMessage.showErrorAlert("UI Error", "Erro ao carregar a página de Login");
        }
    }

    public static void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/criticbox.fxml"));
            Scene scene = new Scene(loader.load());
            MainController mainController = loader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            mainController.setStage(stage);
            stage.setScene(scene);
            stage.show();

            CurrentlyUser.setReviews(userDAO.getReviewsByUser(CurrentlyUser.getUser()));
            CurrentlyUser.setFavorites(userDAO.getFavoritesByUser(CurrentlyUser.getUser()));
            CurrentlyUser.setWatched(userDAO.getWatchedByUser(CurrentlyUser.getUser()));
        }
        catch (IOException e) {
            AlertMessage.showErrorAlert("UI Error", "Erro ao carregar a Mainview");
        }
    }
}
