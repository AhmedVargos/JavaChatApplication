package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.views.ServerView;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.ServerMainInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ServerMainIntImp extends UnicastRemoteObject implements ServerMainInterface {

    public ServerMainIntImp() throws RemoteException {
    }

    private String property = System.getProperty("user.dir");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isResourceClosed;
    private String query;

    //connection database
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
    public boolean updateInfo(User user) throws SQLException, RemoteException {
        try {
            connect();
            query = "UPDATE USER SET connecting_status =" + user.getConnStatus() + ", appearance_status=" + user.getAppearanceStatus() + " WHERE id =" + user.getId();
            //query = "select * from USER where id = '" + user.getId() + "'";
            statement = connection.createStatement();
            statement.executeUpdate(query);

            /*resultSet.updateInt(1, user.getId());
            resultSet.updateString(2, user.getFname());
            resultSet.updateString(3, user.getLname());
            resultSet.updateString(4, user.getUsername());
            resultSet.updateString(5, user.getEmail());
            resultSet.updateString(6, user.getPassword());
            resultSet.updateInt(7, user.getGender());
            resultSet.updateString(9, user.getCountry());

            resultSet.rowUpdated();*/
                closeResourcesOpened();
                ArrayList<User> mFriends = getContactsList(user.getId());
                for (User friend : mFriends) {
                    if (ServerView.getClientsOnline().get(friend.getId()) != null) {
                        ServerView.getClientsOnline().get(friend.getId()).updateContactsList(new ServerMainIntImp().getContactsList(friend.getId()));
                        ServerView.getClientsOnline().get(friend.getId()).makeNotification("Friend States Changed", "A friend has changed his status.");
                    }
                }
                return true;
             

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean signOut(User user) throws SQLException, RemoteException {
        try {
            connect();
            query = "select * from USER where id = '" + user.getId() + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            resultSet.updateString(11, "offline");

            resultSet.rowUpdated();
            closeResourcesOpened();
            ServerView.getClientsOnline().remove(user.getId());
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    @Override
    public ArrayList<User> getContactsList(int id) throws SQLException, RemoteException {
        ArrayList<User> userFriendList = new ArrayList<>();
        try {
            connect();
            query = "select * from USER "
                    + "where id in (select friend_id from FRIEND where user_id = '" + id + "'" + ")";

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
                userFriendList.add(user);
                //ServerView.getClientsOnline().get(id).makeNotification("Friend List", "Friend list updated.");

            }

            resultSet.rowUpdated();
            closeResourcesOpened();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userFriendList;

    }

    @Override
    public ArrayList<User> getRequestsList(int id) throws SQLException, RemoteException {
        ArrayList<User> userFriendList = new ArrayList<>();
        try {
            connect();
            query = "select * from USER "
                    + "where id in (select sender_id from FRIEND_REQUEST where receiver_id = '" + id + "'" + ")";

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

                User user = new User(id, name, email, fname, lname, pass, gender, country, connStatus, appStatus);

                userFriendList.add(user);
            }

            resultSet.rowUpdated();
            closeResourcesOpened();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userFriendList;

    }

    @Override
    public boolean createGroup(String groupName, ArrayList<User> users) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean notify(int id) throws SQLException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
