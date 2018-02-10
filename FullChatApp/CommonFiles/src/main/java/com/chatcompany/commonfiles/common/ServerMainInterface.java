/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.commonfiles.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import models.User;

/**
 * Description ServerMainInterface is where to put all methods
 *
 * @author  shalaby
 * clients may use as a service
 */
public interface ServerMainInterface extends Remote {


    /**
     * Description Func used to Update User Info
     *
     * @param user UserDTO
     * @return true if succeed, false if failed
     * @throws SQLException if DB failed
     * @throws RemoteException if RMI failed
     */
    public boolean updateInfo(User user) throws SQLException, RemoteException;

    /**
     * Description Func used to logout
     *
     * @param user UserDTO
     * @return true if succeed, false if failed
     * @throws SQLException if DB failed
     * @throws RemoteException if RMI failed
     */
    public boolean signOut(User user) throws SQLException, RemoteException;


    public ArrayList<User> getContactsList(int id) throws SQLException, RemoteException;

    public boolean createGroup(String groupName, ArrayList<User> users)throws SQLException, RemoteException;


    public boolean notify(int id) throws SQLException, RemoteException;


    

}
