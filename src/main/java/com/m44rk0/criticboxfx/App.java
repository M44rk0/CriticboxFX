package com.m44rk0.criticboxfx;

import com.m44rk0.criticboxfx.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage stage) throws IOException {
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