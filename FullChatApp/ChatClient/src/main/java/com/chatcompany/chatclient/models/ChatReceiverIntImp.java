/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.models;

import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.common.ChatReceiverInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Dell
 */
public class ChatReceiverIntImp extends UnicastRemoteObject implements ChatReceiverInterface {

    public ChatReceiverIntImp() throws RemoteException {

    }

    @Override
    public void receiveMessage(Message message, ChatSession chat) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
