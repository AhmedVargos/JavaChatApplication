package com.chatcompany.chatclient.models;

import com.chatcompany.chatclient.controllers.ContactTabViewController;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.common.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;
import javafx.application.Platform;
import javafx.geometry.Pos;
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
    public void receiveMessage(Message msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
