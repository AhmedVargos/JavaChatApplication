package com.chatcompany.commonfiles.common;

import com.chatcompany.commonfiles.commModels.ChatSession;
import java.rmi.RemoteException;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;
import java.io.File;
import java.rmi.Remote;

public interface ChatInterface extends Remote {

    /**
     * send a message from server and pass it to the controller
     *
     * @param msg massage
     * @throws RemoteException if RMI failed
     */
    public abstract void sendMessage(Message msg, ChatSession chat) throws RemoteException;

    /**
     * send a message from server and pass it to the controller
     *
     * @param msg massage
     * @throws RemoteException if RMI failed
     */
    public abstract void sendMessageToGroup(ArrayList<Message> msg) throws RemoteException;

    public void sendFile(int sId, String filename, String isFirst, byte[] data, int dataLength, ChatSession chatSession) throws RemoteException;
}
