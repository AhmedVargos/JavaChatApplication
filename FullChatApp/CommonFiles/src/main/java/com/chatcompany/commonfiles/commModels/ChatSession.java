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

    public ChatSession(String id, ArrayList<User> chatUsers) {
        this.id = id;
        this.chatUsers = chatUsers;
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
    
    
}
