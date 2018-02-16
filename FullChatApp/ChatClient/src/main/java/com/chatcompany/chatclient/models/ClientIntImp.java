package com.chatcompany.chatclient.models;

import com.chatcompany.chatclient.controllers.ChatAreaController;
import com.chatcompany.chatclient.controllers.ChatBoxController;
import com.chatcompany.chatclient.controllers.ContactTabViewController;
import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.common.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

public class ClientIntImp extends UnicastRemoteObject implements ClientInterface {

    private ContactTabViewController mContactTabViewController;

    public ClientIntImp() throws RemoteException {
    }

    public void setmContactTabViewController(ContactTabViewController mContactTabViewController) {
        this.mContactTabViewController = mContactTabViewController;
    }

    @Override
    public void receiveMessage(Message msg, ChatSession chat) throws RemoteException {

        String id;

        ObservableList<Tab> tabs = (ObservableList<Tab>) MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().getChatTabPane().getTabs();
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i).getId().equals(chat.getId())) {
                ChatAreaController.append(msg, tabs.get(i).getId());
            } else {
                //create tab session 
                MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().openNewChatTabReciver(chat);
                ChatAreaController.append(msg, tabs.get(i).getId());
                
            }
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

}
