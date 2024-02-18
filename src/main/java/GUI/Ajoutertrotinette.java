package GUI;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.station;
import services.stationservice;
import services.trotinetteservice;
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

    int idd=0;
    @FXML
    void ajoutertrotinette(ActionEvent event) throws SQLException {
        trotinette trotinette=new trotinette(Integer.parseInt(vitesseid.getText()),Integer.parseInt(chargeid.getText()),couleurid.getText(),dispoid.getText(),Integer.parseInt(idstation.getText()));
        trotinetteservice trotinetteservice=new trotinetteservice();
        try {
            trotinetteservice.ajouter(trotinette);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("trotinette a ete  ajouté avec succes");
            alert.show();
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void initialize() {
        try {
            ObservableList<trotinette> observableList = FXCollections.observableList(trotinetteservice.recuperer());
    tabletrotinette.setItems(observableList);
    it.setCellValueFactory(new PropertyValueFactory<>("id_trotinette"));
     v.setCellValueFactory(new PropertyValueFactory<>("vitesse"));
     ch.setCellValueFactory(new PropertyValueFactory<>("charge"));
     c.setCellValueFactory(new PropertyValueFactory<>("couleur"));
     d.setCellValueFactory(new PropertyValueFactory<>("dispo"));
     is.setCellValueFactory(new  PropertyValueFactory<>("id_station"));



        }catch (SQLException e){
            System.out.println(e.getMessage());
    }}
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
    void ajouterstation(ActionEvent event) throws SQLException {
        station station=new station(lieuid.getText(),nomid.getText(),etatid.getText());
        stationservice stationservice=new stationservice();
        try {
            stationservice.ajouter(station);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("station  a ete  ajouté avec succes");
            alert.show();
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    @FXML

    public void supprimerstation(ActionEvent actionEvent) throws SQLException {
        // Récupérer l'ID de la trotinette à supprimer (supposons qu'il soit stocké dans un champ de texte nommé idTrotinette)
        int id_station = Integer.parseInt(ids.getText());

        // Créer une instance de TrotinetteService
        stationservice stationService =new stationservice();

        try {
            // Appeler la méthode de suppression de trotinette en utilisant l'ID récupéré
            stationService.supprimer(id_station);

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
  @FXML
  void getData(MouseEvent event) {
      if (event.getButton() == MouseButton.PRIMARY) {
          // Récupérer l'objet Panier sélectionné dans le TableView
          trotinette trotinette = tabletrotinette.getSelectionModel().getSelectedItem();
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
      }


  }


    private void refreshTableView() {
        try {
            ObservableList<trotinette> observableList = FXCollections.observableList(trotinetteservice.recuperer());
            tabletrotinette.setItems(observableList);
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

    }



    @FXML
    void buttonmodifer(ActionEvent event) {
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



    }

