package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.utilities.FriendListViewFactory;
import com.chatcompany.chatclient.utilities.RequestListViewFactory;
import com.chatcompany.chatclient.views.MainApp;
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

public class ContactTabViewController implements Initializable {

    @FXML
    private Tab chatGroupTab;
    @FXML
    private Tab requestsTab;
    @FXML
    private Tab groupTab;
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
    private TextField addfirendtxtfield;

    @FXML
    VBox vbox;

    // HBox lView;
    // Text text;
    // List<User> myList;
    ObservableList<User> userRequestsList;
    private ChatAreaController chatAreaController;
    ImageView img;
    String Img = "/images/user.png";
    private ObservableList<User> userFriendsList;

    public void setChatAreaController(ChatAreaController chatAreaController) {
        this.chatAreaController = chatAreaController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ObservableList<String> classes = contactsTabPane.getStyleClass();

        friendTab.setText(null);
        friendTab.setGraphic(buildImage("/images/ic_person_white_24dp_2x.png"));

        requestsTab.setText(null);
        requestsTab.setGraphic(buildImage("/images/ic_send_white_24dp_2x.png"));

        groupTab.setText(null);
        groupTab.setGraphic(buildImage("/images/ic_group_white_24dp_2x.png"));

        chatGroupTab.setText(null);
        chatGroupTab.setGraphic(buildImage("/images/ic_group_add_white_24dp_2x.png"));

        initListViews();
        addFriends();
    }

    private void initListViews() {
        ArrayList<User> usersTemp = new ArrayList<>();
        usersTemp.add(new User("ahmed", "asd@sda.com", "asd", "adsd", "qasd", "dasd", "qqasd", "sad"));
        usersTemp.add(new User("ahmed", "asd@sda.com", "asd", "adsd", "qasd", "dasd", "qqasd", "sad"));

        userRequestsList = FXCollections.observableList(usersTemp);

        requestsList.setItems(userRequestsList);
        requestsList.setCellFactory(new RequestListViewFactory());

        ArrayList<User> usersTemp = new ArrayList<>();
        usersTemp.add(new User("ahmed", "asd@sda.com", "asd", "adsd", "qasd", "dasd", "qqasd", "sad"));
        usersTemp.add(new User("ahmed", "asd@sda.com", "asd", "adsd", "qasd", "dasd", "qqasd", "sad"));
        usersTemp.add(new User("ahmed", "asd@sda.com", "asd", "adsd", "qasd", "dasd", "qqasd", "asd"));

        userFriendsList = FXCollections.observableList(usersTemp);

        friendsList.setItems(userFriendsList);
        friendsList.setCellFactory(new FriendListViewFactory());

    }

    // Helper method to create image from image patch
    private static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch, 44, 44, false, false);
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
                //  User user = new User();
                //  user.setUsername(addfirendtxtfield.getText());
//                Label label = new Label(addfirendtxtfield.getText());
//                label.setStyle(" -fx-padding: 10px; -fx-text-fill: #C8D6CA;");
//                label.setFont(Font.font("Verdana", 20));
//                label.setLayoutX(70);
//                label.setLayoutY(5);
//              
//                AnchorPane ab = new AnchorPane();
//
//                 ab.setStyle("-fx-border-style: solid inside;"
//                        + "-fx-border-width: 2;" + "-fx-border-insets:2;"
//                        + "-fx-border-radius: 5;" + "-fx-border-color:#5c868D ;" + "-fx-background-color:  #5c868D;");
//                 ab.setMaxWidth(260);
//                 ab.setMinWidth(50);
//             
//                Circle circle = new Circle(45, 30, 20);
//                circle.setStroke(Color.SEAGREEN);
//                Image im = new Image("/images/user.png", false);
//                circle.setFill(new ImagePattern(im));
//                circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
//
//                Circle circlestatus = new Circle(15, 30, 5);
//                circlestatus.setFill(Color.LIGHTGREEN);
//                 ab.getChildren().add(circle);
//                 ab.getChildren().add(label);
//                 ab.getChildren().add(circlestatus);
//
//               // hb.setAlignment(Pos.BASELINE_LEFT);
//                //when you click open new tab.........
//                 ab.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
////                        System.out.println(addfirendtxtfield.getText());
//                        //TODO will send an object of the user clicked
//                        chatAreaController.openNewChatSession(label.getText());
//                    }
//                });
//                
//                vbox.getChildren().add(ab);

                FriendInterface friendInterface = null;
                boolean isWorking = false;
                try {
                    friendInterface = (FriendInterface) MainApp.getServiceLoaderInterface().getServiceInstance(REQUESTS_SERVICE);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                isWorking = friendInterface.sendFriendRequest(MainApp.getMainUser().getId(),addfirendtxtfield.getText());
                if(isWorking){
                    System.out.println("Request Sent");
                }else {
                    System.out.println("Request Not Sent");

                }
            }
        });
    }
    
    public void addNewRequest(ArrayList<User> request){
        //userRequestsList = FXCollections.observableList(request);
        userRequestsList.clear();
        userRequestsList.addAll(request);
        //requestsList.setItems(userRequestsList);
    }

    public void addNewFriend(ArrayList<User> friends){
        //userRequestsList = FXCollections.observableList(request);
        userFriendsList.clear();
        userFriendsList.addAll(friends);
        //requestsList.setItems(userRequestsList);
    }
}
