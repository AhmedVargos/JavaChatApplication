package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.views.ServerView;
import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.common.ChatInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;

public class ChatIntImp extends UnicastRemoteObject implements ChatInterface {

    public ChatIntImp() throws RemoteException {

    }

    //////////////////////////////////////////
    @Override
    public void sendMessage(Message msg, ChatSession chatSession) throws RemoteException {
        ArrayList<User> userList = chatSession.getChatUsers();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (ServerView.getClientsOnline().get(user.getId()) != null) {
                ServerView.getClientsOnline().get(user.getId()).receiveMessage(msg, chatSession);

            }
        }
    }

    @Override
    public void sendMessageToGroup(ArrayList<Message> msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //Will send message to a single user

}
