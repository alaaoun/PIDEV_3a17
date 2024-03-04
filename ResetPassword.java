package GUI;


import javafx.scene.control.Button;
import models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PasswordResetService;
import services.UserService;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class ResetPassword {

    @FXML
    private TextField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    public Button submitButton;


    private String email;
    private final PasswordResetService passwordResetService = new PasswordResetService();
    UserService userService = new UserService();
    public User user;

    @FXML


    // Method to set the student whose password is being reset
    public void setUser(User user) {
        this.user = user;
        System.out.println("User object set in ResetPassword controller: " + user);
    }

    private void displaySuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        if (user != null) {
            // Assuming you have a method to update only the password in your StudentService
            String newPassword = newPasswordField.getText();
            user.setMdp(newPassword);
            // Update only the password for the student
            try {
                userService.updatePassword(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("User password updated successfully: " + user.getMail());
            redirectToLoginScreen(event);
            // Display success message or navigate to another screen
        } else {
            // Handle the case when the student object is null
            System.out.println("User object is null");
        }
    }
    private void redirectToLoginScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
        }
    }

}
