package com.chatcompany.commonfiles.common;

import com.chatcompany.commonfiles.commModels.ChatSession;
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
    public abstract void receiveMessage(Message msg,ChatSession chat) throws RemoteException;
    public void reciveFile( String filename, byte[] data, int dataLength,ChatSession chat)throws RemoteException;
    public abstract int acceptReceiveFile(ChatSession chatSession)throws RemoteException;
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
    
    /**
     * called to make a notification
     *
     * @param title
     * @param message
     * @throws RemoteException
     */
    public abstract void makeNotification(String title, String message) throws RemoteException;
    
    
}
