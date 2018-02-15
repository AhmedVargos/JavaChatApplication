package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.common.FriendInterface;
import com.chatcompany.commonfiles.common.ServerMainInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public ChatAreaController getEmbeddedChatTabsViewController() {
        return embeddedChatTabsViewController;
    }

    public ContactTabViewController getEmbeddedContactTabViewController() {
        return embeddedContactTabViewController;
    }

    public ToolBarViewController getEmbeddedToolBarViewController() {
        return embeddedToolBarViewController;
    }
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        embeddedContactTabViewController.setChatAreaController(embeddedChatTabsViewController);
        MainApp.getClientIntImp().setmContactTabViewController(embeddedContactTabViewController);
        
        //Adding instanse to the mainAPP
        MainApp.setMainChatParentController(this);
        try {
            FriendInterface friendInterface = (FriendInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.REQUESTS_SERVICE);
            embeddedContactTabViewController.addNewRequest(friendInterface.getFriendRequestList(MainApp.getMainUser().getId()));
            ServerMainInterface serverMainInterface = (ServerMainInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.SERVER_MAIN_SERVICE);
            embeddedContactTabViewController.addNewFriend(serverMainInterface.getContactsList(MainApp.getMainUser().getId()));
            
        } catch (RemoteException ex) {
            Logger.getLogger(MainChatParentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainChatParentController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }    
}
