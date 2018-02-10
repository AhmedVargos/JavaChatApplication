package com.chatcompany.chatserver.models;


import com.chatcompany.commonfiles.common.FriendInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import models.User;

public class FriendIntImp implements FriendInterface {

    @Override
    public boolean sendFriendRequest(int idMe, int idMyfirend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean acceptFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteFriend(int idMe, int idMyFriend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getFriendRequestList(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
