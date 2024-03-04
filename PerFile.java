package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.User;
import services.UserService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    private HBox proB;
    @FXML
    private HBox userB;
    @FXML
    private HBox resB;
    @FXML
    private HBox recB;
    @FXML
    public TableColumn<User, String> AFrole;
    @FXML
    public TableView<User> AFtable;
    @FXML
   public Button deleteButton;

    @FXML
    public Button modifyButton;
    @FXML
    public Button Bpdf;

    @FXML
    public TextField search;
    UserService userService=new UserService();

    @FXML
    void modifer(ActionEvent event) {
        User selectedUser = AFtable.getSelectionModel().getSelectedItem();

        selectedUser.setNom(FXNom1.getText());
        selectedUser.setMail(FXMail1.getText());
        selectedUser.setNum_tel(FXNumtel1.getText());
        selectedUser.setAdresse(FXAdresse1.getText());
        selectedUser.setMdp(FXMdp1.getText());

        try {
            UserService userService = new UserService();
            userService.modifierUser(selectedUser);

            loadUsersData();
        } catch (SQLException e) {
            e.printStackTrace();
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
        //AFtable.getItems().remove(selectedUser);

        // Call the supprimer method to delete the user from the database
        try {
            userService.supprimer(selectedUser.getId());
            refreshUserTable();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions related to database operations
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

        if (nom.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer votre nom");
            return;
        }
        if (adresse.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer une adresse ");
            return;
        }
        if (numtel.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer votre numéro ");
            return;
        }
        if (numtel.length() != 8) {
            showAlert("Erreur de formulaire !", "Le numéro de téléphone doit contenir 8 caractères");
            return;
        }
        if (!numtel.startsWith("2") && !numtel.startsWith("5") && !numtel.startsWith("9")) {
            showAlert("Erreur de formulaire !", "Le numéro de téléphone doit commencer par 2, 5 ou 9.");
            return;
        }

        if (mail.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer une adresse e-mail");
            return;
        }
        if (!mail.contains("@")) {
            afficherMessageErreur("L'e-mail doit contenir le caractère '@'.");
            return;
        }


        if (mdp.isEmpty()) {
            showAlert("Erreur de formulaire !", "Veuillez entrer un mot de passe");
            return;
        }

        UserService ps = new UserService();
        try {
            ps.ajouter(new User(nom,mail,numtel,adresse, mdp));
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
        AFrole.setCellValueFactory(new PropertyValueFactory<>("role_id"));

        ObservableList<User> personnes;
        try {
            UserService userService = new UserService();
            personnes = FXCollections.observableArrayList(userService.afficher());
            AFtable.setItems(personnes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        AFtable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les champs de texte avec les données de l'utilisateur sélectionné
                FXNom1.setText(newValue.getNom());
                FXAdresse1.setText(newValue.getAdresse());
                FXNumtel1.setText(newValue.getNum_tel());
                FXMail1.setText(newValue.getMail());
                FXMdp1.setText(newValue.getMdp());
            }
        });
        FilteredList<User> filteredData = new FilteredList<>(personnes, b -> true);
    search.textProperty().addListener((observable, oldValue, newValue)-> {
        filteredData.setPredicate(User -> {
            if (newValue.isEmpty() || newValue.isBlank() ||newValue==null){
               return true;
            }
            String searchKeyword=newValue.toLowerCase();
            if(User.getNom().toLowerCase().indexOf(searchKeyword) > -1){
                return true;
            } else if (User.getNum_tel().toString().indexOf(searchKeyword) > -1) {
                return true;
            }else
                return false;
        });
    });
        SortedList<User> sortedData=new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(AFtable.comparatorProperty());
        AFtable.setItems(sortedData);

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
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/backReservation.fxml"));
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

    private void refreshUserTable() {
        try {
            UserService userService = new UserService();
            ObservableList<User> personnes = FXCollections.observableArrayList(userService.afficher());
            AFtable.setItems(personnes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleBstatButton(ActionEvent event) {
        try {
            // Load the stat.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stat.fxml"));
            Parent root = loader.load();

            // Create a new stage for the stat view
            Stage stage = new Stage();
            stage.setTitle("Statistics");
            stage.setScene(new Scene(root));
            stage.show();

            // Optionally, close the current stage (if needed)
            // ((Stage) BstatButton.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void generatePdfButtonClicked() {
        // Handle the button click to generate the PDF
        userService.generatePdfFromTableView(AFtable.getItems()); // Pass the list of users

        // Open the PDF file
        File file = new File("table_view.pdf");
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }}}



