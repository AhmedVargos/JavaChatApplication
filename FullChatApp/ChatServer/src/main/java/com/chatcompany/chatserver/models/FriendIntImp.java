package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.common.FriendInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class FriendIntImp implements FriendInterface {

    //Send a friend request to a single user and save it to DB
    @Override
    public boolean sendFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        return false;
    }


    //Accepts a friend request and  save the friend in DB, and remove request
    @Override
    public boolean acceptFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        return false;
    }

    //Delete friend  and remove it from DB
    @Override
    public boolean deleteFriend(int idMe, int idMyFriend) throws RemoteException {
        return false;
    }

    //Delete friend request  and remove it from DB
    @Override
    public boolean removeFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        return false;
    }

    //Return all the requests to a user
    @Override
    public ArrayList<User> getFriendRequestList(int id) throws RemoteException {
        return null;
    }
}
