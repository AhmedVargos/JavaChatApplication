package com.chatcompany.chatserver.views;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.HashMap;

import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.ClientInterface;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author shalaby
 */
public class ServerView extends Application {


    private static Stage myStage;
    private static ServerView server;

    private static HashMap<Integer,ClientInterface> clientsOnline;
    


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

    public ServerView(){
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
            root = FXMLLoader.load(getClass().getResource("/fxml/ServerUI.fxml"));
            myStage = stage;
            Scene scene = new Scene(root);
            //ServerMainViewController = controller.setScene(scene);
            //PieChart PC = controller.getPc();
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            //scene.getStylesheets().add(getClass().getResource(pathCSS).toExternalForm());
            scene.getStylesheets().add("/styles/tabPane.css");
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
        server=new ServerView();
        launch(args);
    }

}
