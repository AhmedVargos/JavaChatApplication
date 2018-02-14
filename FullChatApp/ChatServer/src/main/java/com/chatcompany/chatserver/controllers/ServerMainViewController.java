package com.chatcompany.chatserver.controllers;


import com.chatcompany.chatserver.models.LoginIntImp;
import com.chatcompany.chatserver.models.ServiceLoaderIntImp;
import static com.chatcompany.commonfiles.commModels.Constants.REGISTRY_PORT;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ServerMainViewController implements Initializable {

     private double xOffset, yOffset;
    @FXML
    public ImageView serverViewMin;
    @FXML
    public ImageView serverViewClose;
    @FXML
    public Button serverStartBtn;
    @FXML
    public Button serverStopBtn;
    @FXML
    private PieChart pc;
    
    @FXML
    private PieChart pc1;
    @FXML
    private ObservableList<ObservableList> data;
    /**
     * table view
     */
    @FXML
    private TableView<ObservableList> table;

    @FXML
    Tab mainTab;
    @FXML
    Tab statisticsTab;
    @FXML
    Tab userDataTab;
    private Registry registry; 
    private final String CHAT_TAG = "chat";
    boolean firstTable = true;
    
    private String property = System.getProperty("user.dir");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isResourceClosed;
    private String query;

//    public LoginIntImp() throws RemoteException {
//
//    }

    private void connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + property + "\\chatDatabase.db";
        
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url, "", "");
            statement = connection.createStatement();
            isResourceClosed = false;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    /**
     * close connection to database
     */
    private void closeResourcesOpened() {
        try {
            if (!isResourceClosed) {
                //resultSet.close();
                statement.close();
                connection.close();
                isResourceClosed = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize handlers to the buttons
        close();
        minimize();

        serverStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startServer();
            }
        });


        serverStopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopServer();
            }
        });
    }

     @FXML
    public void pie(ActionEvent event) {
        Platform.runLater(() -> {
            int m, f, on, off;
            on = off = m = f = 0;

            try {
                connect();
                String SQL = "SELECT * from user where gender = 'M' ";
                String sql = "SELECT * from user where gender = 'F' ";
                String SQL1 = "SELECT * from user where connecting_status = 'ONLINE' ";
                String sql1 = "SELECT * from user where connecting_status = 'OFFLINE' ";
                ResultSet rs = statement.executeQuery(SQL);
                ResultSet rs1 = statement.executeQuery(sql);
                ResultSet rs2 = statement.executeQuery(SQL1);
                ResultSet rs3 = statement.executeQuery(sql1);
                while (rs.next()) {
                    m++;
                }
                while (rs1.next()) {
                    f++;
                }
                while (rs2.next()) {
                    on++;
                }
                while (rs3.next()) {
                    off++;
                }
                int tm = (m * 100) / (m + f);
                int tf = 100 - tm;
                int ton = (on * 100) / (on + off);
                int toff = 100 - ton;
                ObservableList<PieChart.Data> MFDATA
                        = FXCollections.observableArrayList(
                                new PieChart.Data("MALE", tm),
                                new PieChart.Data("FEMALE", tf)
                        );
                pc.setData(MFDATA);

                ObservableList<PieChart.Data> ONOFF
                        = FXCollections.observableArrayList(
                                new PieChart.Data("OFFLINE", toff),
                                new PieChart.Data("ONLINE", ton)
                        );
                pc1.setData(ONOFF);
            } catch (Exception e) {
                Platform.runLater(() -> {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Cloud Chat ");
                    alert.setContentText("Cannot access database at this moment");
                    alert.showAndWait();
                });
            }

        });
    }

     public void fillTabel() {

        for (int i = 0; i < table.getItems().size(); i++) {
            table.getItems().clear();
            table.getColumns().clear();
        }

        String SQL = "SELECT * from user";
        data = FXCollections.observableArrayList();
        try  {
            connect();

            ResultSet rs = statement.executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                if (i == 8) {
                    continue;
                }
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(col);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            firstTable = false;
            table.setItems(data);
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("SE Chat ");
                alert.setContentText("Cannot access database at this moment");
                alert.showAndWait();
            });
        }

    }

    //stops the server
    private void stopServer() {
        try {
            registry.unbind(CHAT_TAG);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //starts the server
    private void startServer() {
        try {
            registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            
            registry.rebind(CHAT_TAG, new ServiceLoaderIntImp());

            System.out.println("Server is Online");

        } catch (RemoteException ex) {
            //Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    //close window will close all connections
    private void close() {
        serverViewClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //Platform.exit();
                System.exit(0);
            }
        });
    }
  // to refresh table
      public void refreshTable() {
        for (int i = 0; i < table.getItems().size(); i++) {
            table.getItems().clear();
            table.getColumns().clear();
        }
        fillTabel();
    }
    //minimize the screen
    private void minimize() {
        serverViewMin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ((Stage)serverViewMin.getScene().getWindow()).setIconified(true);
            }
        });
    }
}
