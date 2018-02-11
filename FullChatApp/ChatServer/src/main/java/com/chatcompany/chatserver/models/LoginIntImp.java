package com.chatcompany.chatserver.models;

import com.chatcompany.commonfiles.common.LoginInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import com.chatcompany.commonfiles.commModels.User;
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
    public User login(String userName, String pass) throws RemoteException {
        User user = null;

        try {
            System.out.println("Is in signin");
           
            connect();
            //+ "'and password='" + pass
            query = "select * from USER where user_name = '" + userName + "'"+ " and password='" + pass + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            System.out.println("Is in signin");
            if (resultSet.next()) {
            System.out.println("SIGNIN AND CREATEING OBJ");
                //TODO fix the return obj
                String name = resultSet.getString("user_name");
                String email = resultSet.getString("mail");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
               
                int gender = resultSet.getInt("gender");
                String country = resultSet.getString("country");
                user = new User(name, fname, lname, String.valueOf(gender), country);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResourcesOpened();
            return user;
        }

    }

    @Override
    public synchronized Boolean SignUp(User user) throws SQLException, RemoteException {
        try {
            System.out.println("Is in signup");
            connect();
            query = "select * from USER where user_name = '" + user.getUsername() + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                closeResourcesOpened();
                return false;
            } else {
                
            System.out.println("Is inserting row");
                //,connecting_status,appearance_status
                query = "insert into USER (fname,lname,user_name,mail,password,gender,country) values('" + user.getFname()
                        + "','" +user.getLname() + "','" + user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword() + "','"
                        + Integer.valueOf(user.getGender()) + "','" + user.getCountry() + "')";
                statement.executeUpdate(query);
                //add in table
            

                closeResourcesOpened();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
