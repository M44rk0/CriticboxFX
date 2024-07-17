package com.m44rk0.criticboxfx.controller.login;

import com.m44rk0.criticboxfx.controller.user.CurrentlyUser;
import com.m44rk0.criticboxfx.model.user.User;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.m44rk0.criticboxfx.App;

import java.util.Objects;

import static com.m44rk0.criticboxfx.App.userDAO;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField nameRegister;

    @FXML
    private PasswordField passwordConfirmRegister;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private PasswordField passwordRegister;

    @FXML
    private Button registerButton;

    @FXML
    private TextField userLogin;

    @FXML
    private TextField userRegister;

    @FXML
    private Text invalid;

    private Stage stage;

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

    @FXML
    public void register(){
        if(!Objects.equals(getPasswordRegisterText(), getPasswordConfirmRegisterText())){
            AlertMessage.showCommonAlert("Erro de Cadastro", "Senhas não coincidem.");
        }

        else if(userDAO.userExists(getUserRegisterText())){
            AlertMessage.showCommonAlert("Erro de Cadastro", "Usúario já está cadastrado.");
        }

        else if (getPasswordRegisterText().isEmpty() || getPasswordRegisterText().isEmpty() || getNameRegisterText().isEmpty() || getUserRegisterText().isEmpty()) {
            AlertMessage.showCommonAlert("Erro de Cadastro", "Preencha todos os campos!");
        }
        else {
            userRegister.clear();
            nameRegister.clear();
            passwordRegister.clear();
            passwordConfirmRegister.clear();
            User user = new User(getNameRegisterText(), getUserRegisterText(), getPasswordRegisterText());
            userDAO.addUser(user);
            AlertMessage.showCommonAlert("Confirmação de Cadastro", "Usuário cadastrado com sucesso, faça seu login!");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getUserLoginText(){
        return userLogin.getText();
    }

    public String getPasswordLoginText(){
        return passwordLogin.getText();
    }

    public String getNameRegisterText(){
        return nameRegister.getText();
    }

    public String getUserRegisterText(){
        return userRegister.getText();
    }

    public String getPasswordRegisterText(){
        return passwordRegister.getText();
    }

    public String getPasswordConfirmRegisterText(){
        return passwordConfirmRegister.getText();
    }

    public void isInvalid(){
        invalid.setVisible(true);
    }

}

