package com.chatcompany.chatserver.models;



import com.chatcompany.commonfiles.common.LoginInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import models.User;

public class LoginIntImp extends UnicastRemoteObject implements LoginInterface {

    public LoginIntImp()throws RemoteException{

    }

    @Override
    public User login(String userName, String pass) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean SignUp(User user) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
