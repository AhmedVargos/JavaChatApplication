package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.utilities.FriendGroupListViewFactory;
import com.chatcompany.chatclient.utilities.FriendListViewFactory;
import com.chatcompany.chatclient.utilities.RequestListViewFactory;
import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.Constants;
import static com.chatcompany.commonfiles.commModels.Constants.CHAT_SERVICE;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.FriendInterface;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import static com.chatcompany.commonfiles.commModels.Constants.REQUESTS_SERVICE;
import com.chatcompany.commonfiles.common.ChatInterface;
import com.chatcompany.commonfiles.common.LoginInterface;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;

public class ContactTabViewController implements Initializable {

    @FXML
    private Tab chatGroupTab;
    @FXML
    private Tab requestsTab;

    @FXML
    private JFXTabPane contactsTabPane;
    @FXML
    private ListView friendsList;
    @FXML
    private ListView requestsList;
    @FXML
    private Tab friendTab;

    @FXML
    private Button addfriend;
    @FXML
    private Button createGroupBtn;

    @FXML
    private TextField addfirendtxtfield;
    @FXML
    private ListView friendsInGroupList;

    @FXML
    VBox vbox;
    @FXML
    private JFXTextField groupNameTextField;
    // HBox lView;
    // Text text;
    // List<User> myList;
    ObservableList<User> userRequestsList;
    private ChatAreaController chatAreaController;
    ImageView img;
    String Img = "/images/user.png";
    private ObservableList<User> userFriendsList;
    private ArrayList<User> usersTemp;
    public ArrayList<User> listOfUsersInGroup = new ArrayList<>();

    public void setChatAreaController(ChatAreaController chatAreaController) {
        this.chatAreaController = chatAreaController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ObservableList<String> classes = contactsTabPane.getStyleClass();

        friendTab.setText(null);
        AnchorPane mPane = new AnchorPane(buildImage("/images/ic_person_white_24dp_2x.png"));
        mPane.setPadding(new Insets(2, 6, 6, 6));
        friendTab.setGraphic(mPane);

        requestsTab.setText(null);
        mPane = new AnchorPane(buildImage("/images/ic_send_white_24dp_2x.png"));
        mPane.setPadding(new Insets(2, 6, 6, 6));
        requestsTab.setGraphic(mPane);

        chatGroupTab.setText(null);
        mPane = new AnchorPane(buildImage("/images/ic_group_add_white_24dp_2x.png"));
        mPane.setPadding(new Insets(2, 6, 6, 6));
        chatGroupTab.setGraphic(mPane);

        initListViews();
        addFriends();
        createGoup();

    }

    public ArrayList<User> getListOfUsersInGroup() {
        return listOfUsersInGroup;
    }

    private void initListViews() {
        userRequestsList = FXCollections.observableArrayList();

        requestsList.setItems(userRequestsList);
        requestsList.setCellFactory(new RequestListViewFactory());

        userFriendsList = FXCollections.observableArrayList();

        friendsList.setItems(userFriendsList);
        friendsList.setCellFactory(new FriendListViewFactory());

        friendsInGroupList.setItems(userFriendsList);
        friendsInGroupList.setCellFactory(new FriendGroupListViewFactory());
    }

    // Helper method to create image from image patch
    private static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch, 36, 36, false, false);
        ImageView imageView = new ImageView();
        //You can set width and height
        //imageView.setFitHeight(16);
        //imageView.setFitWidth(16);
        imageView.setImage(i);
        return imageView;
    }

    //add new freind to contact list
    private void addFriends() {

        addfriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
          
                FriendInterface friendInterface = null;
                boolean isWorking = false;
                try {
                    friendInterface = (FriendInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.REQUESTS_SERVICE);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (ClassCastException ex) {
                    ex.printStackTrace();
                }
                try {
                    isWorking = friendInterface.sendFriendRequest(MainApp.getMainUser().getId(), addfirendtxtfield.getText());
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                if (isWorking) {
                    addfirendtxtfield.clear();
                    System.out.println("Request Sent");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Request Sent");
                    alert.setContentText("The request has been sent");
                    alert.show();
                } else {
                    System.out.println("Request Not Sent");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failed");
                    alert.setContentText("The request failed to be sent");
                    alert.show();

                }
            }
        });
    }

    public void addNewRequest(ArrayList<User> request) {
        //userRequestsList = FXCollections.observableList(request);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userRequestsList.clear();
                userRequestsList.addAll(request);

            }
        });
      }

    public void addNewFriend(ArrayList<User> friends) {
        //userRequestsList = FXCollections.observableList(request);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userFriendsList.clear();
                userFriendsList.addAll(friends);

            }
        });

   }

    public void removeRequestFromList(User mUser) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userRequestsList.remove(mUser);

            }
        });
    }

    public void removeFriendFromList(User mUser) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userFriendsList.remove(mUser);
            }
        });
    }

    private void createGoup() {
        createGroupBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(MainApp.getMainChatParentController().getEmbeddedContactTabViewController().getListOfUsersInGroup().size());
                if ((!groupNameTextField.getText().trim().isEmpty())
                        && MainApp.getMainChatParentController().getEmbeddedContactTabViewController().getListOfUsersInGroup().size() > 0) {
                    ArrayList<User> listInGroup = new ArrayList<>();
                    listInGroup.add(MainApp.getMainUser());
                    listInGroup.addAll(MainApp.getMainChatParentController().getEmbeddedContactTabViewController().getListOfUsersInGroup());
                    MainApp.getMainChatParentController().getEmbeddedChatTabsViewController().openNewGroupChatSession(listInGroup, groupNameTextField.getText().trim());
                }
            }
        });
    }
}
