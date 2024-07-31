package com.m44rk0.criticboxfx;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.dao.ReviewDAO;
import com.m44rk0.criticboxfx.dao.TitleDAO;
import com.m44rk0.criticboxfx.dao.UserDAO;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal do aplicativo que estende {@link Application}.
 * Gerencia a inicialização da aplicação e a transição entre diferentes visualizações.
 */
public class App extends Application {

    /**
     * Instância do {@link UserDAO} para operações relacionadas aos usuários.
     */
    public static UserDAO userDAO = new UserDAO();

    /**
     * Instância do {@link TitleDAO} para operações relacionadas aos títulos.
     */
    public static TitleDAO titleDAO = new TitleDAO();

    /**
     * Instância do {@link ReviewDAO} para operações relacionadas às avaliações.
     */
    public static ReviewDAO reviewDAO = new ReviewDAO();

    /**
     * Método principal da aplicação.
     * Lança a aplicação JavaFX.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Inicializa a aplicação e mostra a tela de login.
     *
     * @param stage O palco principal da aplicação.
     */
    @Override
    public void start(Stage stage) {
        showLoginView(stage);
    }

    /**
     * Exibe a tela de login.
     *
     * @param stage O palco onde a tela de login será exibida.
     */
    public static void showLoginView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/login/login.fxml"));
            Scene sceneLogin = new Scene(loader.load());
            stage.setResizable(false);
            stage.setScene(sceneLogin);
            stage.show();
        }
        catch (IOException e) {
            AlertMessage.showErrorAlert("UI Error", "Erro ao carregar a página de Login");
        }
    }

    /**
     * Exibe a tela principal após o login.
     * Inicializa os dados do usuário atual, como avaliações, favoritos e títulos assistidos.
     */
    public static void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("controller/mainview/criticbox.fxml"));
            Scene scene = new Scene(loader.load());
            MainController mainController = loader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            mainController.setStage(stage);
            stage.setScene(scene);
            stage.show();

            // Remove todos os títulos salvos no banco de dados durante a busca e que
            // não possuem relacionamentos ativos com outras tabelas
            // (review, favorites, watched, lastresults) ao final da aplicação.
            stage.setOnCloseRequest(event -> {
                titleDAO.removeUnrelatedTitles();
                titleDAO.removeUnrelatedLastResults();
            });

        }
        catch (IOException e) {
            AlertMessage.showErrorAlert("UI Error", "Erro ao carregar a página inicial");
        }
    }
}

