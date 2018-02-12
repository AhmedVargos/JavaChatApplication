package com.chatcompany.chatserver.controllers;


import com.chatcompany.chatserver.models.LoginIntImp;
import com.chatcompany.chatserver.models.ServiceLoaderIntImp;
import static com.chatcompany.commonfiles.commModels.Constants.REGISTRY_PORT;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMainViewController implements Initializable {

    @FXML
    public ImageView serverViewMin;
    @FXML
    public ImageView serverViewClose;
    @FXML
    public Button serverStartBtn;
    @FXML
    public Button serverStopBtn;

    private Registry registry; 
    private final String CHAT_TAG = "chat";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize handlers to the buttons
        close();
        minimize();

        serverStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startServer();
            }
        });


        serverStopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopServer();
            }
        });
    }

    //stops the server
    private void stopServer() {
        try {
            registry.unbind(CHAT_TAG);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //starts the server
    private void startServer() {
        try {
            registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            
            registry.rebind(CHAT_TAG, new ServiceLoaderIntImp());

            System.out.println("Server is Online");

        } catch (RemoteException ex) {
            //Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    //close window will close all connections
    private void close() {
        serverViewClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //Platform.exit();
                System.exit(0);
            }
        });
    }

    //minimize the screen
    private void minimize() {
        serverViewMin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ((Stage)serverViewMin.getScene().getWindow()).setIconified(true);
            }
        });
    }
}
