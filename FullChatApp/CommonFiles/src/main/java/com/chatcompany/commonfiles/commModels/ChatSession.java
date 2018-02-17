/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.commonfiles.commModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Vargos
 */
public class ChatSession implements Serializable{
    String id;
    ArrayList<User> chatUsers;
    String chatName;

    public ChatSession(String id, ArrayList<User> chatUsers,String chatName) {
        this.id = id;
        this.chatUsers = chatUsers;
        this.chatName = chatName;
    }

    public ChatSession() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<User> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(ArrayList<User> chatUsers) {
        this.chatUsers = chatUsers;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
    
    
}
