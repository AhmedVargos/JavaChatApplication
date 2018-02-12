package com.chatcompany.chatserver.models;

import com.chatcompany.chatserver.controllers.ServerMainViewController;
import com.chatcompany.commonfiles.common.FriendInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import com.chatcompany.commonfiles.commModels.User;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FriendIntImp implements FriendInterface {
    
     private String property = System.getProperty("user.dir");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isResourceClosed;
    private String query;
    private ServerMainViewController controller ;

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
    public boolean sendFriendRequest(int idMe, int idMyfirend) throws RemoteException{
             if (idMe == idMyfirend) {
            return false; // nfs el idme = idmefriend
        }
        try {
            connect();
            query = "select * from User where user_id='" + idMyfirend + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!(resultSet.next())) {

                return false; // el friend m4 mawgod
            }

            query = "select * from friend_request where (user_id='" + idMe + "' and friend='" + idMyfirend + "') or "
                    + "(user='" + idMyfirend + "' and friend='" + idMe + "')";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {

                return false;// el friend mogoooood aslan
            }

            query = "select * from friend_requests where (user_id='" + idMe + "' and friend_id='" + idMyfirend + "')or"
                    + "(sender='" + idMyfirend + "' and receiver='" + idMe + "')";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {

                return false;//Constant.REQUEST_ALREADY_EXIST;
            }

            query = "insert into Requests (sender_id,receiver_id)values ('" + idMe + "','" + idMyfirend + "' )";
            statement.executeUpdate(query);

            
            
         

            return  true;  //  Constant.SENDED;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Constant.EXCEPTION;
        } finally {
            closeResourcesOpened();
        }

    }
    @Override
    public boolean acceptFriendRequest(int idMe, int idMyFriend) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM FRIEND_REQUEST WHERE receiver_id=? ") )
        {

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
           if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    idsArrayList.add(rs.getInt("sender_id"));
                }
                for (Integer idaI : idsArrayList) {
                   ///  requestsUsers.add(getUserById(id));
                }
                return requestsUsers;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return requestsUsers;
    }}
    


