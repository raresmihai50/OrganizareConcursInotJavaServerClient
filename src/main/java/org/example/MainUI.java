package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.LogInViewController;
import org.example.repository.OrganizerDBRepository;
import org.example.repository.ParticipantDBRepository;
import org.example.repository.TrialDBRepository;
import org.example.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainUI extends Application {
    public void start(Stage stage) throws IOException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        OrganizerDBRepository orgRepo = new OrganizerDBRepository(props);
        TrialDBRepository trRepo = new TrialDBRepository(props);
        ParticipantDBRepository partRepo = new ParticipantDBRepository(props, trRepo);
        Service serv = new Service(orgRepo,trRepo,partRepo);


        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 340);
        LogInViewController ctr = fxmlLoader.getController();
        ctr.initLoginController(serv);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    //Main
    public static void main(String[] args) {
        launch();
    }
}
