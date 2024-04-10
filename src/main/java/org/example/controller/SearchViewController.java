package org.example.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.Participant;
import org.example.domain.Trial;
import org.example.service.Service;

import java.util.ArrayList;
import java.util.List;

public class SearchViewController {
    @FXML
    public CheckBox distance_cb;
    @FXML
    public CheckBox style_cb;
    @FXML
    public CheckBox cb_50m;
    @FXML
    public CheckBox cb_800m;
    @FXML
    public CheckBox cb_200m;
    @FXML
    public CheckBox cb_1500m;
    @FXML
    public CheckBox cb_freestyle;
    @FXML
    public CheckBox cb_butterfly;
    @FXML
    public CheckBox cb_backstroke;
    @FXML
    public CheckBox cb_individual;
    @FXML
    public TableView<Participant> participantsTable;
    @FXML
    public Button search_btn;
    @FXML
    public Button exit_btn;
    @FXML
    TableColumn<Participant, Integer> id_col;
    @FXML
    TableColumn<Participant, String> name_col;
    @FXML
    TableColumn<Participant, Integer> age_col;
    @FXML
    TableColumn<Participant, Integer> trials_col;
    @FXML
    public ObservableList<Participant> obs_lst = FXCollections.observableArrayList();
    @FXML
    Stage stage;
    Service serv;

    public void initSearchViewController(Service serv, Stage dialogStage) {
        this.stage = dialogStage;
        this.serv = serv;
        setCb();
        initializeDistanceAndStyleCb();
        setParticipantsTable();
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

    public void handleExit() {
        stage.close();
    }

    private void setParticipantsTable() {
        id_col.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Participant, String>("name"));
        age_col.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("age"));
        //trials_col.setCellValueFactory(new PropertyValueFactory<Participant, List<Trial>>("trials"));
        trials_col.setCellValueFactory(cellData -> {
            List<Trial> trials = cellData.getValue().getTrials();
            Integer trialsSize = trials != null ? trials.size() : 0;
            return new SimpleIntegerProperty(trialsSize).asObject();
        });
        participantsTable.setItems(obs_lst);
    }
    public void handleSearch() {
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
            MessageAlert.showErrorMessage(null, "Select at least one Trial to Search !");
        }
        else {
            List<Participant> participants = serv.findParticipantsByTrials(trials);
            obs_lst.setAll(participants);
        }
    }
}
