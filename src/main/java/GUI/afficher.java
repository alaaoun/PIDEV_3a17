package GUI;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.station;
import services.stationservice;
import services.trotinetteservice;

import java.io.IOException;
import java.net.URL;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.trotinette;
import  javafx.scene.control.cell.PropertyValueFactory;


public class afficher {
    public Button buttonmodifer;
    trotinetteservice trotinetteservice=new trotinetteservice();
    stationservice stationservice=new stationservice();
    @FXML
    private TableView<trotinette> tabletrotinette;

    @FXML
    private TableColumn<trotinette, Integer> v;
    @FXML
    private TableColumn<trotinette, Integer> is;

    @FXML
    private TableColumn<trotinette, Integer> it;
    @FXML
    private TableColumn<trotinette, String> d;
    @FXML
    private TableColumn<trotinette, String> c;

    @FXML
    private TableColumn<trotinette, Integer> ch;

    @FXML
    private TextField vitesseid;
    @FXML
    private TextField id;
    @FXML
    private TextField chargeid;
    @FXML
    private TextField couleurid;
    @FXML
    private TextField dispoid;
    @FXML
    private TextField etatid;

    @FXML
    private TextField lieuid;
    @FXML
    private TableColumn<station, String> e;
    @FXML
    private TableColumn<station, String> l;

    @FXML
    private TableColumn<station, String> n;
    @FXML
    private TableColumn<station, Integer> idst;
    @FXML
    private TableView<station> tablestation;
    @FXML
    private TextField nomid;

    @FXML
    private TextField idstation;
    @FXML
    private TextField searchConseil;
    @FXML
    private TextField ids;
    int idd=0;
    int iddd=0;
    ObservableList<trotinette> observableList ;


    @FXML
    void initialize() {

        try {
            ObservableList<station> observableList = FXCollections.observableList(stationservice.recupererr());
            tablestation.setItems(observableList);

            l.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            e.setCellValueFactory(new PropertyValueFactory<>("etat"));
            idst.setCellValueFactory(new PropertyValueFactory<>("id_station"));
            n.setCellValueFactory(new PropertyValueFactory<>("name"));




            ObservableList<trotinette> observableListt = FXCollections.observableList(trotinetteservice.recuperer());
            tabletrotinette.setItems(observableListt);
            it.setCellValueFactory(new PropertyValueFactory<>("id_trotinette"));
            v.setCellValueFactory(new PropertyValueFactory<>("vitesse"));
            ch.setCellValueFactory(new PropertyValueFactory<>("charge"));
            c.setCellValueFactory(new PropertyValueFactory<>("couleur"));
            d.setCellValueFactory(new PropertyValueFactory<>("dispo"));
            is.setCellValueFactory(new  PropertyValueFactory<>("id_station"));



        }catch (SQLException e){
            System.out.println(e.getMessage());
        }}
    @FXML
    void searchConseil(KeyEvent event) {


        // Get the search text from the input field
        String searchQuery = searchConseil.getText(); // Replace searchTextField with the actual name of your TextField

        // Call the searchProducts method in your ConseilService
        List<trotinette> matchingConseils = trotinetteservice.searchProducts(searchQuery);
        // Update the UI with the matching Conseils


        observableList.clear(); // Clear the current data
       observableList.addAll(matchingConseils); // Add the matching Conseils to the observable list
        tabletrotinette.setItems(observableList); // Set the items in the TableView


    }
    @FXML
    void buttonretour(MouseEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Ajoutertrotinette.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        couleurid.getScene().setRoot(root);

    }

    @FXML
    void getData(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Récupérer l'objet Panier sélectionné dans le TableView
            trotinette trotinette = tabletrotinette.getSelectionModel().getSelectedItem();
            station station = tablestation.getSelectionModel().getSelectedItem();
            // Vérifi  er si un élément a été effectivement sélectionné
            if (trotinette != null) {
                // Récupérer les données du Panier et les afficher dans les champs de texte appropriés
                idd = trotinette.getId_trotinette();
                vitesseid.setText(String.valueOf(trotinette.getVitesse()));
                chargeid.setText(String.valueOf(trotinette.getCharge()));
                idstation.setText(String.valueOf(trotinette.getId_station()));
                couleurid.setText((trotinette.getCouleur()));
                dispoid.setText(trotinette.getDispo());

                // ajouterp.setDisable(true);

            }
            if (station != null) {
                // Récupérer les données du Panier et les afficher dans les champs de texte appropriés
                iddd= station.getId_station();
                lieuid.setText(station.getLieu());
                nomid.setText((station.getName()));
                etatid.setText((station.getEtat()));


                // ajouterp.setDisable(true);

            }
        }


    }


    private void refreshTableView() {
        try {
            ObservableList<trotinette> observableList = FXCollections.observableList(trotinetteservice.recuperer());
            tabletrotinette.setItems(observableList);
            ObservableList<station> observableListt = FXCollections.observableList(stationservice.recupererr());
            tablestation.setItems(observableListt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Gérer l'erreur selon vos besoins
        }
    }

    // Méthode pour réinitialiser les champs de texte
    private void resetFields() {
        vitesseid.clear();
        chargeid.clear();
        couleurid.clear();
        dispoid.clear();
        idstation.clear();
        lieuid.clear();
        nomid.clear();
        etatid.clear();

    }



    @FXML
    void buttonmodifer(MouseEvent event) {
        // Récupérer l'objet Conseil sélectionné dans le TableView
        trotinette trotinette = tabletrotinette.getSelectionModel().getSelectedItem();



        // Vérifier si un élément a été effectivement sélectionné
        if (trotinette != null) {
            // Mettre à jour les propriétés du Conseil avec les valeurs des champs de texte
            trotinette.setCouleur(couleurid.getText());
            trotinette.setDispo(dispoid.getText());
            trotinette.setId_station(Integer.parseInt(idstation.getText()));
            trotinette.setVitesse(Integer.parseInt(vitesseid.getText()));
            trotinette.setCharge(Integer.parseInt(chargeid.getText()));

            // Appeler la méthode de mise à jour dans votre service ou gestionnaire de données
            try {
                trotinetteservice.modifer(trotinette,idd); // Assuming you have an update method in your service
                // Rafraîchir la TableView après la modification
                refreshTableView();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour du trotinette: " + e.getMessage());
                // Vous pouvez également afficher une boîte de dialogue ou une alerte pour informer l'utilisateur de l'erreur.
            }
        }
        resetFields();
        refreshTableView();

    }
    @FXML
    void buttonmodifer2(MouseEvent event) {
        // Récupérer l'objet Conseil sélectionné dans le TableView
        station station = tablestation.getSelectionModel().getSelectedItem();



        // Vérifier si un élément a été effectivement sélectionné
        if (station != null) {
            // Mettre à jour les propriétés du Conseil avec les valeurs des champs de texte
            station.setLieu(lieuid.getText());
            station.setName(nomid.getText());
            station.setEtat(etatid.getText());
;
            // Appeler la méthode de mise à jour dans votre service ou gestionnaire de données
            try {
                stationservice.modifer(station,iddd); // Assuming you have an update method in your service
                // Rafraîchir la TableView après la modification
                refreshTableView();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour du trotinette: " + e.getMessage());
                // Vous pouvez également afficher une boîte de dialogue ou une alerte pour informer l'utilisateur de l'erreur.
            }
        }
        resetFields();
        refreshTableView();

    }







    @FXML
    void del(MouseEvent event) {
        trotinette trotinette = tabletrotinette.getSelectionModel().getSelectedItem();
        if (trotinette != null) {
            try {
                trotinetteservice.supprimer(idd);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            resetFields();
            refreshTableView();
    }


    }

    @FXML
    void buttonsup2(MouseEvent event) {
        station station = tablestation.getSelectionModel().getSelectedItem();
        if (station!= null) {
            try {
                stationservice.supprimer(iddd);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            resetFields();
            refreshTableView();
        }

    }

}
