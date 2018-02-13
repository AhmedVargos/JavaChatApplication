package com.chatcompany.commonfiles.common;


import java.rmi.RemoteException;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.User;
import java.sql.SQLException;

public interface FriendInterface {
    /**
     * Description Func used to send friend Request
     *
     * @param idMe User id
     * @param idMyfirend receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
    public boolean sendFriendRequest(String sender, String receiver) throws RemoteException;


    /**
     * Description Func used to accept friend Request
     *
     * @param idMe User id
     * @param idMyFriend receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
    public boolean acceptFriendRequest(int idMe, int idMyFriend ) throws RemoteException;


    /**
     * Description Func used to delete friend
     *
     * @param idMe User id
     * @param idMyFriend receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
     public boolean deleteRequest(int idSender, int idReciever) throws RemoteException;

     
    /**
     * Description Func used to decline friend Request
     *
     * @param idSender User id
     * @param idReceiver receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
    public boolean removeFriendRequest(int idMe, int idMyFriend) throws RemoteException;

    /**
     * Description Func used to get all friend requests
     *
     * @param id User id
     * @return ArrayList of userDtos user Friend Requests List
     * @throws RemoteException if RMI failed
     */
    public ArrayList<User> getFriendRequestList(int id) throws RemoteException;
    

}