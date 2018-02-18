package com.chatcompany.chatclient.models;

import com.chatcompany.chatclient.controllers.ChatAreaController;
import com.chatcompany.chatclient.controllers.ChatBoxController;
import com.chatcompany.chatclient.controllers.ContactTabViewController;
import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.common.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ClientIntImp extends UnicastRemoteObject implements ClientInterface {

    private ContactTabViewController mContactTabViewController;

    public ClientIntImp() throws RemoteException {
    }

    public void setmContactTabViewController(ContactTabViewController mContactTabViewController) {
        this.mContactTabViewController = mContactTabViewController;
    }

    @Override
    public void receiveMessage(Message msg, ChatSession chat) throws RemoteException {
        boolean isFound = false;
        String id;

        ObservableList<Tab> tabs = (ObservableList<Tab>) MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().getChatTabPane().getTabs();

        for (int i = 0; i < tabs.size(); i++) {
            id = tabs.get(i).getId();
            if (tabs.get(i).getId().equals(chat.getId())) {
                isFound = true;
                ChatAreaController.append(msg, tabs.get(i).getId());
            }
        }
        if (!isFound) {
            //create tab session 
            MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().openNewChatTabReciver(chat, msg);
            //ChatAreaController.append(msg, tabs.get(i).getId());

        }
    }

    @Override
    public void receiveFriendRequest(ArrayList<User> friendRequests) throws RemoteException {
        mContactTabViewController.addNewRequest(friendRequests);
    }

    @Override
    public void updateContactsList(ArrayList<User> friend) throws RemoteException {
        mContactTabViewController.addNewFriend(friend);
    }

    @Override
    public void makeNotification(String title, String message) throws RemoteException {

        if (title.equals(Constants.ANNOUNCEMENT)) {
            //TODO go to the fixed welcome tab and add the message to it.
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications notification = Notifications.create()
                            .darkStyle()
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.BOTTOM_RIGHT)
                            .text(message)
                            .title(title);

                    notification.show();
                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Notifications notification = Notifications.create()
                            .darkStyle()
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.BOTTOM_RIGHT)
                            .text(message)
                            .title(title);

                    notification.show();
                }
            });
        }

    }
    //receive file from server 

    @Override
    public int acceptReceiveFile(ChatSession chatSession) throws RemoteException {

        
            int ret = 0;
                
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Confirmation Dialog");
//                alert.setHeaderText("Look, a Confirmation Dialog");
//                alert.setContentText("Are you ok with this?");
//
//                Optional<ButtonType> result = alert.showAndWait();
//                if (result.get() == ButtonType.OK) {
//                    ret = 1;
//                } else {
//                    ret = 0;
//                }

            
            
        
       return 1; 
    }

    @Override
    public void reciveFile(String filename, byte[] data, int dataLength, ChatSession chatSession) throws RemoteException {
        boolean isFound = false;
        String id;

        ObservableList<Tab> tabs = (ObservableList<Tab>) MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().getChatTabPane().getTabs();
        Message msg = new Message();
        for (int i = 0; i < tabs.size(); i++) {
            id = tabs.get(i).getId();
            if (tabs.get(i).getId().equals(chatSession.getId())) {
                isFound = true;

                ChatAreaController.append(new Message(), tabs.get(i).getId());
            }
        }
        if (!isFound) {
            //create tab session 
            MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().openNewChatTabReciver(chatSession, msg);
            //ChatAreaController.append(msg, tabs.get(i).getId());

        }
        //check for user accept   
        try {
            String pathDefault = "C:\\Users\\Public\\Downloads\\";
            File f = new File(pathDefault + filename);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f, true);
            out.write(data, 0, dataLength);
            out.flush();
            out.close();
            System.out.println("Done writing data...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
