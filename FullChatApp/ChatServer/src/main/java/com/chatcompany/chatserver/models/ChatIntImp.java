package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.common.ChatInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChatIntImp implements ChatInterface {
    //Will send message to a single user
    @Override
    public void sendMessage(Message msg) throws RemoteException {

    }

    //Will send message to a group of users
    @Override
    public void sendMessageToGroup(ArrayList<Message> msg) throws RemoteException {

    }
}
