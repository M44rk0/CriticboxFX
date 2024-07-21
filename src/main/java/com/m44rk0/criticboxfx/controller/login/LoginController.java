package com.m44rk0.criticboxfx.controller.login;

import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.user.User;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.m44rk0.criticboxfx.App;
import java.util.Objects;

import static com.m44rk0.criticboxfx.App.userDAO;

/**
 * Controlador para gerenciar a lógica de login e registro na interface do usuário.
 */
public class LoginController {

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

    @FXML
    private Text invalid;

    private Stage stage;

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
            stage.close();
            App.showMainView();
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
            userRegister.clear();
            nameRegister.clear();
            passwordRegister.clear();
            passwordConfirmRegister.clear();
            User user = new User(getNameRegisterText(), getUserRegisterText(), getPasswordRegisterText());
            userDAO.addUser(user);
            AlertMessage.showCommonAlert("Confirmação de Cadastro", "Usuário cadastrado com sucesso, faça seu login!");
        }
    }

    /**
     * Define o stage (janela) atual.
     *
     * @param stage O stage a ser definido.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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


