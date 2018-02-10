package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.common.ServerMainInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerMainIntImp implements ServerMainInterface {

    //Update user info
    @Override
    public boolean updateInfo(User user) throws SQLException, RemoteException {
        return false;
    }

    //SignOut user from db and remove it from list
    @Override
    public boolean signOut(User user) throws SQLException, RemoteException {
        return false;
    }

    //Return user Friends List
    @Override
    public ArrayList<User> getContactsList(int id) throws SQLException, RemoteException {
        return null;
    }

    //Create group with name and list
    @Override
    public boolean createGroup(String groupName, ArrayList<User> users) throws SQLException, RemoteException {
        return false;
    }

    //Notify the user with changes
    @Override
    public boolean notify(int id) throws SQLException, RemoteException {
        return false;
    }
}
