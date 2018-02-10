package com.chatcompany.chatclient.models;

import com.chatcompany.chatclient.common.ClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientIntImp implements ClientInterface {
    //Message received from server
    @Override
    public void receiveMessage(Message msg) throws RemoteException {

    }

    //Friend request received from server
    @Override
    public void receiveFriendRequest(ArrayList<User> friendRequests) throws RemoteException {

    }

    //Update the contact list
    @Override
    public void updateContactsList(ArrayList<User> friend) throws RemoteException {

    }
}
