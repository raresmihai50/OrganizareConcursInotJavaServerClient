package org.example.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrganizerViewController implements Initializable,IObserver {
    @FXML
    public Button search_btn;
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
    TableColumn<Participant, Integer> trials_col;
    @FXML
    Button reload_btn;
    @FXML
    public ObservableList<Participant> obs_lst = FXCollections.observableArrayList();
    @FXML
    Stage stage;
    IService serv;
    Organizer org;


    public void initOrganizareViewController(Organizer org, IService serv, Stage stage) {
        this.serv = serv;
        this.stage = stage;
        this.org = org;
        organizer_username_lbl.setText("Organizer: "+ org.getUsername());
        setParticipantsTable();
        loadParticipantsTable();
    }
    void setOrg(Organizer org){
        this.org = org;
    }
    public void setServer(IService s) {
        serv = s;
    }

    void setParticipantsTable() {
        organizer_username_lbl.setText("Organizer: "+ org.getUsername());
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
//    void loadParticipantsTable(){
//        List<Participant> participants = null;
//        try {
//            participants = serv.findAllParticipant();
//        } catch (MessageError e) {
//            throw new RuntimeException(e);
//        }
//        obs_lst.setAll(participants);
//    }
    void loadParticipantsTable(){
        try{
            List<Participant> participants = serv.findAllParticipant();
            participantsTable.getItems().clear();
            for(Participant part : participants){
                participantsTable.getItems().add(part);
            }
            if(participantsTable.getItems().size()>0)
                participantsTable.getSelectionModel().select(0);
        } catch (MessageError e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }
    public void handleLogOut(ActionEvent actionEvent){
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    public void handleAddParticipant(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("/addParticipant-view.fxml"));
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
    public void handleSearchBtn(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("/search-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 620, 550);
            stage.setScene(scene);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Search Participants");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);

            SearchViewController searchViewController = fxmlLoader.getController();
            searchViewController.initSearchViewController(serv, dialogStage);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void handleReload(){
        loadParticipantsTable();
    }

    @Override
    public void participantAdded(Participant participant) throws MessageError {
        participantsTable.getItems().add(participant);

    }

    void logout() {
        try {
            serv.logout(org, this);
        } catch (MessageError e) {
            System.out.println("Logout error " + e);
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
