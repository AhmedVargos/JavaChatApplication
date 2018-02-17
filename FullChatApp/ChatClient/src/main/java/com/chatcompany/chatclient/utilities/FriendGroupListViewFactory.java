package com.chatcompany.chatclient.utilities;

import com.chatcompany.chatclient.controllers.FriendGroupItemViewController;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.commModels.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;

public class FriendGroupListViewFactory implements Callback<ListView<User>, ListCell<User>> {

    @Override
    public ListCell<User> call(ListView<User> param) {
        return new FriendCellFormatter();
    }

    public class FriendCellFormatter extends ListCell<User> {

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty && item.getConnStatus() != Constants.OFFLINE) {
                setGraphic(createCell(item));
            } else {
                setGraphic(null);
            }
        }

        private Parent createCell(User item) {
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader();
                root = loader.load(getClass().getResource("/fxml/FriendGroupListItemView.fxml").openStream());
                FriendGroupItemViewController listController = loader.getController();
                listController.setUserImage("/images/user.png");
                listController.setUserName(item.getUsername());
                listController.setmUser(item);
                listController.setUserConnState(item.getConnStatus());
                listController.setUserAppearanceStatus(item.getAppearanceStatus());

            } catch (IOException ex) {
                Logger.getLogger(FriendGroupListViewFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            return root;
        }
    }

}
