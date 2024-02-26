package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class CreateUser {
    public TextField FXNomU;
    public TextField FXAdresseU;
    public TextField FXNumtelU;
    public TextField FXMailU;
    public TextField FXMdpU;
    @FXML
    void creeButtonOnAction(ActionEvent event) {
        try {
            Stage primaryStage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/nouveauuser.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Add a person");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AddUser(ActionEvent actionEvent) {
        String nom = FXNomU.getText();
        String adresse = FXAdresseU.getText();
        String numtel = FXNumtelU.getText();
        String mail = FXMailU.getText();
        String mdp = FXMdpU.getText();

        if (!nom.matches("^[a-zA-Z]+$")) {
            showAlert("Erreur de formulaire !", "Le nom doit contenir uniquement des lettres alphabétiques");
            return;
        }

        if (adresse.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer une adresse ");
            return;
        }
        // Valider le numéro de téléphone (exactement 8 caractères)
        if (numtel.length() != 8) {
            showAlert("Erreur de formulaire !", "Le numéro de téléphone doit contenir 8 caractères");
            return;
        }

        // Valider l'adresse e-mail (non vide)
        if (!mail.contains("@")) {
            showAlert("Form Error!", "Invalid email address (must contain '@')");
            return;
        }


        // Valider le mot de passe (non vide)
        if (mdp.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer un mot de passe");
            return;
        }

        // Logique d'ajout de l'administrateur (votre code existant)
        UserService ps = new UserService();
        try {
            ps.ajouteruser(new User(nom, adresse, numtel, mail, mdp));
            // Rafraîchir l'affichage après l'ajout de l'administrateur

        } catch (SQLException s) {
            System.out.println(s);
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialogue d'information");
        alert.setContentText("Utilisateur inséré avec succès !");
        alert.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
