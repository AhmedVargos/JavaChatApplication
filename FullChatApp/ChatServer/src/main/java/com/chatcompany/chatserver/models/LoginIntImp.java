package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.common.LoginInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class LoginIntImp implements LoginInterface {

    //Check i user is on the DB and return Obj from user
    @Override
    public User login(String userName, String pass) throws RemoteException {
        return null;
    }

    //Create new user account if available
    @Override
    public Boolean SignUp(User user) throws SQLException, RemoteException {
        return null;
    }
}
