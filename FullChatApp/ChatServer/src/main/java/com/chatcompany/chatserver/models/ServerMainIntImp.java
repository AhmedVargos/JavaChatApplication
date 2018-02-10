package com.chatcompany.chatserver.models;


import com.chatcompany.commonfiles.common.ServerMainInterface;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import models.User;

public class ServerMainIntImp implements ServerMainInterface {

    
    @Override
    public boolean updateInfo(User user) throws SQLException, RemoteException {
        return false;
    }

    @Override
    public boolean signOut(User user) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getContactsList(int id) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createGroup(String groupName, ArrayList<User> users) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean notify(int id) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
