package GUI;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.station;
import services.stationservice;
import services.trotinetteservice;

import java.io.IOException;
import java.net.URL;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.trotinette;
import  javafx.scene.control.cell.PropertyValueFactory;



public class Ajoutertrotinette {
    trotinetteservice trotinetteservice=new trotinetteservice();
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
    private TextField nomid;

    @FXML
    private TextField idstation;
    @FXML
    private TextField ids;


    @FXML
    void ajoutertrotinette(MouseEvent event) throws SQLException {
        // Check if any of the fields are empty
        if (vitesseid.getText().isEmpty() || chargeid.getText().isEmpty() || couleurid.getText().isEmpty() || dispoid.getText().isEmpty() || idstation.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
            return; // Exit the method if any field is empty
        }

        // If all fields are filled, proceed with adding the scooter
        try {
            trotinette trotinette = new trotinette(Integer.parseInt(vitesseid.getText()), Integer.parseInt(chargeid.getText()), couleurid.getText(), dispoid.getText(), Integer.parseInt(idstation.getText()));
            trotinetteservice.ajouter(trotinette);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Trotinette a été ajouté avec succès");
            alert.show();
        } catch (NumberFormatException e) {
            // Handle the case where vitesseid or chargeid or idstation is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir des valeurs numériques pour les champs Vitesse, Charge et ID Station");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    @FXML
    public void supprimertrotinette(ActionEvent actionEvent) throws SQLException {
        // Récupérer l'ID de la trotinette à supprimer (supposons qu'il soit stocké dans un champ de texte nommé idTrotinette)
        int id_trotinette = Integer.parseInt(id.getText());

        // Créer une instance de TrotinetteService
        trotinetteservice trotinetteService =new trotinetteservice();

        try {
            // Appeler la méthode de suppression de trotinette en utilisant l'ID récupéré
            trotinetteService.supprimer(id_trotinette);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Trotinette supprimée avec succès");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur, afficher une alerte d'erreur avec le message d'erreur obtenu
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    @FXML
    void afficherrtrotinette(MouseEvent event) throws IOException {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/afficher.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        couleurid.getScene().setRoot(root);

    }
    @FXML
    void front(ActionEvent event) throws IOException {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/front.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        couleurid.getScene().setRoot(root);

    }
    @FXML
    void ajouterstation(MouseEvent event) throws SQLException {
        // Check if any of the fields are empty
        if (lieuid.getText().isEmpty() || nomid.getText().isEmpty() || etatid.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
            return; // Exit the method if any field is empty
        }

        // If all fields are filled, proceed with adding the station
        try {
            station station = new station(lieuid.getText(), nomid.getText(), etatid.getText());
            stationservice stationservice = new stationservice();
            stationservice.ajouter(station);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Station a été ajoutée avec succès");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML

    public void supprimerstation(ActionEvent actionEvent) throws SQLException {
        // Récupérer l'ID de la trotinette à supprimer (supposons qu'il soit stocké dans un champ de texte nommé idTrotinette)
        int id_station = Integer.parseInt(ids.getText());

        // Créer une instance de TrotinetteService
        stationservice stationservice =new stationservice();

        try {
            // Appeler la méthode de suppression de trotinette en utilisant l'ID récupéré
            stationservice.supprimer(id_station);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("station supprimée avec succès");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur, afficher une alerte d'erreur avec le message d'erreur obtenu
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


  /*@FXML
    void getData(MouseEvent event) {



    }*/



    }

