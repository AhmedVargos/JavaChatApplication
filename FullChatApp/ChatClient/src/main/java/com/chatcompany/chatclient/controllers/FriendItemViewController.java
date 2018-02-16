package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.Constants;
import static com.chatcompany.commonfiles.commModels.Constants.REQUESTS_SERVICE;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.FriendInterface;
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
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FriendItemViewController implements Initializable {
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
    private ImageView cancelRequestImage;
    private User mUser;

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cellPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().openNewChatSession(mUser);
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

    public void unfriendUser(MouseEvent event) {
        try {
            FriendInterface friendInterface = (FriendInterface) MainApp.getServiceLoaderInterface().getServiceInstance(REQUESTS_SERVICE);
            boolean isWorking = friendInterface.removeFriend(MainApp.getMainUser().getId(), mUser.getId());
            if (isWorking) {
                System.out.println("Friend is removed");
                MainApp.getMainChatParentController().getEmbeddedContactTabViewController().removeFriendFromList(mUser);
            } else {
                System.out.println("Error in request");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setUserConnState(int status) {
        switch (status) {
            case Constants.ONLINE:
                userConnState.setFill(Color.YELLOW);
                break;
            case Constants.OFFLINE:
                userConnState.setFill(Color.DARKCYAN);
            default:
                userConnState.setFill(Color.YELLOW);

        }

    }
    
    public void setUserAppearanceStatus(int status){
        
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
