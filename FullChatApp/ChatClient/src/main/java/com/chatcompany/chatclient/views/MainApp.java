package com.chatcompany.chatclient.views;

import com.chatcompany.chatclient.controllers.MainChatParentController;
import com.chatcompany.chatclient.models.ClientIntImp;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.LoginInterface;
import com.chatcompany.commonfiles.common.ServiceLoaderInterface;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
    private static Stage mainStage;
    //private static LoginInterface loginInterface;
    private static ServiceLoaderInterface serviceLoaderInterface;
    private static User mainUser;
    private static ClientIntImp clientIntImp;
    private static MainChatParentController mainChatParentController;
    public static ClientIntImp getClientIntImp() {
        return clientIntImp;
    }

    public static MainChatParentController getMainChatParentController() {
        return mainChatParentController;
    }

    public static void setMainChatParentController(MainChatParentController mainChatParentController) {
        MainApp.mainChatParentController = mainChatParentController;
    }

    public static void setClientIntImp(ClientIntImp clientIntImp) {
        MainApp.clientIntImp = clientIntImp;
    }
    
    public static ServiceLoaderInterface getServiceLoaderInterface(){
        return serviceLoaderInterface;
    }
    
    public static void setServiceLoaderInterface(ServiceLoaderInterface login){
        serviceLoaderInterface = login;
    }
    
    public static void setMainUser(User user){
        mainUser = user;
    }
    public static User getMainUser(){
        return mainUser;
    }
    /*
    public static LoginInterface getLoginInterface(){
        return loginInterface;
    }
    
    public static void setLoginInterface(LoginInterface login){
        loginInterface = login;
    }*/
    
    public static Stage getMainStage(){
        return mainStage;
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/IpConnection.fxml"));
        mainStage = stage;
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        //scene.getStylesheets().add("/styles/tagStyle.css");
        stage.initStyle(StageStyle.UNDECORATED);
        //stage.setWidth(850);
        //stage.setHeight(500);
        stage.setResizable(false);
        stage.setTitle("JavaFX and Maven");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
