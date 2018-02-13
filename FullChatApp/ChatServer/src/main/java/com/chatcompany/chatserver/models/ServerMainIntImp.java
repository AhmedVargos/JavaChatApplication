package com.chatcompany.chatserver.models;

import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.ServerMainInterface;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ServerMainIntImp implements ServerMainInterface {

    private String property = System.getProperty("user.dir");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isResourceClosed;
    private String query;

    //coonection database
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
            query = "select * from USER where id = '" + user.getId() + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            resultSet.updateString(1, user.getId());
            resultSet.updateString(2, user.getFname());
            resultSet.updateString(3, user.getLname());
            resultSet.updateString(4, user.getUsername());
            resultSet.updateString(5, user.getEmail());
            resultSet.updateString(6, user.getPassword());
            resultSet.updateString(7, user.getGender());
            resultSet.updateString(9, user.getCountry());

            resultSet.rowUpdated();
            closeResourcesOpened();
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
                    + "where id in (select friend_id from FRIEND where id = '" + id + "'" + ")";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id_friend = resultSet.getString("id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String name = resultSet.getString("user_name");
                String email = resultSet.getString("mail");
                String pass = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                String country = resultSet.getString("country");
                String status = resultSet.getString("status");

                User user = new User(id_friend, name, email, fname, lname, pass, gender, country, status);

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
