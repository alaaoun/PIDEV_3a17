package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.reservation;
import services.ReservationService;
import utlis.MyDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Ajoutreservation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateR;
    @FXML
    private Label Mtotal;
    @FXML
    private Label total;
    @FXML
    private Label impot;
    @FXML
    private TextField heureR;

    @FXML
    private Label model;

    @FXML
    private Spinner<Integer> periode;

    @FXML
    private Label prix;

    @FXML
    private Spinner<Integer> quantite;

    @FXML
    private TableColumn<reservation,String> coldatersv;;
    @FXML
    private TableColumn<reservation,String> colheurersv;
    @FXML
    private TableColumn<reservation,Integer> colid;
    @FXML
    private TableColumn<reservation,Integer> colperiode;
    @FXML
    private TableColumn<reservation,Integer> colprix;
    @FXML
    private TableColumn<reservation,Integer> colqte;
    @FXML
    private TableView<reservation> table;
    @FXML
    private TableColumn<reservation, String> colmodel;

    @FXML
    private Spinner<Integer> qtes;
    @FXML
    private Spinner<Integer> prds;
    @FXML
    private DatePicker dats;

    @FXML
    private TextField heur;
    @FXML
    private Label md;
    @FXML
    private Label prx;




    ObservableList<reservation> listM ;
    int index=-1;




    @FXML
    void AddResrvation(ActionEvent event) throws SQLException {
        // Récupération du texte des labels
        String modelText = model.getText();

        LocalDate selectedDate = dateR.getValue();
        String dateRText = (selectedDate != null) ? selectedDate.toString() : "";

        int calculatedPrice = quantite.getValue() * periode.getValue() * Integer.parseInt(prix.getText());

        reservation reserv = new reservation(
                quantite.getValue(), periode.getValue(),calculatedPrice, modelText, heureR.getText(), dateRText
        );

        // Création d'une instance de ReservationService
        ReservationService ReservationService = new ReservationService();
        try {
            // Appel de la méthode ajouter de ReservationService
            ReservationService.ajouter(reserv);
            // Affichage d'une fenêtre d'alerte pour indiquer que la réservation a été ajoutée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information message");
            alert.setHeaderText(null);
            alert.setContentText("La réservation a été faite avec succès");
            alert.showAndWait();
            initialize();
        } catch (SQLException e) {
            // En cas d'erreur, affichage d'une fenêtre d'alerte avec le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void addsresvr(ActionEvent event) throws SQLException{
        // Récupération du texte des labels
        String models = md.getText();

        LocalDate selectedDate = dats.getValue();
        String datsText = (selectedDate != null) ? selectedDate.toString() : "";

        int calculPrice = qtes.getValue() * prds.getValue() * Integer.parseInt(prx.getText());

        reservation reserv = new reservation(
                qtes.getValue(), prds.getValue(),calculPrice, models, heur.getText(), datsText
        );

        // Création d'une instance de ReservationService
        ReservationService ReservationService = new ReservationService();
        try {
            // Appel de la méthode ajouter de ReservationService
            ReservationService.ajouter(reserv);
            // Affichage d'une fenêtre d'alerte pour indiquer que la réservation a été ajoutée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information message");
            alert.setHeaderText(null);
            alert.setContentText("La réservation a été faite avec succès");
            alert.showAndWait();
            initialize();
        } catch (SQLException e) {
            // En cas d'erreur, affichage d'une fenêtre d'alerte avec le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        }


    @FXML
    void ModifierReservation(ActionEvent event) throws SQLException {
        Connection connection;
        connection = MyDatabase.getInstance().getConnection();
        reservation selectedReservation = table.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            System.out.println("Réservation sélectionnée pour la modification : " + selectedReservation);


            try {
                // Appeler la méthode modifier de ReservationService
                int newPrice = quantite.getValue() * periode.getValue() * Integer.parseInt(prix.getText());
                String sql = "UPDATE reservation SET model = ?, qte = ?, datersv = ?, heurersv = ?, periode = ?, prix = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, model.getText());
                preparedStatement.setInt(2, quantite.getValue());
                preparedStatement.setString(3, dateR.getValue().toString());
                preparedStatement.setString(4, heureR.getText());
                preparedStatement.setInt(5, periode.getValue());
                preparedStatement.setInt(6, newPrice);
                preparedStatement.setInt(7, selectedReservation.getId());

                // Exécuter la requête SQL
                preparedStatement.executeUpdate();

                int newprix = selectedReservation.getQte() * selectedReservation.getPeriode() * Integer.parseInt(prix.getText());
                double tax = 0.1 * newprix;

                // Calculer le montant total (prix total + impôt)
                double totalPrice = newprix - tax;

                Mtotal.setText(String.valueOf(totalPrice));
                total.setText(String.valueOf(newprix));
                impot.setText(String.valueOf(tax));


                System.out.println("La réservation a été modifiée avec succès dans la base de données.");

                // Rafraîchir les données dans le TableView
                initialize(); // Vous pouvez également appeler une méthode spécifique pour mettre à jour les données

                // Afficher un message pour indiquer que la réservation a été modifiée avec succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setHeaderText(null);
                alert.setContentText("La réservation a été modifiée avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                // En cas d'erreur, affichage d'une fenêtre d'alerte avec le message d'erreur
                System.out.println("Erreur lors de la modification de la réservation : " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la modification");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de la modification de la réservation : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("Aucune réservation sélectionnée pour la modification.");
            // Afficher un message d'avertissement si aucune réservation n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune réservation sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réservation à modifier.");
            alert.showAndWait();
        }
    }

    @FXML
    void SupprimerResrvation(ActionEvent event) throws Exception{
        // Récupérer la réservation sélectionnée dans le TableView
        reservation selectedReservation = table.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            // Créer une instance de ReservationService
            ReservationService reservationService = new ReservationService();

            try {
                // Appeler la méthode supprimer de ReservationService
                reservationService.supprimer(selectedReservation.getId());

                // Afficher un message pour indiquer que la réservation a été supprimée avec succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setHeaderText(null);
                alert.setContentText("La réservation a été supprimée avec succès.");
                alert.showAndWait();

                // Rafraîchir les données dans le TableView
                initialize(); // Vous pouvez également appeler une méthode spécifique pour mettre à jour les données
            } catch (SQLException e) {
                // En cas d'erreur, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la suppression de la réservation : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Afficher un message d'avertissement si aucune réservation n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune réservation sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réservation à supprimer.");
            alert.showAndWait();
        }
    }



    @FXML
    void initialize() {
        initializeSpinner(quantite);
        initializeSpinner(periode);
        initializeSpinner(qtes);
        initializeSpinner(prds);

        ReservationService ReservationService = new ReservationService();
        try {
            listM = FXCollections.observableArrayList(ReservationService.recuperer());
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'erreur de récupération des données depuis la base de données
        }

        // Initialiser les colonnes du TableView
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colmodel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colqte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        coldatersv.setCellValueFactory(new PropertyValueFactory<>("datersv"));
        colheurersv.setCellValueFactory(new PropertyValueFactory<>("heurersv"));
        colperiode.setCellValueFactory(new PropertyValueFactory<>("periode"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Assigner la liste au TableView
        table.setItems(listM);


        dateR.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
        dats.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Afficher les détails de la réservation sélectionnée dans les champs correspondants
               // model.setText(newSelection.getModel());
                quantite.getValueFactory().setValue(newSelection.getQte());
                dateR.setValue(LocalDate.parse(newSelection.getDatersv()));
                heureR.setText(newSelection.getHeurersv());
                periode.getValueFactory().setValue(newSelection.getPeriode());
              // prix.setText(String.valueOf(newSelection.getPrix()));
            }
        });


    }




    private void initializeSpinner(Spinner<Integer> spinner) {
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinner.setValueFactory(svf);
    }
}
