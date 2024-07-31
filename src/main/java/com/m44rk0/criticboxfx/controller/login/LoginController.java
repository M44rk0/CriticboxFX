package com.m44rk0.criticboxfx.controller.login;

import com.m44rk0.criticboxfx.model.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.user.User;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.m44rk0.criticboxfx.App;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.m44rk0.criticboxfx.App.userDAO;

/**
 * Controlador para gerenciar a lógica de login e registro na interface do usuário.
 */
public class LoginController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField nameRegister;

    @FXML
    private PasswordField passwordConfirmRegister;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private PasswordField passwordRegister;

    @FXML
    private TextField userLogin;

    @FXML
    private TextField userRegister;

    /**
     * Método para realizar o login do usuário.
     * Verifica as credenciais do usuário e, se válidas, fecha a tela de login e exibe a tela principal.
     * Caso contrário, exibe uma mensagem de erro.
     */
    @FXML
    public void login() {

        User user = userDAO.getUserByCredentials(getUserLoginText(), getPasswordLoginText());

        if (user != null) {

            CurrentlyUser.setUser(user);

            loadSplash();

            CompletableFuture.runAsync(() -> {

                CurrentlyUser.setReviews(userDAO.getReviewsByUser(CurrentlyUser.getUser()));
                CurrentlyUser.setFavorites(userDAO.getFavoritesByUser(CurrentlyUser.getUser()));
                CurrentlyUser.setWatched(userDAO.getWatchedByUser(CurrentlyUser.getUser()));

            }).thenRun(() -> Platform.runLater(() -> {

                Stage splashStage = (Stage) root.getScene().getWindow();
                splashStage.close();
                App.showMainView();

            }));

        } else {
            AlertMessage.showCommonAlert("Erro de Login", "Usuário ou Senha Inválidos.");
        }
    }

    /**
     * Método para registrar um novo usuário.
     * Verifica se as senhas coincidem, se o usuário já existe, e se todos os campos estão preenchidos.
     * Se as verificações passarem, registra o novo usuário.
     */
    @FXML
    public void register() {
        if (!Objects.equals(getPasswordRegisterText(), getPasswordConfirmRegisterText())) {
            AlertMessage.showCommonAlert("Erro de Cadastro", "Senhas não coincidem.");
        } else if (userDAO.userExists(getUserRegisterText())) {
            AlertMessage.showCommonAlert("Erro de Cadastro", "Usuário já está cadastrado.");
        } else if (getPasswordRegisterText().isEmpty() || getPasswordConfirmRegisterText().isEmpty() || getNameRegisterText().isEmpty() || getUserRegisterText().isEmpty()) {
            AlertMessage.showCommonAlert("Erro de Cadastro", "Preencha todos os campos!");
        } else {
            User user = new User(getNameRegisterText(), getUserRegisterText(), getPasswordRegisterText());
            userRegister.clear();
            nameRegister.clear();
            passwordRegister.clear();
            passwordConfirmRegister.clear();
            userDAO.addUser(user);
            AlertMessage.showCommonAlert("Confirmação de Cadastro", "Usuário cadastrado com sucesso, faça seu login!");
        }
    }

    /**
     * Método para carregar o Loading após o login.

     */
    private void loadSplash() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginSplash.fxml"));
            Pane pane = loader.load();
            root.getChildren().addAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            fadeIn.play();

        } catch (IOException e) {
            AlertMessage.showErrorAlert("UI Error", "Erro ao carregar o Splash");
        }
    }

    /**
     * Obtém o texto do campo de login do usuário.
     *
     * @return O texto do campo de login do usuário.
     */
    public String getUserLoginText() {
        return userLogin.getText();
    }

    /**
     * Obtém o texto do campo de senha de login.
     *
     * @return O texto do campo de senha de login.
     */
    public String getPasswordLoginText() {
        return passwordLogin.getText();
    }

    /**
     * Obtém o texto do campo de nome do registro.
     *
     * @return O texto do campo de nome do registro.
     */
    public String getNameRegisterText() {
        return nameRegister.getText();
    }

    /**
     * Obtém o texto do campo de usuário do registro.
     *
     * @return O texto do campo de usuário do registro.
     */
    public String getUserRegisterText() {
        return userRegister.getText();
    }

    /**
     * Obtém o texto do campo de senha do registro.
     *
     * @return O texto do campo de senha do registro.
     */
    public String getPasswordRegisterText() {
        return passwordRegister.getText();
    }

    /**
     * Obtém o texto do campo de confirmação de senha do registro.
     *
     * @return O texto do campo de confirmação de senha do registro.
     */
    public String getPasswordConfirmRegisterText() {
        return passwordConfirmRegister.getText();
    }
}


