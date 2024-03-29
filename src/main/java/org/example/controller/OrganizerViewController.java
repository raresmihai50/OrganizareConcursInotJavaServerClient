package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.MainUI;
import org.example.domain.Organizer;
import org.example.domain.Participant;
import org.example.domain.Trial;
import org.example.service.Service;

import java.io.IOException;
import java.util.List;

public class OrganizerViewController {
    @FXML
    Label organizer_username_lbl;
    @FXML
    TableView<Participant> participantsTable;
    @FXML
    TableColumn<Participant, Integer> id_col;
    @FXML
    TableColumn<Participant, String> name_col;
    @FXML
    TableColumn<Participant, Integer> age_col;
    @FXML
    TableColumn<Participant, List<Trial>> trials_col;
    @FXML
    public ObservableList<Participant> obs_lst = FXCollections.observableArrayList();
    @FXML
    Stage stage;
    Service serv;
    Organizer org;


    public void initOrganizareViewController(Organizer org, Service serv, Stage stage) {
        this.serv = serv;
        this.stage = stage;
        this.org = org;
        organizer_username_lbl.setText("Organizer: "+ org.getUsername());
        setParticipantsTable();
        loadParticipantsTable();
    }

    private void setParticipantsTable() {
        id_col.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Participant, String>("name"));
        age_col.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("age"));
        trials_col.setCellValueFactory(new PropertyValueFactory<Participant, List<Trial>>("trials"));
        participantsTable.setItems(obs_lst);
    }
    private void loadParticipantsTable(){
        List<Participant> participants = serv.findAllParticipant();
        obs_lst.setAll(participants);
    }
    public void handleLogOut(){
        stage.close();
    }
    public void handleAddParticipant(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("/addParticipant-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            stage.setScene(scene);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Participant");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);

            AddParticipantViewController addParticipantViewController = fxmlLoader.getController();
            addParticipantViewController.initAddParticipantController(serv, dialogStage);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
