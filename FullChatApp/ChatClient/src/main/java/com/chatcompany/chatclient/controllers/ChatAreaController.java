/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;


public class ChatAreaController implements Initializable {

    @FXML
    private TabPane chatTabPane;
    private static HashMap<String, ChatBoxController> chatBoxConrollers = new HashMap<>();
    private ArrayList<String> peopleInChatWith = new ArrayList<>();
    private HashMap<String, Tab> tabsOpened = new HashMap<>();
    private HashMap<String, ChatSession> chatsCreated = new HashMap<>();

    @FXML
    private Label announcementText;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chatTabPane.tabClosingPolicyProperty().set(TabPane.TabClosingPolicy.SELECTED_TAB);

    }

    public void openNewChatTabReciver(ChatSession ch, Message msg) {
        final ArrayList<Tab> tabs = new ArrayList<>();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            tabs.add(new Tab(ch.getChatName()));
            tabs.get(0).setContent(root);

           
            tabs.get(0).setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    String tapTId = ((Tab) event.getSource()).getId();
                    ChatSession testC = chatsCreated.get(tapTId);
                    int tempId = testC.getChatUsers().get(1).getId();
                    tabsOpened.remove(String.valueOf(tempId));
                    if (ch.getChatUsers().size() <= 2) {
                        peopleInChatWith.remove(String.valueOf(tempId));

                    }
                }
            });

            ArrayList<User> arrayList = new ArrayList<>();

            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(ch);
            tabs.get(0).setId(ch.getId());
            chatBoxConrollers.put(tabs.get(0).getId(), boxController);
            tabsOpened.put(String.valueOf(ch.getChatUsers().get(0).getId()), tabs.get(0));
            chatsCreated.put(tabs.get(0).getId(), ch);
            
            if (ch.getChatUsers().size() <= 2) {
                peopleInChatWith.add(String.valueOf(ch.getChatUsers().get(0).getId()));

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatTabPane.getTabs().add(tabs.get(0));
                chatTabPane.getSelectionModel().select(tabs.get(0));
                append(msg, tabs.get(0).getId());
            }
        });
    }

    public void checkUserForChat(User user) {
        if (user.getConnStatus() == Constants.OFFLINE) {
            System.out.println("User is offline");
        } else {
            boolean alreadyChating = false;
            for (int i = 0; i < peopleInChatWith.size(); i++) {
                if (peopleInChatWith.get(i).equals(String.valueOf(user.getId()))) {
                    alreadyChating = true;
                }
            }
            if (alreadyChating) {
                System.out.println("Already speaking with him");
                chatTabPane.getSelectionModel().select(tabsOpened.get(String.valueOf(user.getId())));
            } else {
                openNewChatSession(user);
            }
        }
    }

    public void openNewChatSession(User user) {
        //add the chat FXML box and pass the chat session obj to the chat tab controller 
        //and when send each time send message and a chat session obj

        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            Tab tab = new Tab(user.getUsername());
            tab.setContent(root);

              ArrayList<User> arrayList = new ArrayList<>();

            User myUser = MainApp.getMainUser();
            arrayList.add(myUser);
            arrayList.add(user);

            ChatSession chatSession = new ChatSession(myUser.getId() + "" + user.getId(), arrayList, myUser.getUsername());
            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(chatSession);
            String temp = tab.getId();
            tab.setId(chatSession.getId());
            String temp2 = tab.getId();
            tab.setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    String tapTId = ((Tab) event.getSource()).getId();
                    ChatSession testC = chatsCreated.get(tapTId);
                    int tempId = testC.getChatUsers().get(1).getId();
                    tabsOpened.remove(String.valueOf(tempId));
                    peopleInChatWith.remove(String.valueOf(tempId));
                }
            });
            chatBoxConrollers.put(tab.getId(), boxController);
            chatTabPane.getTabs().add(tab);
            chatTabPane.getSelectionModel().select(tab);
            tabsOpened.put(String.valueOf(user.getId()), tab);
            chatsCreated.put(tab.getId(), chatSession);
            peopleInChatWith.add(String.valueOf(user.getId()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openNewGroupChatSession(ArrayList<User> users, String chatName) {
        //add the chat FXML box and pass the chat session obj to the chat tab controller 
        //and when send each time send message and a chat session obj
        ;
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            Tab tab = new Tab(chatName);
            tab.setContent(root);
           

            ChatSession chatSession = new ChatSession(MainApp.getMainUser().getUsername() + new java.util.Date(), users, chatName);
            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(chatSession);
            tab.setId(chatSession.getId());
            
            tab.setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    String tapTId = ((Tab) event.getSource()).getId();
                    ChatSession testC = chatsCreated.get(tapTId);
                    chatsCreated.remove(tapTId);
                    int tempId = testC.getChatUsers().get(1).getId();
                    tabsOpened.remove(String.valueOf(tempId));
                    
                }
            });
            
            chatBoxConrollers.put(tab.getId(), boxController);
            chatTabPane.getTabs().add(tab);
            chatTabPane.getSelectionModel().select(tab);
            tabsOpened.put(chatSession.getId(), tab);
            chatsCreated.put(tab.getId(), chatSession);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public TabPane getChatTabPane() {
        return chatTabPane;
    }

    public static void append(Message msg, String tabId) {
        ChatBoxController chatBoxController = chatBoxConrollers.get(tabId);
        chatBoxController.appendText(msg);

    }

    public void addAnnouncementText(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                announcementText.setText(msg);
                chatTabPane.getSelectionModel().select(0);
            }
        });
    }
}
