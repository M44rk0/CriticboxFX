package com.m44rk0.criticboxfx.utils;

import com.m44rk0.criticboxfx.controller.MainController;
import com.m44rk0.criticboxfx.model.title.Title;
import javafx.scene.image.Image;

public interface CommonController {

    public void setTitle(Title title);
    public void setTitleField(String title);
    public void setOverviewField(String overview);
    public void setPosterImage(Image posterImage);
    public void setReleaseField(String releaseDate);
    public void setMainController(MainController controller);

}
