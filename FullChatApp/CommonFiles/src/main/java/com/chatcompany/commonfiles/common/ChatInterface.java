package com.chatcompany.commonfiles.common;

import java.rmi.RemoteException;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;

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