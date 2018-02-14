package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainChatParentController implements Initializable {
    
    @FXML
    private ChatAreaController embeddedChatTabsViewController;
    @FXML
    private ContactTabViewController embeddedContactTabViewController;
    @FXML
    private ToolBarViewController embeddedToolBarViewController;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        embeddedContactTabViewController.setChatAreaController(embeddedChatTabsViewController);
        MainApp.getClientIntImp().setmContactTabViewController(embeddedContactTabViewController);
    }    
}
