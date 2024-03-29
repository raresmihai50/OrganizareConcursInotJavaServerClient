package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.MainUI;
import org.example.domain.Organizer;
import org.example.service.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static java.lang.System.exit;

public class LogInViewController {
    @FXML
    Button login_btn;
    @FXML
    Button exit_btn;
    @FXML
    TextField username_fld;
    @FXML
    PasswordField password_fld;
    Service serv;

    public void initLoginController(Service serv) {
        this.serv = serv;
    }

    @FXML
    public void showOrganizerViewTab(Organizer org) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("/organizer-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            stage.setScene(scene);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Organizer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);

            OrganizerViewController organizerViewController = fxmlLoader.getController();
            organizerViewController.initOrganizareViewController(org, serv, dialogStage);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExit() {
        System.out.println(serv.findParticipantById(2).getTrials());
        exit(0);
    }

    @FXML
    public void handleLogIn() throws NoSuchAlgorithmException {
        String username = username_fld.getText();
        Organizer org = serv.findOrganizerByUsername(username);
        if(org!=null) {
            String pass = password_fld.getText();

            if (serv.verifica_parola(pass, org.getPassword())) {
                showOrganizerViewTab(org);
            } else {
                MessageAlert.showErrorMessage(null, "Wrong Password !");
            }
        }
    }
}

