package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.Constants;
import static com.chatcompany.commonfiles.commModels.Constants.REQUESTS_SERVICE;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.FriendInterface;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FriendGroupItemViewController implements Initializable {

    @FXML
    private Text appearanceStatusText;
    @FXML
    private AnchorPane cellPane;
    @FXML
    private Circle userImage;
    @FXML
    private Text userName;
    @FXML
    private Circle userConnState;
    @FXML
    private User mUser;
    @FXML
    private JFXCheckBox isInGroupCheckBtn;

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        isInGroupCheckBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    MainApp.getMainChatParentController().getEmbeddedContactTabViewController().getListOfUsersInGroup().add(mUser);
                }else{
                
                    MainApp.getMainChatParentController().getEmbeddedContactTabViewController().getListOfUsersInGroup().remove(mUser);
                }
            }
        });
       
    }

    public void setUserImage(String userImage) {
        Image img = new Image(userImage);
        ImagePattern imagePattern = new ImagePattern(img);
        this.userImage.setFill(imagePattern);
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setUserConnState(Color color) {
        this.userConnState.setStroke(color);
    }

    public void setUserConnState(int status) {
        switch (status) {
            case Constants.ONLINE:
                userConnState.setFill(Color.YELLOW);
                break;
            case Constants.OFFLINE:
                userConnState.setFill(Color.GRAY);
            default:
                userConnState.setFill(Color.GRAY);

        }

    }

    public void setUserAppearanceStatus(int status) {

        switch (status) {
            case Constants.AVAILABLE:
                appearanceStatusText.setText("Available");
                break;
            case Constants.BUSY:
                appearanceStatusText.setText("Busy");
                break;
            case Constants.NOT_AVAILABLE:
                appearanceStatusText.setText("Away");
                break;
            default:
                appearanceStatusText.setText("Available");

        }
    }
}
