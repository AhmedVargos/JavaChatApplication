/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.commonfiles.common;

import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.commModels.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Dell
 */
public interface ChatReceiverInterface extends Remote{
    public abstract void receiveMessage(Message message,ChatSession chat)throws RemoteException;
    
    
}
