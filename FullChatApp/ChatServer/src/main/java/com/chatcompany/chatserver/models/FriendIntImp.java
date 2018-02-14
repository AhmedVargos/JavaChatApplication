package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.controllers.ServerMainViewController;
import com.chatcompany.chatserver.views.ServerView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.FriendInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FriendIntImp extends UnicastRemoteObject implements FriendInterface {

    public FriendIntImp() throws RemoteException {
    }

    private String property = System.getProperty("user.dir");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isResourceClosed;
    private String query;
    private ServerMainViewController controller;

    //   public FriendIntImp (ServerMainViewController controller)throws RemoteException  {
//      this.controller= this.controller;
//    }
//     LoginIntImp  logim = new LoginIntImp();
    private void connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + property + "\\chatDatabase.db";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url, "", "");
            isResourceClosed = false;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * close connection to database
     */
    private void closeResourcesOpened() {
        try {
            if (!isResourceClosed) {
                //resultSet.close();
                statement.close();
                connection.close();
                isResourceClosed = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean sendFriendRequest(int idMe, String receiver) throws RemoteException {

        try {
            connect();
            query = "select * from  USER where user_name='" + receiver + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!(resultSet.next())) {

                return false; //el friend m4 mawgod
            }
            //resultSet.first();
            int receiverId = resultSet.getInt("id");
            query = "select * from friend where (user_id='" + idMe + "' and friend_id='" + receiverId + "')";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {

                return false; //el friend mogoooood aslan
            }

            query = "select * from friend_request where (sender_id='" + idMe + "' and receiver_id='" + receiverId + "')";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {

                return false; // el requst mawgoood
            }

            query = "insert into friend_request (sender_id,receiver_id)values ('" + idMe + "','" + receiverId + "')";
            statement.executeUpdate(query);
            if (ServerView.getClientsOnline().containsKey(receiverId)) {
                ServerView.getClientsOnline().get(receiverId).receiveFriendRequest(getFriendRequestList(receiverId));
                System.out.println("Friend is online and request sent");
            } else {
                System.out.println("Friend is offline");
            }
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeResourcesOpened();
        }

    }

/////////////
    @Override
    public boolean acceptFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        try {
            connect();
            query = "insert into friend (user_id,friend_id)values ('" + idMe + "','" + idMyFriend + "')";
            statement.executeUpdate(query);
            query = "delete from request_friend where sender_id='" + idMe + "' and receiver_id='" + idMyFriend + "'";
            statement.executeUpdate(query);

            ServerView.getClientsOnline().get(idMe).updateContactsList(new ServerMainIntImp().getContactsList(idMe));
            ServerView.getClientsOnline().get(idMyFriend).updateContactsList(new ServerMainIntImp().getContactsList(idMyFriend));

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeResourcesOpened();
        }

    }

    @Override
    /// l7d ma nt2abel
    public boolean deleteRequest(int idSender, int idReciever) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        try {
            connect();
            query = "delete from friend_Requests where user_id='" + idMe + "' and friend_id='" + idMyFriend + "'";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        closeResourcesOpened();
        return false;

    }

    @Override
    public ArrayList<User> getFriendRequestList(int id) throws RemoteException {

        ArrayList<User> requestsUsers = new ArrayList<>();
        ArrayList<Integer> idsArrayList = new ArrayList<>();
        try (
                // object mn class data base
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM FRIEND_REQUEST WHERE receiver_id=? ")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    idsArrayList.add(rs.getInt("sender_id"));
                }
                for (Integer idaI : idsArrayList) {
                    ///  requestsUsers.add(getUserById(id));
                    //query = "select friend_id from FRIEND where id = '" + idaI + "'";
                     query = "select * from USER where id = '" + idaI + "'";
           
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        int id_friend = resultSet.getInt("id");
                        String fname = resultSet.getString("fname");
                        String lname = resultSet.getString("lname");
                        String name = resultSet.getString("user_name");
                        String email = resultSet.getString("mail");
                        String pass = resultSet.getString("password");
                        int gender = resultSet.getInt("gender");
                        String country = resultSet.getString("country");
                        int connStatus = resultSet.getInt("connecting_status");
                        int appStatus = resultSet.getInt("appearance_status");

                        User user = new User(id_friend, name, email, fname, lname, pass, gender, country, connStatus, appStatus);
                        requestsUsers.add(user);
                    }
                }
                return requestsUsers;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return requestsUsers;
    }
}
