package com.chatcompany.chatclient.models;


import com.chatcompany.chatclient.controllers.ContactTabViewController;
import com.chatcompany.commonfiles.common.ClientInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;

public class ClientIntImp implements ClientInterface {
    private ContactTabViewController mContactTabViewController;

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
   
}
