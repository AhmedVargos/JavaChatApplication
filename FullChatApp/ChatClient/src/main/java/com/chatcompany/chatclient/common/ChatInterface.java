package com.chatcompany.chatclient.common;

import com.chatcompany.chatserver.models.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface  ChatInterface{
	 /**
     * send a message from server and pass it to the controller
     *
     * @param msg massage 
     * @throws RemoteException if RMI failed
     */
    public abstract void sendMessage(Message msg) throws RemoteException;

 /**
     * send a message from server and pass it to the controller
     *
     * @param msg massage 
     * @throws RemoteException if RMI failed
     */
    public abstract void sendMessageToGroup(ArrayList<Message> msg) throws RemoteException;


}