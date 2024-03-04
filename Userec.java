package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.IssueType;
import models.reclamation;
import services.reclamationService;

public class Userec {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private HBox gestionRes;
    @FXML
    private HBox gestionPro;

    @FXML
    private TextField emailrec;

    @FXML
    private TextField fullname;

    @FXML
    private TextField message;

    @FXML
    private TextField pnrec;

    @FXML
    private TextField subject;

    @FXML
    private ChoiceBox<IssueType> issue; // Use ChoiceBox for IssueType

    @FXML
    void ajouterec(ActionEvent event) throws SQLException {
        String fullName = fullname.getText();
        String emailrecs = emailrec.getText();
        String pnrecs = pnrec.getText();
        IssueType issues = issue.getValue();
        String subjects = subject.getText();
        String messages = message.getText();

        if (isEmptyOrNull(fullName) || isEmptyOrNull(emailrecs) || isEmptyOrNull(pnrecs) || issues == null || isEmptyOrNull(subjects) || isEmptyOrNull(messages)) {
            showAlert("Please fill in all fields.");
            return;
        }

        if (!isValidPhoneNumber(pnrecs)) {
            showAlert("Invalid phone number format; please enter 8 digits.");
            return;
        }

        if (!isValidEmail(emailrecs)) {
            showAlert("Invalid email format; please enter a valid email address.");
            return;
        }

        if (isExistingReclamation(fullName, emailrecs, Integer.parseInt(pnrecs), issues, subjects, messages)) {
            showAlert("A reclamation with the same details already exists.");
            return;
        }

        reclamation rec = new reclamation(fullName, emailrecs, Integer.parseInt(pnrecs), issues, subjects, messages);

        reclamationService recs = new reclamationService();
        try {
            recs.ajoutereclamtion(rec);
            showAlert("Reclamation added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{8}");
    }

    private boolean isValidEmail(String email) {
        return email.matches(".+@.+\\..+");
    }

    private boolean isExistingReclamation(String fullName, String emailrec, int pnrec, IssueType issue, String subject, String message) throws SQLException {
        reclamationService recs = new reclamationService();
        List<reclamation> allReclamations = recs.readAll();
        for (reclamation existingRec : allReclamations) {
            if (existingRec.getFullname().equals(fullName)
                    && existingRec.getEmailrec().equals(emailrec)
                    && existingRec.getIssue().name().equals(issue.name())  // Use name() method for comparing enum values
                    && existingRec.getPnrec() == pnrec
                    && existingRec.getSubject().equals(subject)
                    && existingRec.getMessage().equals(message)) {
                return true;
            }
        }
        return false;
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    void initialize() {
        IssueType[] issueTypes = IssueType.values();
        issue.getItems().addAll(issueTypes);
        gestionRes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    // Load the Ajoutreservation.fxml file
                    Parent root = FXMLLoader.load(getClass().getResource("/Ajoutreservation.fxml"));

                    // Get the current scene
                    Scene scene = gestionRes.getScene();

                    // Update the root of the scene
                    scene.setRoot(root);

                    // Optionally, you can also update the stage title
                    Stage stage = (Stage) scene.getWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    }
}
