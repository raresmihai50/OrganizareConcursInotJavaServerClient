package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.LogInViewController;
import org.example.gui.OrganizerListModel;
import org.example.gui.OrganizerViewController;
import org.example.jsonprotocol.ServicesJsonProxy;

import java.io.IOException;
import java.util.Properties;

import static javafx.application.Application.launch;

public class StartClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new ServicesJsonProxy(serverIP, serverPort);



        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login-view.fxml"));
        Parent root=loader.load();


        LogInViewController ctrl =
                loader.<LogInViewController>getController();
        ctrl.initLoginController(server);




        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("organizer-view.fxml"));
        Parent croot=cloader.load();


        OrganizerViewController orgCtrl =
                cloader.<OrganizerViewController>getController();
        orgCtrl.setServer(server);

        ctrl.setOrganizerViewController(orgCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("MPP server/client");
        primaryStage.setScene(new Scene(root, 600, 340));
        primaryStage.show();




    }
//    public static void main(String[] args) {
//        launch();
//    }
}
