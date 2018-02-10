package com.chatcompany.chatclient.controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ContactTabViewController implements Initializable{
    @FXML
    public Tab chatGroupTab;
    @FXML
    public Tab requestsTab;
    @FXML
    public Tab groupTab;
    public JFXTabPane contactsTabPane;
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

    ImageView img;
    String Img = "/images/user.png";

    
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
        
        addFriends();
    }

    // Helper method to create image from image patch
    private static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch,44,44,false,false);
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
                Label label = new Label(addfirendtxtfield.getText());
                label.setStyle(" -fx-padding: 10px; -fx-text-fill:#5c868D;");
                label.setFont(Font.font("Verdana", 20));
                label.setLayoutX(70);
                label.setLayoutY(5);
              
                AnchorPane ab = new AnchorPane();

                 ab.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets:2;"
                        + "-fx-border-radius: 5;" + "-fx-border-color:#5c868D ;" + "-fx-background-color: #C8D6CA;");
                 ab.setMaxWidth(260);
                 ab.setMinWidth(50);
                 System.out.println(ab.getWidth());

                Circle circle = new Circle(45, 30, 20);
                circle.setStroke(Color.SEAGREEN);
                Image im = new Image("/images/user.png", false);
                circle.setFill(new ImagePattern(im));
                circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

                Circle circlestatus = new Circle(15, 30, 5);
                circlestatus.setFill(Color.LIGHTGREEN);
                 ab.getChildren().add(circle);
                 ab.getChildren().add(label);
                 ab.getChildren().add(circlestatus);

               // hb.setAlignment(Pos.BASELINE_LEFT);
                //when you click open new tab.........
                 ab.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println(addfirendtxtfield.getText());
                    }
                });
                
                vbox.getChildren().add(ab);

            }
        });

    }
}
