package com.m44rk0.criticboxfx.controller.mainview;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;

public class ViewTabController {

    @FXML
    private FlowPane resultsFlow;

    @FXML
    private Tab resultsTab;


    public FlowPane getResultsFlow() {
        return resultsFlow;
    }

    public void setResultTabText(String text){
        this.resultsTab.setText(text);
    }
}
