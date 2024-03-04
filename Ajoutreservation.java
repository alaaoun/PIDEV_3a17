package GUI;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.recu;
import models.reservation;
import services.RecuService;
import services.ReservationService;
import utils.MYdatabase;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class Ajoutreservation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private HBox gestionPro;
    @FXML
    private HBox gestionRec;
    @FXML
    private Label idt;
    @FXML
    private Label idt1;


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
    @FXML
    private Label nom;


    @FXML
    private Button buttonModifier;

    @FXML
    private Button buttonSupprimer;

    ObservableList<reservation> listM ;
    int index=-1;

    private boolean paymentInProgress = false;
    private double totalCumulatifPrix = 0.0;

    @FXML
    void AddResrvation(ActionEvent event) throws SQLException {
        // Récupération du texte des labels
        String modelText = model.getText();

        LocalDate selectedDate = dateR.getValue();
        String dateRText = (selectedDate != null) ? selectedDate.toString() : "";

        String heureRText = heureR.getText().trim(); // Trim pour supprimer les espaces inutiles

        // Vérifier si les champs dateR et heureR ne sont pas vides
        if (quantite.getValue() == 0 || periode.getValue() == 0 || dateRText.isEmpty() || heureRText.isEmpty()) {
            // Afficher un message d'erreur indiquant que les champs sont obligatoires
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("La quantité et la période doivent être supérieures à zéro, et tous les champs doivent être remplis.");
            alert.showAndWait();
            return; // Sortir de la méthode sans effectuer l'ajout de la réservation
        }

        // Calcul du prix de la réservation
        int calculatedPrice = quantite.getValue() * periode.getValue() * Integer.parseInt(prix.getText());

        // Calcul de l'impôt et du prix total
        double tax = 0.1 * calculatedPrice;
        double totalPrice = calculatedPrice + tax;

        // Ajout du prix total et de l'impôt au total cumulatif
        totalCumulatifPrix += totalPrice;

        // Création d'une nouvelle réservation avec les nouveaux champs
        reservation reserv = new reservation(
                quantite.getValue(), periode.getValue(), calculatedPrice, modelText, heureRText, dateRText,
                tax, totalPrice, totalCumulatifPrix
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

            // Mettre à jour les labels dans l'interface utilisateur
            Mtotal.setText(String.valueOf(totalCumulatifPrix));
            total.setText(String.valueOf(calculatedPrice));
            impot.setText(String.valueOf(tax));

            // Rafraîchir les données dans le TableView
            initialize();
        } catch (SQLException e) {
            // En cas d'erreur, affichage d'une fenêtre d'alerte avec le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void addrsv(ActionEvent event) throws SQLException {
        // Récupération du texte des labels
        String models = md.getText();

        LocalDate selectedDate = dats.getValue();
        String datsText = (selectedDate != null) ? selectedDate.toString() : "";

        int calculPrice = qtes.getValue() * prds.getValue() * Integer.parseInt(prx.getText());
        int calculatedPrice = quantite.getValue() * periode.getValue() * Integer.parseInt(prix.getText());

        // Calcul de l'impôt et du prix total
        double tax = 0.1 * calculatedPrice;
        double totalPrice = calculatedPrice + tax;

        // Ajout du prix total et de l'impôt au total cumulatif
        totalCumulatifPrix += totalPrice;
        reservation reserv = new reservation(
                qtes.getValue(), prds.getValue(),calculPrice, models, heur.getText(), datsText,
                tax, totalPrice, totalCumulatifPrix);

        // Création d'une instance de ReservationService
        ReservationService ReservationService = new ReservationService();
        try {
            //Appel de la méthode ajouter de ReservationService
             ReservationService.ajouter(reserv);
            // Affichage d'une fenêtre d'alerte pour indiquer que la réservation a été ajoutée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information message");
            alert.setHeaderText(null);
            alert.setContentText("La réservation a été faite avec succès");
            alert.showAndWait();
            Mtotal.setText(String.valueOf(totalCumulatifPrix));
            total.setText(String.valueOf(calculatedPrice));
            impot.setText(String.valueOf(tax));
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
        connection = MYdatabase.getInstance().getConnection();
        reservation selectedReservation = table.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            System.out.println("Réservation sélectionnée pour la modification : " + selectedReservation);

            // Créer une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de modification");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette réservation ?");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Si l'utilisateur clique sur le bouton OK, procéder à la modification
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Calculer le nouveau prix
                    int newPrice = quantite.getValue() * periode.getValue() * Integer.parseInt(prix.getText());

                    // Mettre à jour la réservation dans la base de données avec les nouvelles valeurs
                    String sql = "UPDATE reservation SET model = ?, qte = ?, datersv = ?, heurersv = ?, periode = ?, prix = ? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, model.getText());
                    preparedStatement.setInt(2, quantite.getValue());
                    preparedStatement.setString(3, dateR.getValue().toString());
                    preparedStatement.setString(4, heureR.getText());
                    preparedStatement.setInt(5, periode.getValue());
                    preparedStatement.setInt(6, newPrice);
                    preparedStatement.setInt(7, selectedReservation.getId());
                    preparedStatement.executeUpdate();

                    // Calculer le montant total en utilisant le nouveau prix
                    double tax = 0.1 * newPrice;
                    double totalPrice = newPrice + tax;

                    // Mettre à jour les valeurs d'impôt, de total et de montant total dans la base de données
                    sql = "UPDATE reservation SET impot = ?, total = ?, mtotal = ? WHERE id = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setDouble(1, tax);
                    preparedStatement.setDouble(2, newPrice);
                    preparedStatement.setDouble(3, totalPrice);
                    preparedStatement.setInt(4, selectedReservation.getId());
                    preparedStatement.executeUpdate();

                    // Mettre à jour les labels correspondants dans l'interface utilisateur
                    impot.setText(String.valueOf(tax));
                    total.setText(String.valueOf(newPrice));
                    Mtotal.setText(String.valueOf(totalPrice));

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
    void SupprimerResrvation(ActionEvent event) throws Exception {
        // Récupérer la réservation sélectionnée dans le TableView
        reservation selectedReservation = table.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            // Créer une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette réservation ?");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Si l'utilisateur clique sur le bouton OK, procéder à la suppression
            if (result.isPresent() && result.get() == ButtonType.OK) {
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
    void payer(ActionEvent event) throws Exception {
        String nomText = controller.nomUser;

        // Calcul du prix de la réservation
        double impotValue = Double.parseDouble(impot.getText());
        double totalValue = Double.parseDouble(total.getText());
        double mtotalValue = Double.parseDouble(Mtotal.getText());
        Date dateP = new Date();

        // Ajout du prix total et de l'impôt au total cumulatif


        // Création d'une nouvelle réservation avec les nouveaux champs
        recu recuP = new recu(
                nomText, impotValue, totalValue, mtotalValue, dateP
        );

        // Création d'une instance de ReservationService
        RecuService recuService = new RecuService();

        // Créer une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de paiement");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir procéder au paiement ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Si l'utilisateur clique sur le bouton OK, procéder au paiement
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Appel de la méthode payer de RecuService
                recuService.payer(recuP);

                // Affichage d'une fenêtre d'alerte pour indiquer que le paiement a été effectué avec succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText(null);
                alert.setContentText("Le paiement a été effectué avec succès");
                alert.showAndWait();

                paymentInProgress = true;
                buttonModifier.setDisable(true);
                buttonSupprimer.setDisable(true);


                // Rafraîchir les données dans le TableView
                initialize();
            } catch (SQLException e) {
                // En cas d'erreur, affichage d'une fenêtre d'alerte avec le message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void AddRecu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Recu.fxml"));
            Parent root = loader.load();

            Recu recuController = loader.getController();

            RecuService recuService = new RecuService();
            List<recu> recuList = recuService.recupererRecu();

            if (!recuList.isEmpty()) {
                // Assume you want to generate PDF for the first recu in the list
                recu recuData = recuList.get(0);

                // Set the values of fields in the Recu controller
                recuController.setRefrecu(recuData.getId());
                recuController.setNomclient(recuData.getName_client());
                recuController.setTaxrecu(recuData.getImpot());
                recuController.setTotalrecu(recuData.getTotal());
                recuController.setMtotalrecu(recuData.getMtotal());
                recuController.setDaterecu(recuData.getDate());

                // Generate and download the PDF
                generateAndDownloadPDF(recuData, event);
            } else {
                // If no data is retrieved, display an error message or perform an appropriate action
                System.out.println("No recu data retrieved from the database.");
                // Display an error message or perform an appropriate action
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        idt.setText(String.valueOf(controller.idtro));
        idt1.setText(String.valueOf(controller.idtro));
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


        gestionPro.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    // Load the Ajoutreservation.fxml file
                    Parent root = FXMLLoader.load(getClass().getResource("/front.fxml"));

                    // Get the current scene
                    Scene scene = gestionPro.getScene();

                    // Update the root of the scene
                    scene.setRoot(root);

                    // Optionally, you can also update the stage title
                    Stage stage = (Stage) scene.getWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        gestionRec.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    // Load the Ajoutreservation.fxml file
                    Parent root = FXMLLoader.load(getClass().getResource("/userec.fxml"));

                    // Get the current scene
                    Scene scene = gestionRec.getScene();

                    // Update the root of the scene
                    scene.setRoot(root);

                    // Optionally, you can also update the stage title
                    Stage stage = (Stage) scene.getWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }




    private void initializeSpinner(Spinner<Integer> spinner) {
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinner.setValueFactory(svf);
    }

    private void generateAndDownloadPDF(recu recuData, ActionEvent event) {
        try {
            Document document = new Document();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

            if (selectedFile != null) {
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Add recu data to PDF
                addRecuDataToPDF(document, recuData);

                document.close();
                System.out.println("The PDF file for the receipt has been generated and downloaded successfully.");
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addRecuDataToPDF(Document document, recu recuData) throws DocumentException {
        // Title
        Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, BaseColor.BLUE);
        Paragraph title = new Paragraph("ESPRSICO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Date in header left
        Font dateFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
        Paragraph date = new Paragraph("Date: " + recuData.getDate(), dateFont);
        date.setAlignment(Element.ALIGN_LEFT);
        document.add(date);

        Font contentFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
        Paragraph id = new Paragraph("Référence recu: " + recuData.getId(), contentFont);
        id.setAlignment(Element.ALIGN_CENTER);
        document.add(id);

        Paragraph nom = new Paragraph("Nom client: " + recuData.getName_client(), contentFont);
        nom.setAlignment(Element.ALIGN_CENTER);
        document.add(nom);

        Paragraph taxe = new Paragraph("Taxe recu: " + recuData.getImpot() + " TND", contentFont);
        taxe.setAlignment(Element.ALIGN_CENTER);
        document.add(taxe);

        Paragraph tot = new Paragraph("Total recu: " + recuData.getTotal() + " TND", contentFont);
        tot.setAlignment(Element.ALIGN_CENTER);
        document.add(tot);

        Paragraph mt = new Paragraph("Montant total recu: " + recuData.getMtotal() + " TND", contentFont);
        mt.setAlignment(Element.ALIGN_CENTER);
        document.add(mt);



    }




}
