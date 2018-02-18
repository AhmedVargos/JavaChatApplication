/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.utilities;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.Message;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author Vargos
 */
public class MessageHBoxStyle {

    public static final String MY_LINE_MIDDLE = "myLineMiddle";
    public static final String MY_LINE_LAST = "myLineLast";
    public static final String MY_LINE_TOP = "myLineTop";
    public static final String MY_LINE_ALONE = "myLineAlone";
    public static final String MOVE_RIGHT = "moveRight";
    public static final String MOVE_LEFT = "moveLeft";
    public static final String OTHER_LINE_LAST = "otherLineLast";
    public static final String OTHER_LINE_MIDDLE = "otherLineMiddle";
    public static final String OTHER_LINE_TOP = "otherLineTop";
    public static final String OTHER_LINE_ALONE = "otherLineAlone";
    public static final String PIC_CIRLCE = "picCirlce";

    public String addMyChatLine(Message message, VBox chatBox, String lastSenderUser) {
        if (message.getFrom().equals(MainApp.getMainUser().getUsername())) {
            lastSenderUser = MainApp.getMainUser().getUsername();
            if (!chatBox.getChildren().isEmpty()) {
                Node preParentNode = chatBox.getChildren().get(chatBox.getChildren().size() - 1);
                Node preNode = ((HBox) preParentNode).getChildren().get(0);
                ObservableList<String> preStyleText = preNode.getStyleClass();
                int type = -1;
                for (String name
                        : preStyleText) {
                    switch (name) {
                        case MY_LINE_ALONE:
                            type = 1;
                            break;
                        case MY_LINE_TOP:
                            type = 2;
                            break;
                        case MY_LINE_LAST:
                            type = 3;
                            break;
                        case MY_LINE_MIDDLE:
                            type = 4;
                            break;
                        default:
                            type = -1;

                    }

                }
                Label displayedText;
                HBox hBox;
                switch (type) {
                    case 1:
                        displayedText = makeTextLabel(message);
                        preNode.getStyleClass().remove(MY_LINE_ALONE);
                        preNode.getStyleClass().add(MY_LINE_TOP);
                        displayedText.getStyleClass().add(MY_LINE_LAST);
                        displayedText.setWrapText(true);
                        displayedText.setPadding(new Insets(6, 6, 6, 10));
                        hBox = new HBox();
                        hBox.getChildren().add(displayedText);
                        hBox.getStyleClass().add(MOVE_RIGHT);
                        //sentMessageField.clear();

//                    VBox.setMargin(hBox, new Insets(0, 0, 4, 0));
//                    scrollPaneObj.vvalueProperty().bind(chatBoxView.heightProperty());
                        chatBox.setSpacing(4);
                        chatBox.getChildren().addAll(hBox);

                        break;
                    case 2:

                        displayedText = makeTextLabel(message);
                        displayedText.getStyleClass().add(MY_LINE_LAST);
                        displayedText.setWrapText(true);
                        displayedText.setPadding(new Insets(6, 6, 6, 10));
                        hBox = new HBox();
                        hBox.getChildren().add(displayedText);
                        hBox.getStyleClass().add(MOVE_RIGHT);
                        //sentMessageField.clear();

                        chatBox.setSpacing(4);
                        chatBox.getChildren().addAll(hBox);

                        break;
                    case 3:
                        preNode.getStyleClass().remove(MY_LINE_LAST);
                        preNode.getStyleClass().add(MY_LINE_MIDDLE);
                        displayedText = makeTextLabel(message);
                        displayedText.getStyleClass().add(MY_LINE_LAST);
                        displayedText.setPadding(new Insets(6, 6, 6, 10));
                        displayedText.setWrapText(true);
                        hBox = new HBox();
                        hBox.getChildren().add(displayedText);
                        hBox.getStyleClass().add(MOVE_RIGHT);
                        //sentMessageField.clear();

                        chatBox.setSpacing(4);
                        chatBox.getChildren().addAll(hBox);

                        break;
                    default:
                        displayedText = makeTextLabel(message);
                        displayedText.getStyleClass().add(MY_LINE_ALONE);

                        displayedText.setPadding(new Insets(6, 6, 6, 10));
                        hBox = new HBox();
                        hBox.getChildren().add(displayedText);
                        hBox.getStyleClass().add(MOVE_RIGHT);
                        //sentMessageField.clear();
                        chatBox.getChildren().addAll(hBox);
                        break;
                }
            } else {
                Label displayedText = makeTextLabel(message);
                displayedText.getStyleClass().add(MY_LINE_ALONE);
                displayedText.setPadding(new Insets(6, 6, 6, 10));
                displayedText.setWrapText(true);
                HBox hBox = new HBox();
                hBox.getChildren().add(displayedText);
                hBox.getStyleClass().add(MOVE_RIGHT);
                //sentMessageField.clear();

                //scrollPaneObj.vvalueProperty().bind(chatBoxView.heightProperty());
                chatBox.setSpacing(4);
                chatBox.getChildren().addAll(hBox);

            }
            return lastSenderUser;
        } else {
            if (!chatBox.getChildren().isEmpty()) {
                Node preParentNode = chatBox.getChildren().get(chatBox.getChildren().size() - 1);
                Node preNode;
                if (((HBox) preParentNode).getChildren().size() > 1) {
                    preNode = ((HBox) preParentNode).getChildren().get(1);
                } else {
                    preNode = ((HBox) preParentNode).getChildren().get(0);
                }
                ObservableList<String> preStyleText = preNode.getStyleClass();
                int type = -1;
                for (String name
                        : preStyleText) {
                    switch (name) {
                        case OTHER_LINE_ALONE:
                            type = 1;
                            break;
                        case OTHER_LINE_MIDDLE:
                            type = 3;
                            break;
                        case OTHER_LINE_LAST:
                            type = 4;
                            break;
                        default:
                            type = -1;

                    }

                }
                Label labelUserTag;
                Label displayedText;
                HBox hBox;
                Image im;
                Circle picCircle;
                switch (type) {
                    case 1:
                        if ((!lastSenderUser.equals(message.getFrom())) && !lastSenderUser.isEmpty()) {
                            labelUserTag = new Label(message.getFrom());
                            labelUserTag.getStyleClass().add("tag");
                            HBox hBoxLabel = new HBox();
                            hBoxLabel.getChildren().add(labelUserTag);
                            hBoxLabel.getStyleClass().add(MOVE_LEFT);
                            chatBox.getChildren().add(hBoxLabel);
                        }
                        if (((HBox) preParentNode).getChildren().size() > 1) {

                            if (((HBox) preParentNode).getChildren().get(0).getId().equals(PIC_CIRLCE)) {
                                ((HBox) preParentNode).getChildren().get(0).setVisible(false);
                            }
                        }

                        ((HBox) preParentNode).getChildren().get(1).getStyleClass().remove(OTHER_LINE_ALONE);
                        ((HBox) preParentNode).getChildren().get(1).getStyleClass().add(OTHER_LINE_TOP);
                        picCircle = new Circle();
                        picCircle.setId(PIC_CIRLCE);
                        picCircle.setRadius(10.0);
                        picCircle.setStroke(Color.LIGHTCYAN);
                        im = new Image("file:/E:/ITI%20files/JAVA%20FX/lab1%20chat/chatWIthFXML/images/1.jpg", false);
                        picCircle.setFill(new ImagePattern(im));

                        displayedText = makeTextLabel(message);
                        displayedText.setWrapText(true);
                        displayedText.getStyleClass().add(OTHER_LINE_LAST);
                        displayedText.setPadding(new Insets(6, 10, 6, 6));
                        hBox = new HBox();
                        hBox.getChildren().addAll(picCircle, displayedText);
                        hBox.getStyleClass().add(MOVE_LEFT);
                        //sentMessageField.clear();

//                    VBox.setMargin(hBox, new Insets(0, 0, 4, 0));
//                    scrollPaneObj.vvalueProperty().bind(chatBoxView.heightProperty());
                        chatBox.setSpacing(4);
                        chatBox.getChildren().addAll(hBox);

                        break;
                    case 4:

                        if ((!lastSenderUser.equals(message.getFrom())) && !lastSenderUser.isEmpty()) {
                            labelUserTag = new Label(message.getFrom());
                            labelUserTag.getStyleClass().add("tag");
                            HBox hBoxLabel = new HBox();
                            hBoxLabel.getChildren().add(labelUserTag);
                            hBoxLabel.getStyleClass().add(MOVE_LEFT);
                            chatBox.getChildren().add(hBoxLabel);
                        }

                        if (((HBox) preParentNode).getChildren().size() > 1) {

                            if (((HBox) preParentNode).getChildren().get(0).getId().equals(PIC_CIRLCE)) {
                                ((HBox) preParentNode).getChildren().get(0).setVisible(false);;
                            }
                        }

//                        preNode.getStyleClass().remove(OTHER_LINE_LAST);
//                        preNode.getStyleClass().add(OTHER_LINE_MIDDLE);
                        ((HBox) preParentNode).getChildren().get(1).getStyleClass().remove(OTHER_LINE_LAST);
                        ((HBox) preParentNode).getChildren().get(1).getStyleClass().add(OTHER_LINE_MIDDLE);
                        picCircle = new Circle();
                        picCircle.setId(PIC_CIRLCE);
                        picCircle.setRadius(10.0);
                        picCircle.setStroke(Color.LIGHTCYAN);
                        im = new Image("file:/E:/ITI%20files/JAVA%20FX/lab1%20chat/chatWIthFXML/images/1.jpg", false);
                        picCircle.setFill(new ImagePattern(im));

                        displayedText = makeTextLabel(message);
                        displayedText.getStyleClass().add(OTHER_LINE_LAST);
                        displayedText.setWrapText(true);
                        displayedText.setPadding(new Insets(6, 10, 6, 6));
                        hBox = new HBox();
                        hBox.getChildren().addAll(picCircle, displayedText);
                        hBox.getStyleClass().add(MOVE_LEFT);
                        //sentMessageField.clear();

                        chatBox.setSpacing(4);
                        chatBox.getChildren().addAll(hBox);

                        break;
                    case 3:

                        if ((!lastSenderUser.equals(message.getFrom())) && !lastSenderUser.isEmpty()) {
                            labelUserTag = new Label(message.getFrom());
                            labelUserTag.getStyleClass().add("tag");
                            HBox hBoxLabel = new HBox();
                            hBoxLabel.getChildren().add(labelUserTag);
                            hBoxLabel.getStyleClass().add(MOVE_LEFT);
                            chatBox.getChildren().add(hBoxLabel);
                        }

                        if (((HBox) preParentNode).getChildren().size() > 1) {

                            if (((HBox) preParentNode).getChildren().get(0).getId().equals(PIC_CIRLCE)) {
                                ((HBox) preParentNode).getChildren().get(0).setVisible(false);
                            }
                        }

                        ((HBox) preParentNode).getChildren().get(1).getStyleClass().remove(OTHER_LINE_LAST);
                        ((HBox) preParentNode).getChildren().get(1).getStyleClass().add(OTHER_LINE_MIDDLE);

                        picCircle = new Circle();
                        picCircle.setId(PIC_CIRLCE);
                        picCircle.setRadius(10.0);
                        picCircle.setStroke(Color.LIGHTCYAN);
                        im = new Image("file:/E:/ITI%20files/JAVA%20FX/lab1%20chat/chatWIthFXML/images/1.jpg", false);
                        picCircle.setFill(new ImagePattern(im));

                        displayedText = makeTextLabel(message);
                        displayedText.getStyleClass().add(OTHER_LINE_LAST);
                        displayedText.setWrapText(true);
                        displayedText.setPadding(new Insets(6, 10, 6, 6));
                        hBox = new HBox();
                        hBox.getChildren().addAll(picCircle, displayedText);
                        hBox.getStyleClass().add(MOVE_LEFT);
                        //sentMessageField.clear();

                        chatBox.setSpacing(4);
                        chatBox.getChildren().addAll(hBox);

                        break;
                    default:

                        if ((!lastSenderUser.equals(message.getFrom())) && !lastSenderUser.isEmpty()) {
                            labelUserTag = new Label(message.getFrom());
                            labelUserTag.getStyleClass().add("tag");
                            HBox hBoxLabel = new HBox();
                            hBoxLabel.getChildren().add(labelUserTag);
                            hBoxLabel.getStyleClass().add(MOVE_LEFT);
                            chatBox.getChildren().add(hBoxLabel);

                        }
//                        Node isPicNode1 =(Circle) ((HBox) preNode).getChildren().get(0);

                        if (((HBox) preParentNode).getChildren().size() > 1) {

                            if (((HBox) preParentNode).getChildren().get(0).getId().equals(PIC_CIRLCE)) {
                                ((HBox) preParentNode).getChildren().get(0).setVisible(false);
                            }
                        }

                        displayedText = makeTextLabel(message);
                        displayedText.getStyleClass().add(OTHER_LINE_ALONE);
                        displayedText.setWrapText(true);
                        displayedText.setPadding(new Insets(6, 10, 6, 6));

                        picCircle = new Circle();
                        picCircle.setId(PIC_CIRLCE);
                        picCircle.setRadius(10.0);
                        picCircle.setStroke(Color.LIGHTCYAN);
                        im = new Image("file:/E:/ITI%20files/JAVA%20FX/lab1%20chat/chatWIthFXML/images/1.jpg", false);
                        picCircle.setFill(new ImagePattern(im));
                        hBox = new HBox();
                        hBox.getChildren().addAll(picCircle, displayedText);
                        hBox.getStyleClass().add(MOVE_LEFT);
                        //sentMessageField.clear();
                        chatBox.getChildren().addAll(hBox);
                        break;
                }
            } else {
                Label labelUserTag = new Label(message.getFrom());
                labelUserTag.getStyleClass().add("tag");
                HBox hBoxLabel = new HBox();

                hBoxLabel.getChildren().add(labelUserTag);
                hBoxLabel.getStyleClass().add(MOVE_LEFT);

                Label displayedText = makeTextLabel(message);
                displayedText.getStyleClass().add(OTHER_LINE_ALONE);
                displayedText.setPadding(new Insets(6, 10, 6, 6));
                displayedText.setWrapText(true);
                Circle picCircle = new Circle();
                picCircle.setId(PIC_CIRLCE);
                picCircle.setRadius(10.0);
                picCircle.setStroke(Color.LIGHTCYAN);
                Image im = new Image("file:/E:/ITI%20files/JAVA%20FX/lab1%20chat/chatWIthFXML/images/1.jpg", false);
                picCircle.setFill(new ImagePattern(im));

                HBox hBox = new HBox();
                hBox.getChildren().addAll(picCircle, displayedText);
                hBox.getStyleClass().add(MOVE_LEFT);
                //sentMessageField.clear();

                //scrollPaneObj.vvalueProperty().bind(chatBoxView.heightProperty());
                chatBox.setSpacing(4);
                chatBox.getChildren().addAll(hBoxLabel, hBox);

            }

            lastSenderUser = message.getFrom();
            return lastSenderUser;
        }

    }
    
    private Label makeTextLabel(Message msg){
        Label outText = new Label(msg.getBody());
                //(int fontsSize, String from, String to, XMLGregorianCalendar date, String fontColor, String fontFamily, String fontStyle, String body, String fontWeight, Boolean underline) {

                outText.setTextFill(Color.web(msg.getFontColor()));
                outText.setStyle("-fx-font-size:" + msg.getFontSize() + ";-fx-font-family:" + msg.getFontFamily() + ";-fx-text-inner-color:" + msg.getFontColor() + ";-fx-font-style:" + msg.getFontStyle() + ";-fx-font-weight:" + msg.getFontWeight() + ";");
                return outText;
    }

}
