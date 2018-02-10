package com.chatcompany.chatclient.models;


import com.chatcompany.commonfiles.common.ClientInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import models.Message;
import models.User;

public class ClientIntImp implements ClientInterface {

    @Override
    public void receiveMessage(Message msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void receiveFriendRequest(ArrayList<User> friendRequests) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateContactsList(ArrayList<User> friend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
