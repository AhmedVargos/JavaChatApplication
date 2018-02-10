package com.chatcompany.chatserver.models;

import com.chatcompany.commonfiles.common.ChatInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;

public class ChatIntImp implements ChatInterface {

    @Override
    public void sendMessage(Message msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMessageToGroup(ArrayList<Message> msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //Will send message to a single user
  
}
