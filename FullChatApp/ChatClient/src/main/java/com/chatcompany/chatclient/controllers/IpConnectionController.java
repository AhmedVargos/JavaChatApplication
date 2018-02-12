package com.chatcompany.chatclient.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.chatcompany.chatclient.views.MainApp;
import static com.chatcompany.commonfiles.commModels.Constants.REGISTRY_PORT;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.LoginInterface;
import com.chatcompany.commonfiles.common.ServiceLoaderInterface;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class IpConnectionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label exit;
    @FXML
    private Label min;
    @FXML
    private TextField ipTextfield;

    //private boolean toggleTemp = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Platform.exit();
            }
        });
        min.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ((Stage) min.getScene().getWindow()).setIconified(true);
            }
        });

    }

    @FXML
    private void handleConnectionAction(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            String ip = ipTextfield.getText().toString();
            Pattern IP_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

            if (IP_PATTERN.matcher(ip).matches()) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/fxml/SignIn.fxml"));
                    Registry registry = LocateRegistry.getRegistry(ip, REGISTRY_PORT);
                    ServiceLoaderInterface server = (ServiceLoaderInterface) registry.lookup("chat");
                    MainApp.setServiceLoaderInterface(server);

                    Scene scene = new Scene(parent);
                    
                    //Open new scene and position it in the middle
                    MainApp.getMainStage().setScene(scene);
                    MainApp.getMainStage().setWidth(537);
                    MainApp.getMainStage().setHeight(437);

                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
                    MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Server Ip not found");
                    alert.setContentText("Please cheack the ip entered!");
                    alert.show();
                }
            }
        }

    }
}
