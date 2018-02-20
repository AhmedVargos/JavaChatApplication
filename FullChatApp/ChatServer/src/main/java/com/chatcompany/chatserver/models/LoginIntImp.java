package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.views.ServerView;
import com.chatcompany.commonfiles.commModels.Constants;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.ClientInterface;
import com.chatcompany.commonfiles.common.LoginInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginIntImp extends UnicastRemoteObject implements LoginInterface {

    private String property = System.getProperty("user.dir");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isResourceClosed;
    private String query;

    public LoginIntImp() throws RemoteException {

    }

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
    public synchronized User login(String userName, String pass, ClientInterface clientInterface) throws RemoteException {
        User user = null;

        try {
            System.out.println("Is in signin");

            connect();
            //+ "'and password='" + pass
            query = "select * from USER where user_name = '" + userName + "'" + " and password='" + pass + "'" + " and connecting_status !='1'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            System.out.println("Is in signin");
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String name = resultSet.getString("user_name");
                String email = resultSet.getString("mail");
                String password = resultSet.getString("password");
                int gender = resultSet.getInt("gender");
                String country = resultSet.getString("country");
                int appStatus = resultSet.getInt("appearance_status");

                user = new User(id, name, email, fname, lname, password, gender, country, Constants.ONLINE, appStatus);
                ServerView.getClientsOnline().put(user.getId(), clientInterface);

                /*query = "UPDATE USER SET connecting_status =" + user.getConnStatus() + ", appearance_status=" + user.getAppearanceStatus() + " WHERE id =" + user.getId();
                //query = "select * from USER where id = '" + user.getId() + "'";
                statement = connection.createStatement();
                statement.executeUpdate(query);*/
                closeResourcesOpened();
                new ServerMainIntImp().updateInfo(user);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResourcesOpened();
            return user;
        }

    }

    @Override
    public synchronized User SignUp(User user, ClientInterface clientInterface) throws SQLException, RemoteException {
        User newUser = new User();

        try {
            System.out.println("Is in signup");
            connect();
            query = "select * from USER where user_name = '" + user.getUsername() + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                closeResourcesOpened();
                return newUser;
            } else {

                System.out.println("Is inserting row");
                //,connecting_status,appearance_status
                query = "insert into USER (fname,lname,user_name,mail,password,gender,country,connecting_status,appearance_status) values('" + user.getFname()
                        + "','" + user.getLname() + "','" + user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword() + "','"
                        + user.getGender() + "','" + user.getCountry() + "',1,1)";
                statement.executeUpdate(query);
                //add in table

                //select user to get there id
                query = "select * from USER where user_name = '" + user.getUsername() + "'";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                int id = resultSet.getInt("id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String name = resultSet.getString("user_name");
                String email = resultSet.getString("mail");
                String pass = resultSet.getString("password");
                int gender = resultSet.getInt("gender");
                String country = resultSet.getString("country");
                int connStatus = resultSet.getInt("connecting_status");
                int appStatus = resultSet.getInt("appearance_status");

                newUser = new User(id, name, email, fname, lname, pass, gender, country, connStatus, appStatus);
                ServerView.getClientsOnline().put(user.getId(), clientInterface);
                clientInterface.makeNotification("Welcome", "Welcome to the RnChat Application!");
                closeResourcesOpened();
                return newUser;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return newUser;
    }
}
