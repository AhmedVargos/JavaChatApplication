package com.chatcompany.chatclient.utilities;

import com.chatcompany.chatclient.controllers.RequestListItemController;
import com.chatcompany.commonfiles.commModels.User;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestListViewFactory implements Callback<ListView<User>,ListCell<User>> {
    @Override
    public ListCell<User> call(ListView<User> param) {
        return new RequestCellFormatter();
    }

    public class RequestCellFormatter extends ListCell<User>{

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty);
            if(item != null && !empty){
                setGraphic(createCell(item));
            }else {
                setGraphic(null);
            }
        }

        private Parent createCell(User item) {
            Parent root = null;
            try{
                FXMLLoader loader=new FXMLLoader();
                root = loader.load(getClass().getResource("/fxml/RequestListItemView.fxml").openStream());
                RequestListItemController listItemController=loader.getController();
                listItemController.setUserImage("/images/user.png");
                listItemController.setUserName(item.getUsername());

            }catch (IOException ex) {
                Logger.getLogger(RequestListViewFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            return root;
        }
    }
}
