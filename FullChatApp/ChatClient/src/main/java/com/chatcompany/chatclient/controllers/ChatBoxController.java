package com.chatcompany.chatclient.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.chatcompany.chatclient.utilities.Chat;
import com.chatcompany.chatclient.utilities.MessageHBoxStyle;
import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.chatclient.xmlHandler.XmlHandler;
import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.common.ChatInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import com.chatcompany.commonfiles.commModels.ChatSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

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
    
    Color messageColor;
    @FXML
    ImageView sendButton;
    @FXML
    ImageView saveChat;
    @FXML
    ImageView addFile;
    int size = 14;
    String fontf = "Arial";
    String hex2 = "#000000";
    String style = "normal";
    String weight = "normal";
    ChatSession chatSession;
    MessageHBoxStyle messageHBoxStyle;
    String lastSender;
    ArrayList<Message> messagesHistory = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messageHBoxStyle = new MessageHBoxStyle();
        // TODO 
        List<java.lang.String> font = Font.getFamilies();
        fontFamily.getItems().addAll(font);
        fontFamily.getSelectionModel().select(2);
        ObservableList<String> fontSizes = FXCollections.observableArrayList("8", "10", "12", "14", "18", "24", "26");
        fontSize.setItems(fontSizes);
        fontSize.getSelectionModel().select(2);
        colorPicker.setValue(Color.BLACK);

        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messageColor = colorPicker.getValue();
                hex2 = "#" + Integer.toHexString(messageColor.hashCode());
                sendTextFiled.setStyle("-fx-font-size:" + size + ";-fx-font-family:" + fontf + ";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
            }
        });
        fontSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                size = Integer.parseInt((String) fontSize.getSelectionModel().getSelectedItem());
                sendTextFiled.setStyle("-fx-font-size:" + size + ";-fx-font-family:" + fontf + ";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
            }
        });
        fontFamily.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fontf = (String) fontFamily.getSelectionModel().getSelectedItem();
                sendTextFiled.setStyle("-fx-font-size:" + size + ";-fx-font-family:" + fontf + ";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
            }
        });
        BoldToggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                weight = (BoldToggleButton.isSelected()) ? "Bold" : "normal";
                sendTextFiled.setStyle("-fx-font-size:" + size + ";-fx-font-family:" + fontf + ";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
            }
        });
        ItalicToggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                style = (ItalicToggleButton.isSelected()) ? "italic" : "normal";
                sendTextFiled.setStyle("-fx-font-size:" + size + ";-fx-font-family:" + fontf + ";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
            }
        });

        ///////////// send message
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ///send message here 
                String text = sendTextFiled.getText().toString();
                if (!text.equals(null)) {
                    /* Label outText = new Label(text);
                    outText.setTextFill(messageColor);
                    outText.setStyle("-fx-font-size:" + size + ";-fx-font-family:" + fontf + ";-fx-text-inner-color:" + hex2 + ";-fx-font-style:" + style + ";-fx-font-weight:" + weight + ";");
                    Message message = new Message(size, "String from", "String to", null, hex2, fontf, style, text, weight, false);
                    ///send message to reciever
                    HBox myHBox = new HBox();
                    myHBox.getChildren().add(outText);
                    chatVBox.getChildren().add(outText);
                     */
                    String to = "";
                    if (chatSession.getChatUsers().get(0).getUsername().equals(MainApp.getMainUser().getUsername())) {
                        to = chatSession.getChatUsers().get(1).getUsername();
                    } else {
                        to = "Other";
                    }
                    Message message = new Message(String.valueOf(size), MainApp.getMainUser().getUsername(), to, null, hex2, fontf, style, text, weight, "");

                    ChatInterface chatInterface;
                    try {
                        chatInterface = (ChatInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.CHAT_SERVICE);

                        chatInterface.sendMessage(message, chatSession);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        
        //save btn
        saveChat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    saveChatHistory();
                } catch (JAXBException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        ///////////send file
        addFile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ChatInterface chatInterface = null;
                File file = null;

                FileChooser chooser = new FileChooser();
                chooser.setTitle("Open File");
                file = chooser.showOpenDialog(new Stage());

                if (file != null) {

                    //you need functon on the server to check if the user accept to download in the client
                    //askClientReceiveFile
                    try {
                        chatInterface = (ChatInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.CHAT_SERVICE);
                        if (chatInterface.askClientReceiveFile(chatSession) == 1) {
                            FileInputStream in = new FileInputStream(file);
                            byte[] mydata = new byte[1024 * 1024];
                            int mylen = in.read(mydata);
                            while (mylen > 0) {
                                chatInterface.sendFile(file.getName(), mydata, mylen, chatSession);
                                mylen = in.read(mydata);
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        });

    }

    public void setChatSession(ChatSession chatSession) {
        this.chatSession = chatSession;

    }

    void appendText(Message msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                /*HBox myHBox = new HBox();
                Label outText = new Label(msg.getBody());
                //(int fontsSize, String from, String to, XMLGregorianCalendar date, String fontColor, String fontFamily, String fontStyle, String body, String fontWeight, Boolean underline) {

                outText.setTextFill(Color.web(msg.getFontColor()));
                outText.setStyle("-fx-font-size:" + msg.getFontsSize() + ";-fx-font-family:" + msg.getFontFamily() + ";-fx-text-inner-color:" + msg.getFontColor() + ";-fx-font-style:" + msg.getFontStyle() + ";-fx-font-weight:" + msg.getFontWeight() + ";");
                myHBox.getChildren().add(outText);
                chatVBox.getChildren().add(outText);*/
                messagesHistory.add(msg);
                lastSender = messageHBoxStyle.addMyChatLine(msg, chatVBox, lastSender);
            }
        });
    }

    public void saveChatHistory() throws JAXBException, IOException {

        // for test list    
//    List <String> test = new ArrayList<>();
//    test.add("nanan");
//    
//   // messages for test
//    Message m = new Message("shalaby", "hi mohamed ..", test, "12", "red","20-2-2012","d", "ARIAL", "bold","h");
//    Message m2 = new Message("shalaby", "hi khaled ..", test, "12", "red","20-2-2012","k", "ARIAL", "bold","f");
//    Message m3 = new Message("Ahmed",  "hi sayed ..",test, "12", "red","20-2-2012","1", "ARIAL", "bold","gf");
//    
        //chat object to contain messages
        Chat myChat = new Chat();
        myChat.getMessage().addAll(messagesHistory);

        // create fileChooser save Dialoge
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml files (*.xml)", "xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(MainApp.getMainStage());
        if (file != null) {
            // calling saving method
            XmlHandler xmlHandler = new XmlHandler();
            xmlHandler.SaveXml(file, myChat.getMessage());

        }

    }

}
