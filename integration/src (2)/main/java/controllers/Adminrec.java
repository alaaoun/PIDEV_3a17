package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.reclamation;
import models.reponse;
import services.reclamationService;
import services.reponseService;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class Adminrec {

    @FXML
    private ResourceBundle resources;
    @FXML
    private TextField searchTextField;
    @FXML
    private HBox proB;
    @FXML
    private HBox userB;
    @FXML
    private HBox resB;
    @FXML
    private HBox recB;


    @FXML
    private URL location;

    @FXML
    private Button deleteButton;

    @FXML
    private Button displayButton;
    @FXML
    private Button statButton;

    @FXML
    private TableColumn<reclamation, String> emailrec;

    @FXML
    private TableColumn<reclamation, String> fullname;

    @FXML
    private TableColumn<reclamation, Integer> idrec;

    @FXML
    private TableColumn<reclamation, String> message;

    @FXML
    private TableColumn<reclamation, String> issue;

    @FXML
    private TableColumn<reclamation, Integer> pnrec;

    @FXML
    private TableColumn<reclamation, Integer> stater;

    @FXML
    private TableColumn<reclamation, String> subject;

    @FXML
    private TableView<reclamation> tableview;
    private Button respondButton;

    private final reclamationService recs = new reclamationService();

    @FXML
    void display(ActionEvent event) {
        emailrec.setCellValueFactory(new PropertyValueFactory<>("emailrec"));
        fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        idrec.setCellValueFactory(new PropertyValueFactory<>("idrec"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        pnrec.setCellValueFactory(new PropertyValueFactory<>("pnrec"));
        issue.setCellValueFactory(new PropertyValueFactory<>("issue")); // Add this line
        stater.setCellValueFactory(new PropertyValueFactory<>("stater"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        try {
            List<reclamation> reclamationList = recs.getReclamationList();
            tableview.setItems(FXCollections.observableArrayList(reclamationList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void filterByFullname(ActionEvent event) {
        try {
            List<reclamation> reclamationList = recs.getReclamationList();

            // Sort the reclamationList by fullname in ascending order
            Collections.sort(reclamationList, new Comparator<reclamation>() {
                @Override
                public int compare(reclamation rec1, reclamation rec2) {
                    return rec1.getFullname().compareTo(rec2.getFullname());
                }
            });

            tableview.setItems(FXCollections.observableArrayList(reclamationList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void filterReclamations(String query) {
        try {
            List<reclamation> reclamationList = recs.getReclamationList(); // Assuming recs is your data source
            List<reclamation> filteredList = new ArrayList<>();
            for (reclamation rec : reclamationList) {
                if (recContainsQuery(rec, query)) {
                    filteredList.add(rec);
                }
            }
            tableview.setItems(FXCollections.observableArrayList(filteredList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    boolean recContainsQuery(reclamation rec, String query) {
        return rec.getFullname().toLowerCase().contains(query.toLowerCase()) ||
                rec.getEmailrec().toLowerCase().contains(query.toLowerCase()) ||
                rec.getSubject().toLowerCase().contains(query.toLowerCase()) ||
                rec.getFullname().toLowerCase().contains(query.toLowerCase()) ||
                Integer.toString(rec.getPnrec()).toLowerCase().contains(query.toLowerCase()) ||
                Integer.toString(rec.getStater()).toLowerCase().contains(query.toLowerCase()) ||
                rec.getMessage().toLowerCase().contains(query.toLowerCase());
    }


    @FXML
    void stat(ActionEvent event) {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/statt.fxml"));
        try {
            Parent root = loader2.load();

            // Get the current stage
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.setTitle("Dashboard");
            Scene sr = new Scene(root, 1000, 671);
            primaryStage.setScene(sr);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void modifierec(ActionEvent event) {
        reclamation selectedReclamation = tableview.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit Stater");
            dialog.setHeaderText("make response for Reclamation ID: " + selectedReclamation.getIdrec());
            dialog.setContentText("Please enter the response here:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newStater -> {
                try {
                    reponseService repS = new reponseService();
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = currentDate.format(formatter);

// Convert the formatted date to java.sql.Date
                    Date sqlDate = Date.valueOf(formattedDate);
                    reponse rep = new reponse(selectedReclamation.getEmailrec(), sqlDate, newStater, selectedReclamation.getIdrec());
                    selectedReclamation.setStater(1);
                    rep.toString();
                    repS.ajouteReponse(rep);
                    recs.modifiereclamtion(1, selectedReclamation.getIdrec());
                    tableview.refresh();

                        // Set the SMTP host and port for sending the email
                        String host = "smtp.gmail.com";
                        String port = "587";
                        String username = "arco.sc0156@gmail.com";
                        String password = "hghseksuroiqviag";

                        // Set the properties for the email session
                        Properties properties = new Properties();
                        properties.put("mail.smtp.host", host);
                        properties.put("mail.smtp.port", "587");
                        properties.put("mail.smtp.auth", "true"); // Enable authentication
                        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS encryption

                        // Create a new email session using the specified properties
                        Session session = Session.getDefaultInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                        try {
                            // Create a new email message
                            Message msg = new MimeMessage(session);

                            // Set the "From" address for the email
                            // msg.setFrom(new InternetAddress("ahmed.benabid2503@gmail.com"));

                            // Add the "To" address for the email (including the recipient's name)
                            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(selectedReclamation.getEmailrec()));

                            // Set the subject and body text for the email
                            msg.setSubject("Reponce de reclamation");
                            msg.setText("Mr(s) ,vous pouvez voir la reponse dans notre app.");

                            // Create an alert to notify the user that the email was sent successfully

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation d'envoie");
                            alert.setHeaderText("Voulez-vous envoyez ce mail?");
                            alert.setContentText("Cette action est requise.");

                            // Show the confirmation dialog and wait for the user's response
                            Optional<ButtonType> resultt = alert.showAndWait();

                            // Send the email

                            if (resultt.get() == ButtonType.OK) {
                                System.out.println("En cours d'envoie...");
                                Transport.send(msg);
                                System.out.println("Envoyé avec succès !");

                            } else {
                                // Close the dialog and do nothing
                                alert.close();
                                System.out.println("Echec d'envoie!");
                            }


                            // Print a message to the console to indicate that the email was sent successfully





                        } catch (AddressException e) {
                            // Create an alert to notify the user that there was an error with the email address
                            e.printStackTrace();
                            System.out.println("Failed to send email: " + e.getMessage());
                        } catch (MessagingException e) {
                            e.printStackTrace();
                            System.out.println("Failed to send email: " + e.getMessage());
                        }

                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
            noSelectionAlert.setTitle("No Selection");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("Please select a reclamation to edit.");
            noSelectionAlert.showAndWait();
        }
    }

    @FXML
    void supprimerec(ActionEvent event) {
        reclamation selectedReclamation = tableview.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Reclamation");
            alert.setContentText("Are you sure you want to delete this reclamation?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                try {
                    recs.supprimereclamtion(selectedReclamation.getIdrec());
                    tableview.getItems().remove(selectedReclamation);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
            noSelectionAlert.setTitle("No Selection");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("Please select a reclamation to delete.");
            noSelectionAlert.showAndWait();
        }
    }
    @FXML
    private void showStats(ActionEvent event) {

    }

    @FXML
    void initialize() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterReclamations(newValue);
        });
        proB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Ajoutertrotinette.fxml"));
                try {
                    Parent root = loader2.load();

                    // Get the current stage
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    primaryStage.setTitle("Dashboard");
                    Scene sr = new Scene(root, 1000, 671);
                    primaryStage.setScene(sr);
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        userB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Affichage.fxml"));
                try {
                    Parent root = loader2.load();

                    // Get the current stage
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    primaryStage.setTitle("Dashboard");
                    Scene sr = new Scene(root, 1000, 671);
                    primaryStage.setScene(sr);
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        recB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/adminrec.fxml"));
                try {
                    Parent root = loader2.load();

                    // Get the current stage
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    primaryStage.setTitle("Dashboard");
                    Scene sr = new Scene(root, 1000, 671);
                    primaryStage.setScene(sr);
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        resB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/adminrec.fxml"));
                try {
                    Parent root = loader2.load();

                    // Get the current stage
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    primaryStage.setTitle("Dashboard");
                    Scene sr = new Scene(root, 1000, 671);
                    primaryStage.setScene(sr);
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    /*@Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
        //respondButton.setOnAction(event -> openResponseWindow());

    }

    private <ResponseWindowController> void openResponseWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("response.fxml"));
            Parent root = loader.load();

            // Get the controller for the response window
            ResponseWindowController responseController = loader.getController();

            // You can pass data to the responseController if needed
            // responseController.setReclamationData(selectedReclamation);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Respond to Reclamation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }*/
}
