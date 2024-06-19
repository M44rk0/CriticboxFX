module com.m44rk0.criticboxfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires info.movito.themoviedbapi;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires io.github.cdimascio.dotenv.java;

    exports com.m44rk0.criticboxfx.model;
    exports com.m44rk0.criticboxfx.controller;
    opens com.m44rk0.criticboxfx.controller to javafx.fxml, javafx.controls;
    exports com.m44rk0.criticboxfx.view;
    opens com.m44rk0.criticboxfx.view to javafx.controls, javafx.fxml;
    exports com.m44rk0.criticboxfx;
    opens com.m44rk0.criticboxfx to javafx.controls, javafx.fxml;
    opens com.m44rk0.criticboxfx.model to javafx.controls, javafx.fxml;
}