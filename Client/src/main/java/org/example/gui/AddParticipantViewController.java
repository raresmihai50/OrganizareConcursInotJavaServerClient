package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class AddParticipantViewController { //TODO implements IObserver ???
    @FXML
    TextField name_txtfld;
    @FXML
    TextField age_txtfld;
    @FXML
    CheckBox distance_cb;
    @FXML
    CheckBox style_cb;
    @FXML
    CheckBox cb_50m;
    @FXML
    CheckBox cb_200m;
    @FXML
    CheckBox cb_800m;
    @FXML
    CheckBox cb_1500m;
    @FXML
    CheckBox cb_freestyle;
    @FXML
    CheckBox cb_butterfly;
    @FXML
    CheckBox cb_backstroke;
    @FXML
    CheckBox cb_individual;
    @FXML
    Button cancel_btn;
    @FXML
    Button add_btn;
    @FXML
    Stage stage;
    IService serv;

    public void initAddParticipantController(IService serv, Stage dialogStage) {
        this.serv = serv;
        this.stage = dialogStage;
        setCb();
        initializeDistanceAndStyleCb();
    }

    private void setCb() {
        cb_50m.setVisible(false);
        cb_200m.setVisible(false);
        cb_800m.setVisible(false);
        cb_1500m.setVisible(false);
        cb_backstroke.setVisible(false);
        cb_freestyle.setVisible(false);
        cb_butterfly.setVisible(false);
        cb_individual.setVisible(false);
    }

    private void DistanceOnCb() {
        cb_50m.setVisible(true);
        cb_200m.setVisible(true);
        cb_800m.setVisible(true);
        cb_1500m.setVisible(true);
    }

    private void DistanceOffCb() {
        cb_50m.setVisible(false);
        cb_50m.setSelected(false);
        cb_200m.setVisible(false);
        cb_200m.setSelected(false);
        cb_800m.setVisible(false);
        cb_800m.setSelected(false);
        cb_1500m.setVisible(false);
        cb_1500m.setSelected(false);
    }

    private void StyleOnCb() {
        cb_butterfly.setVisible(true);
        cb_backstroke.setVisible(true);
        cb_individual.setVisible(true);
        cb_freestyle.setVisible(true);
    }

    private void StyleOffCb() {
        cb_butterfly.setVisible(false);
        cb_butterfly.setSelected(false);
        cb_backstroke.setVisible(false);
        cb_backstroke.setSelected(false);
        cb_individual.setVisible(false);
        cb_individual.setSelected(false);
        cb_freestyle.setVisible(false);
        cb_freestyle.setSelected(false);
    }

    public void initializeDistanceAndStyleCb() {
        distance_cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                DistanceOnCb();
                //Checkbox Distance bifat
            } else {
                DistanceOffCb();
                //Checkbox Distance debifat
            }
        });
        style_cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                StyleOnCb();
                //Checkbox Style bifat
            } else {
                StyleOffCb();
                //Checkbox Style debifat
            }
        });
    }

    public void handleAddParticipant() throws MessageError {
        List<Trial> trials = new ArrayList<Trial>();
        if (distance_cb.isSelected()) {
            if (cb_50m.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(distance_cb.getText(), cb_50m.getText());
                trials.add(trial);
            }
            if (cb_200m.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(distance_cb.getText(), cb_200m.getText());
                trials.add(trial);
            }
            if (cb_800m.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(distance_cb.getText(), cb_800m.getText());
                trials.add(trial);
            }
            if (cb_1500m.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(distance_cb.getText(), cb_1500m.getText());
                trials.add(trial);
            }
        }
        if (style_cb.isSelected()) {
            if (cb_backstroke.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(style_cb.getText(), cb_backstroke.getText());
                trials.add(trial);
            }
            if (cb_butterfly.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(style_cb.getText(), cb_butterfly.getText());
                trials.add(trial);
            }
            if (cb_freestyle.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(style_cb.getText(), cb_freestyle.getText());
                trials.add(trial);
            }
            if (cb_individual.isSelected()) {
                Trial trial = serv.findTrialByTypeDetails(style_cb.getText(), cb_individual.getText());
                trials.add(trial);
            }
        }
        if (trials.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Sign Up for at least one Trial !");
        } else if (Objects.equals(name_txtfld.getText(), "")) {
            MessageAlert.showErrorMessage(null, "Name filed should not be empty !");
        } else if (Objects.equals(age_txtfld.getText(), "")) {
            MessageAlert.showErrorMessage(null, "Age field should not be empty !");
        } else if ((!Objects.equals(age_txtfld.getText(), "")) && (parseInt(age_txtfld.getText())<4)) {
            MessageAlert.showErrorMessage(null, "Age should be higher than 3 !");
        } else {
            Random rand = new Random();
            rand.nextInt();
            System.out.println(rand);
            String name = name_txtfld.getText();
            int age = parseInt(age_txtfld.getText());

            try {
                Participant part = new Participant(rand.nextInt(), name, age, trials);
                serv.addParticipant(part);
            } catch (MessageError e) {
                throw new RuntimeException(e);
            }
            stage.close();
        }
    }

    public void handleCancel() {
        stage.close();
    }
}
