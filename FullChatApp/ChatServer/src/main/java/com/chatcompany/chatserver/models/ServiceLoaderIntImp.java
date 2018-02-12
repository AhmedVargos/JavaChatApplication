package com.chatcompany.chatserver.models;

import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.common.ServiceLoaderInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceLoaderIntImp extends UnicastRemoteObject implements ServiceLoaderInterface  {
    
    public ServiceLoaderIntImp () throws RemoteException{
    
    }
    @Override
    public Object getServiceInstance(String name) throws RemoteException {

        switch (name) {
            case Constants.LOGIN_SERVICE:
                return new LoginIntImp();
            case Constants.SERVER_MAIN_SERVICE:
                return new ServerMainIntImp();
            case Constants.REQUESTS_SERVICE:
                return new FriendIntImp();
            case Constants.CHAT_SERVICE:
                return new ChatIntImp();
            default:
                return null;
        }

    }
}
