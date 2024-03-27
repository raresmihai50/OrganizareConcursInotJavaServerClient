package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.service.Service;

import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML
    Button login_btn;
    @FXML
    TextField id_fld;
    @FXML
    TextField username_fld;
    @FXML
    PasswordField password_fld;
    Service serv;
    public void initLoginController(Service serv){
        this.serv = serv;
    }
    @FXML
    public void handleLogIn() throws NoSuchAlgorithmException {
        String pass = password_fld.getText();
        int id_org = Integer.parseInt(id_fld.getText());


    }
}
