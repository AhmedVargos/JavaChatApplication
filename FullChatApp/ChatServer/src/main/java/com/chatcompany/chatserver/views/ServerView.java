package com.chatcompany.chatserver.views;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.chatcompany.chatserver.controllers.ServerMainViewController;
import java.io.IOException;
import java.util.HashMap;

import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.ClientInterface;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author shalaby
 */
public class ServerView extends Application {

    private static Stage myStage;
    private static ServerView server;

    private static HashMap<Integer, ClientInterface> clientsOnline;

    public static ServerView getServer() {
        return server;
    }

    public static Stage getMyStage() {
        return myStage;
    }

    public static HashMap<Integer, ClientInterface> getClientsOnline() {
        return clientsOnline;
    }

//    public static void removeClientFromMap(User user){
//        clientsOnline.remove(user.getId());
//    }
//
//    public static void removeAllClientsFromMap(){
//        clientsOnline.clear();
//    }
//
//    public static void addClientToMap(){
//
//    }
    public ServerView() {
        clientsOnline = new HashMap<>();
        //  onlineUsersRefsMap=<=new Hashtable<Integer, ClientInterface>();
        // mo4kela hena

    }

    /**
     *
     *
     * Javafx Start Method
     *
     * method to get Start Server
     */
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            root = FXMLLoader.load(getClass().getResource("/fxml/ServerView.fxml"));
            myStage = stage;
            Scene scene = new Scene(root);
            stage.setWidth(750);
            stage.setHeight(500);
            ServerMainViewController controller = loader.getController();
            //PieChart PC = controller.getPc();
            stage.setResizable(false);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                    try {
                        controller.stopServer();
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            //scene.getStylesheets().add(getClass().getResource(pathCSS).toExternalForm());
            //scene.getStylesheets().add("/styles/tabPane.css");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.show();
    }

    /**
     *
     *
     * public static MAIN
     *
     * @param args of string
     */
    public static void main(String[] args) {
        server = new ServerView();
        launch(args);
    }

}
