package com.m44rk0.criticboxfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;

public class TabViewController {

    @FXML
    private TabPane TabPaneResults;

    @FXML
    private FlowPane resultsFlow;

    @FXML
    private Tab resultsTab;

    public TabPane getTabPaneResults() {
        return TabPaneResults;
    }

    public void setTabPaneResults(TabPane tabPaneResults) {
        TabPaneResults = tabPaneResults;
    }

    public FlowPane getResultsFlow() {
        return resultsFlow;
    }

    public void setResultsFlow(FlowPane resultsFlow) {
        this.resultsFlow = resultsFlow;
    }

    public Tab getResultsTab() {
        return resultsTab;
    }

    public void setResultsTab(Tab resultsTab) {
        this.resultsTab = resultsTab;
    }
}
