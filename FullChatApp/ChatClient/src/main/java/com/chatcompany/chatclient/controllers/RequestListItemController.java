package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.FriendInterface;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import static com.chatcompany.commonfiles.commModels.Constants.REQUESTS_SERVICE;

public class RequestListItemController implements Initializable{
    @FXML
    private ImageView acceptRequestImage;
    @FXML
    private ImageView cancelRequestImage;
    @FXML
    private Circle userImage;
    @FXML
    private Text userName;

    private User mUser;

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setUserImage(String userImage) {
        Image img=new Image(userImage);
        ImagePattern imagePattern= new ImagePattern(img);
        this.userImage.setFill(imagePattern);
    }

    public void setUserName(String userName) {

        this.userName.setText(userName);
    }

    public void acceptRequest(MouseEvent mouseEvent) {
        try {
            FriendInterface friendInterface = (FriendInterface) MainApp.getServiceLoaderInterface().getServiceInstance(REQUESTS_SERVICE);
            boolean isWorking = friendInterface.acceptFriendRequest(MainApp.getMainUser().getId(),mUser.getId());
            if(isWorking){
                System.out.println("Request Is Accepted");
                MainApp.getMainChatParentController().getEmbeddedContactTabViewController().removeRequestFromList(mUser);
                
            }else {
                System.out.println("Request is not accepted");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void cancelRequest(MouseEvent mouseEvent) {
        try {
            FriendInterface friendInterface = (FriendInterface) MainApp.getServiceLoaderInterface().getServiceInstance(REQUESTS_SERVICE);
            boolean isWorking = friendInterface.removeFriendRequest(MainApp.getMainUser().getId(),mUser.getId());
            if(isWorking){
                System.out.println("Request Is Canceled");
                MainApp.getMainChatParentController().getEmbeddedContactTabViewController().removeRequestFromList(mUser);   
            }else{
                System.out.println("Error in request");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
