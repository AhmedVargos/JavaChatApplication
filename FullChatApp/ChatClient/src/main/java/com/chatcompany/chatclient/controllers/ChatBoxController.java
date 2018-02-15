package com.chatcompany.chatclient.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.chatcompany.commonfiles.commModels.Message;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class ChatBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    VBox chatVBox;
    @FXML
    TextField sendTextFiled;
    @FXML
    ColorPicker colorPicker;
    @FXML
    ComboBox<String> fontFamily;
    @FXML
    ComboBox fontSize;
    @FXML
    ToggleButton BoldToggleButton;
    @FXML
    ToggleButton ItalicToggleButton;
    @FXML
    ToggleButton UnderlineToggleButton;
    Color messageColor;
    @FXML
    ImageView sendButton;
    @FXML
    int size = 14;
    String fontf = "Arial";
    String hex2="#000000";
    String style ="normal";
    String weight="normal";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        List<java.lang.String> font = Font.getFamilies();
        fontFamily.getItems().addAll(font);
        fontFamily.getSelectionModel().select(2);
        ObservableList<String> fontSizes = FXCollections.observableArrayList("8", "10", "12", "14", "18", "24","26");
        fontSize.setItems(fontSizes);
        fontSize.getSelectionModel().select(2);
        colorPicker.setValue(Color.BLACK);
       
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messageColor = colorPicker.getValue();
                hex2 = "#" + Integer.toHexString(messageColor.hashCode());
               sendTextFiled.setStyle("-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
             }
        });
        fontSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                size = Integer.parseInt((String) fontSize.getSelectionModel().getSelectedItem());
                sendTextFiled.setStyle("-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
            }
        });
        fontFamily.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fontf = (String) fontFamily.getSelectionModel().getSelectedItem();
                sendTextFiled.setStyle("-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
           }
        });
        BoldToggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                weight = (BoldToggleButton.isSelected()) ? "Bold" : "normal";
               sendTextFiled.setStyle("-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
            }
        });
        ItalicToggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                style = (ItalicToggleButton.isSelected()) ? "italic" : "normal";
               sendTextFiled.setStyle("-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
           
            }
        });
        UnderlineToggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Boolean underline = UnderlineToggleButton.isSelected();
                if (underline) {
                   sendTextFiled.setStyle("-fx-underline:true;-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
           
                } else {
                
                   sendTextFiled.setStyle("-fx-underline:false;-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
           
                }

            }
        });
        
        ///////////// send message
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
               ///send message here 
               String text=sendTextFiled.getText().toString();
               if(!text.equals(null)){
                   Label outText=new Label(text);
                   outText.setTextFill(messageColor);
                   outText.setStyle("-fx-font-size:"+size+";-fx-font-family:"+fontf+";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
                   Message message=new Message(size,"String from","String to",null,hex2, fontf,style,text, weight,false);
               ///send message to reciever
                    HBox myHBox=new HBox();
                    myHBox.getChildren().add(outText);
                   chatVBox.getChildren().add(outText);
               }
    }});
    }
}
