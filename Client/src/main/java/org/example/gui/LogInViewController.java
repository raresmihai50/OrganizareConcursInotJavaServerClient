package org.example.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.IService;
import org.example.MessageError;
import org.example.Organizer;
import org.example.StartClient;

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

    OrganizerViewController organizerViewController;
    IService serv;
    Parent mainParent;

    public void initLoginController(IService serv) {
        this.serv = serv;
    }


    public void setParent(Parent p){
        mainParent=p;
    }
    public void setOrganizerViewController(OrganizerViewController organizerViewController){
        this.organizerViewController = organizerViewController;
    }

    @FXML
    public void showOrganizerViewTab(Organizer org) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("/organizer-view.fxml"));
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
        System.exit(0);
    }

    @FXML
    public void handleLogIn(ActionEvent actionEvent) {
        String username = username_fld.getText();
        String password = password_fld.getText();
        Organizer org = new Organizer(username, password);
        try {
            serv.login(org, organizerViewController);
            // Util.writeLog("User succesfully logged in "+crtUser.getId());
            Stage stage = new Stage();
            stage.setTitle("Window for " + org.getUsername());
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    organizerViewController.logout();
                    System.exit(0);
                }
            });
            stage.show();
            organizerViewController.setOrg(org);
            organizerViewController.setParticipantsTable();
            organizerViewController.loadParticipantsTable();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (MessageError e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }
    }
}

//        if(org!=null) {
//            if (serv.verifica_parola(pass, org.getPassword())) {
//                showOrganizerViewTab(org);
//            } else {
//                MessageAlert.showErrorMessage(null, "Wrong Password !");
//            }
//        }
//        else {
//            MessageAlert.showErrorMessage(null, "Username doesn't exist !");
//        }

