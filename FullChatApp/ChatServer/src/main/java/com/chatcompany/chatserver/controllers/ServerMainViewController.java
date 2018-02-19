package com.chatcompany.chatserver.controllers;

import com.chatcompany.chatserver.models.LoginIntImp;
import com.chatcompany.chatserver.models.ServerMainIntImp;
import com.chatcompany.chatserver.models.ServiceLoaderIntImp;
import com.chatcompany.chatserver.views.ServerView;
import com.chatcompany.commonfiles.commModels.Constants;
import static com.chatcompany.commonfiles.commModels.Constants.REGISTRY_PORT;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.ClientInterface;
import com.jfoenix.controls.JFXButton;
import java.net.MalformedURLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import java.net.URL;
import java.rmi.Naming;
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
import javafx.scene.control.TextArea;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class ServerMainViewController implements Initializable {

    @FXML
    public ImageView serverViewClose;

    @FXML
    private TextArea annoucementTextArea;
    @FXML
    private JFXButton sendAnnouncementBtn;

    @FXML
    public ImageView serverViewMin;
    @FXML
    private PieChart pc;

    @FXML
    private PieChart pc1;

    @FXML
    public ToggleButton serverStartBtn;
    @FXML
    public Button serverStopBtn;

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
    Scene s;

    private Registry registry;
    private final String CHAT_TAG = "chat";
    boolean firstTable = true;
    boolean firstTimeRegistery = true;
    private String SERVER_IP = "192.168.1.6";

    public void setScene(Scene scene) {
        this.s = scene;
    }

    public PieChart getPc() {
        return pc;
    }

    private String property = System.getProperty("user.dir");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isResourceClosed;
    private String query;

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
        sendAnnouncement();

        serverStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (serverStartBtn.isSelected()) {
                    startServer();
                    serverStartBtn.setText("Stop");
                } else {
                    try {
                        stopServer();
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    serverStartBtn.setText("Start");
                }
            }
        });

        pie();
        fillTabel();

    }

    @FXML
    public void pie() {
        Platform.runLater(() -> {
            int m, f, on, off;
            on = off = m = f = 0;

            try {
                connect();
                String SQL = "SELECT * from user where gender = " + Constants.MALE;
                String sql = "SELECT * from user where gender = " + Constants.FEMALE;
                String SQL1 = "SELECT * from user where connecting_status = 1";
                String sql1 = "SELECT * from user where connecting_status = 0";
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(SQL);
                statement = connection.createStatement();
                ResultSet rs1 = statement.executeQuery(sql);
                statement = connection.createStatement();
                ResultSet rs2 = statement.executeQuery(SQL1);
                statement = connection.createStatement();
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
                statement.close();
//                int tm = (m * 100) / (m + f);
//                int tf = 100 - tm;
//                int ton = (on * 100) / (on + off);
//                int toff = 100 - ton;
                ObservableList<PieChart.Data> MFDATA
                        = FXCollections.observableArrayList(
                                new PieChart.Data("MALE", m / (m + f)),
                                new PieChart.Data("FEMALE", f / (m + f))
                        );
                pc.setData(MFDATA);
                pc.setTitle("Gender");

                ObservableList<PieChart.Data> ONOFF
                        = FXCollections.observableArrayList(
                                new PieChart.Data("OFFLINE", off / (off + on)),
                                new PieChart.Data("ONLINE", on / (off + on))
                        );
                pc1.setData(ONOFF);
                pc1.setTitle("Status");
            } catch (Exception e) {
                Platform.runLater(() -> {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("se Chat ");
                    alert.setContentText("Cannot access database at this moment");
                    alert.showAndWait();
                });
            }

        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        pie();
    }

    @FXML
    private void handleButtonTableRefeshAction(ActionEvent event) {
        for (int i = 0; i < table.getItems().size(); i++) {
            table.getItems().clear();
            table.getColumns().clear();
        }
        fillTabel();
    }

    public void fillTabel() {

        //table.setEditable(true);
        for (int i = 0; i < table.getItems().size(); i++) {
            table.getItems().clear();
            table.getColumns().clear();
        }

        String SQL = "SELECT * from user";
        data = FXCollections.observableArrayList();
        //table.setEditable(true);
        try {
            connect();

            ResultSet rs = statement.executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                if (i == 0 || i == 5 || i == 7 || i == 9) {
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
    public void stopServer() throws SQLException {
        try {
            registry.unbind(CHAT_TAG);
            serverClosing();
            ServerView.getClientsOnline().clear();

        } catch (RemoteException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void serverClosing() throws SQLException, RemoteException {
        try {
            connect();
            query = "UPDATE USER SET connecting_status =" + Constants.OFFLINE;
            statement = connection.createStatement();
            statement.executeUpdate(query);
            closeResourcesOpened();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //starts the server
    private void startServer() {
        try {

            if (!firstTimeRegistery) {
                registry = LocateRegistry.getRegistry(REGISTRY_PORT);
                System.setProperty("java.rmi.server.hostname", SERVER_IP);
                registry.rebind(CHAT_TAG, new ServiceLoaderIntImp());
                //Naming.rebind("//192.168.1.6/chat", new ServerMainIntImp());               
                System.out.println("Server is Online");
                System.out.println("Already created");

            } else {
                firstTimeRegistery = false;
                registry = LocateRegistry.createRegistry(REGISTRY_PORT);
                System.setProperty("java.rmi.server.hostname", SERVER_IP);
                registry.rebind(CHAT_TAG, new ServiceLoaderIntImp());
                //Naming.rebind("//192.168.1.6/chat", new ServerMainIntImp());
                System.out.println("Server is Online");
                System.out.println("New created");

            }

        } catch (RemoteException ex) {
            //Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private void sendAnnouncement() {
        sendAnnouncementBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!annoucementTextArea.getText().isEmpty()) {
                    for (ClientInterface clientInterface : ServerView.getClientsOnline().values()) {
                        try {
                            clientInterface.makeNotification(Constants.ANNOUNCEMENT, annoucementTextArea.getText());
                        } catch (RemoteException ex) {
                            Logger.getLogger(ServerMainViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
}
