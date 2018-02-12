package com.chatcompany.commonfiles.common;

import com.chatcompany.commonfiles.commModels.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceLoaderInterface extends Remote {

    /**
     * used to login
     * @param email user email address
     * @param pass user password
     * @return user bean if succeed ,null if user Already signed IN
     * @throws RemoteException if RMI failed
     */
    public Object getServiceInstance(String name) throws RemoteException;

}
