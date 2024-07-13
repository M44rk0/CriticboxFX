module com.m44rk0.criticboxfx {

    requires javafx.controls;
    requires javafx.fxml;
    requires info.movito.themoviedbapi;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;

    exports com.m44rk0.criticboxfx;
    opens com.m44rk0.criticboxfx to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.controller.login;
    opens com.m44rk0.criticboxfx.controller.login to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.controller.favorites;
    opens com.m44rk0.criticboxfx.controller.favorites to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.controller.review;
    opens com.m44rk0.criticboxfx.controller.review to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.controller.homepage;
    opens com.m44rk0.criticboxfx.controller.homepage to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.controller.details;
    opens com.m44rk0.criticboxfx.controller.details to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.model.title;
    opens com.m44rk0.criticboxfx.model.title to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.model.search;
    opens com.m44rk0.criticboxfx.model.search to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.model.review;
    opens com.m44rk0.criticboxfx.model.review to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.model.user;
    opens com.m44rk0.criticboxfx.model.user to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.controller.mainview;
    opens com.m44rk0.criticboxfx.controller.mainview to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.dao;
    opens com.m44rk0.criticboxfx.dao to javafx.controls, javafx.fxml;

    exports com.m44rk0.criticboxfx.controller;
    opens com.m44rk0.criticboxfx.controller to javafx.controls, javafx.fxml;
}