package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.User;
import services.UserService;

import java.sql.SQLException;

public class PerFile {
    public TextField FXNom1;
    public TextField FXAdresse1;
    public TextField FXNumtel1;

    public TextField FXMail1;
    public TextField FXMdp1;
    public TextField FXNom;
    public TextField FXAdresse;
    public TextField FXNumtel;
    public TextField FXMail;
    public TextField FXMdp;
    public TextField FXAdresseU;
    public Button FXB;
    public TextField FXMailU;
    public TextField FXMdpU;
    public PasswordField FXpassword;
    @FXML
    public TableColumn<User, Integer> AFid;
    @FXML
    public TableColumn<User, String> AFmail;
    @FXML
    public TableColumn<User, String> AFnom;

    @FXML
    public TableColumn<User, String> AFnum;

    @FXML
    public TableColumn<User, String> AFadresse;
    @FXML
    public TableColumn<User, String> AFmdp;
    @FXML
    public TableColumn<User, String> AFrole;
    @FXML
    public TableView<User> AFtable;
    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    void modifer(ActionEvent event) {
        // Get the selected user from the TableView
        User selectedUser = AFtable.getSelectionModel().getSelectedItem();

        // Update the selected user's properties with new values from text fields
        selectedUser.setNom(FXNom1.getText());
        selectedUser.setAdresse(FXAdresse1.getText());
        selectedUser.setNum_tel(FXNumtel1.getText());
        selectedUser.setMail(FXMail1.getText());
        selectedUser.setMdp(FXMdp1.getText());

        // Call the modifierUser method to update the database
        try {
            UserService userService = new UserService();
            userService.modifierUser(selectedUser);

            // Refresh the TableView (re-fetch data from the database)
            loadUsersData(); // Implement this method to reload data into the TableView
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions related to database operations
        }
    }


    private void loadUsersData() {
        try {
            UserService userService = new UserService();
            ObservableList<User> users = FXCollections.observableArrayList(userService.afficher()); // Fetch data from the database

            // Assuming you have a TableColumn<User, String> named "AFid"
            AFid.setCellValueFactory(new PropertyValueFactory<>("id"));

            // Set the data into the TableView
            AFtable.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions related to database operations
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        // Get the selected user from the TableView
        User selectedUser = AFtable.getSelectionModel().getSelectedItem();

        // Remove the user from the TableView
        AFtable.getItems().remove(selectedUser);

        // Call the supprimer method to delete the user from the database
        try {
            UserService userService = new UserService();
            userService.supprimer(selectedUser.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions related to database operations
        }
    }

    public void AddUser(ActionEvent actionEvent) {
        String nom = FXNom.getText();
        String adresse = FXAdresse.getText();
        String numtel = FXNumtel.getText();
        String mail = FXMail.getText();
        String mdp = FXMdp.getText();

        // Validation des champs
        if (nom.isEmpty() || adresse.isEmpty() || numtel.isEmpty() || mail.isEmpty() || mdp.isEmpty()) {
            afficherMessageErreur("Veuillez remplir tous les champs.");
            return; // Arrête l'ajout si les champs sont vides
        }

        // Validation de l'e-mail
        if (!mail.contains("@")) {
            afficherMessageErreur("L'e-mail doit contenir le caractère '@'.");
            return;
        }

        // Validation du numéro de téléphone
        if (!numtel.matches("\\d{8}")) {
            afficherMessageErreur("Le numéro de téléphone doit contenir exactement 8 chiffres.");
            return;
        }

        UserService ps = new UserService();

        try {
            ps.ajouteruser(new User(nom, adresse, numtel, mail, mdp));
            afficherMessageSucces("Utilisateur inséré avec succès !");
        } catch (SQLException s) {
            System.out.println(s);
        }
    }

    private void afficherMessageErreur(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    private void afficherMessageSucces(String message) {
        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
        successAlert.setTitle("Information");
        successAlert.setContentText(message);
        successAlert.showAndWait();
    }


    public void AddAdmin(ActionEvent actionEvent) {
        String nom = FXNom1.getText();
        String adresse = FXAdresse1.getText();
        String numtel = FXNumtel1.getText();
        String mail = FXMail1.getText();
        String mdp = FXMdp1.getText();

        // Valider le nom (non vide)
        if (nom.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer votre nom");
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
        if (mail.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer une adresse e-mail");
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
            ps.ajouter(new User(nom, adresse, numtel, mail, mdp));
            // Rafraîchir l'affichage après l'ajout de l'administrateur
            refreshUserTable();
        } catch (SQLException s) {
            System.out.println(s);
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialogue d'information");
        alert.setContentText("Admin inséré avec succès !");
        alert.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void initialize() {
        AFid.setCellValueFactory(new PropertyValueFactory<>("id"));
        AFnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        AFadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        AFnum.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        AFmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        AFmdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        AFrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        try {
            UserService userService = new UserService();
            ObservableList<User> personnes = FXCollections.observableArrayList(userService.afficher());
            AFtable.setItems(personnes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        AFtable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update the text fields with data from the selected user
                FXNom1.setText(newValue.getNom());
                FXAdresse1.setText(newValue.getAdresse());
                FXNumtel1.setText(newValue.getNum_tel());
                FXMail1.setText(newValue.getMail());
                FXMdp1.setText(newValue.getMdp());
            }
        });
    }

    private void refreshUserTable() {
        try {
            UserService userService = new UserService();
            ObservableList<User> personnes = FXCollections.observableArrayList(userService.afficher());
            AFtable.setItems(personnes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


