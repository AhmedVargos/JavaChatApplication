package com.chatcompany.commonfiles.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;


public interface ClientInterface extends Remote {

    /**
     * receives a message from server and pass it to the controller
     *
     * @param msg massage 
     * @throws RemoteException if RMI failed
     */
    public abstract void receiveMessage(Message msg) throws RemoteException;

    /**
     * receive sent friend requests
     *
     * @param friendRequests
     * @throws RemoteException
     */
    public abstract void receiveFriendRequest(ArrayList<User> friendRequests) throws RemoteException;

    /**
     * called to update friends lest
     *
     * @param friend
     * @throws RemoteException
     */
    public abstract void updateContactsList(ArrayList<User> friend) throws RemoteException;
    

}
