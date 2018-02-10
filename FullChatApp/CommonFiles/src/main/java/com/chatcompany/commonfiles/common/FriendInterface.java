package com.chatcompany.commonfiles.common;


import java.rmi.RemoteException;
import java.util.ArrayList;
import models.User;

public interface FriendInterface {
    /**
     * Description Func used to send friend Request
     *
     * @param idMe User id
     * @param idMyfirend receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
    public boolean sendFriendRequest(int idMe, int idMyfirend) throws RemoteException;


    /**
     * Description Func used to send friend Request
     *
     * @param idMe User id
     * @param idMyFriend receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
    public boolean acceptFriendRequest(int idMe, int idMyFriend) throws RemoteException;


    /**
     * Description Func used to send friend Request
     *
     * @param idMe User id
     * @param idMyFriend receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
    public boolean deleteFriend(int idMe, int idMyFriend) throws RemoteException;

    /**
     * Description Func used to decline friend Request
     *
     * @param idMe User id
     * @param idMyFriend receiver id
     * @return true if send false otherwise;
     * @throws RemoteException if RMI failed
     */
    public boolean removeFriendRequest(int idMe, int idMyFriend) throws RemoteException;

    /**
     * Description Func used to logout
     *
     * @param id User id
     * @return ArrayList of userDtos user Friend Requests List
     * @throws RemoteException if RMI failed
     */
    public ArrayList<User> getFriendRequestList(int id) throws RemoteException;

    /**
     * Description Func used to get group chat id
     *
     * @param groupName group name
     * @return group chat id
     * @throws RemoteException if RMI failed
     */
}