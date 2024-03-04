package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import services.EmailSenderService;
import services.PasswordResetService;
import services.UserService;
import utils.MYdatabase;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static GUI.CodeGenerator.generateRandomCode;

public class Login implements Initializable {


    public Button FXcancel;

    @FXML
    public Button FXlogin;

    @FXML
    public Button FXlogin1;

    @FXML
    public Label FXloginmessage;

    @FXML
    public PasswordField FXpassword;

    @FXML
    public TextField FXusername;
    @FXML
    public Button forgotPassword1;
    @FXML
    public Hyperlink forgotPassword;

    @FXML
    public CodeEntry codeEntryController = new CodeEntry();
    @FXML
    public final PasswordResetService passwordResetService = new PasswordResetService();
    public final EmailSenderService emailService = new EmailSenderService("smtp.gmail.com","587","medazizarbi1@gmail.com\n","isqn kzte fdrk zgzf\n\n");


    @FXML
    void canceButtonOnAction(ActionEvent event) {
        Stage stage =(Stage) FXcancel.getScene().getWindow();
        stage.close();
    }

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

    @FXML
    void loginButtonOnAction(ActionEvent event) {
        if (FXusername.getText().isBlank() == false && FXpassword.getText().isBlank() == false) {

            validateLogin();
        } else {FXloginmessage.setText("Please enter username and password");
        }
    }
    public void validateLogin() {
        MYdatabase connectNow = MYdatabase.getInstance();
        Connection connectDB = connectNow.getConnection();
        String adminName;
        String verifylogin = "SELECT count(1), r.role_name, u.nom " +
                "FROM utlilisateur u " +
                "JOIN roles r ON u.role_id = r.id " +
                "WHERE u.mail= '" + FXusername.getText() + "' AND u.mdp= '" + FXpassword.getText() + "' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifylogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    String role = queryResult.getString("role_name");
                    adminName = queryResult.getString("nom");
                    FXloginmessage.setText("Welcome " + adminName);

                    if (role.equals("admin")) {
                        FXloginmessage.setText("Welcome Admin " + adminName);
                        openAdminInterface();
                    } else {
                        controller.nomUser=adminName;
                        FXloginmessage.setText("Welcome User " + adminName);
                        try {
                            Stage primaryStage=new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
                            Parent root = loader.load();
                            primaryStage.setTitle("Afficher");
                            primaryStage.setScene(new Scene(root, 1000, 671));
                            primaryStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    FXloginmessage.setText("Invalid login. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openAdminInterface() {
        try {
            Stage primaryStage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichage.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Afficher");
            primaryStage.setScene(new Scene(root, 1000, 671));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void sendPasswordResetEmail(String recipientEmail, String username, String password) {
        // Generate a unique token for password reset
        PasswordResetService passwordResetService = new PasswordResetService();
        String token = passwordResetService.generateToken(recipientEmail);

        // Email message details
        String subject = "Password Reset Request";
        String body = "Dear User,\n\nTo reset your password, please click on the following link:\n\nhttp://example.com/reset_password?token=" + token + "\n\nThis link will expire in 1 hour.\n\nBest regards,\nYour App Team";

        // Set up email properties
        // Existing code...

        // Create a session with authentication
        // Existing code...
    }



    @FXML
    void forgotPassword(ActionEvent event) {
        String recipientEmail = FXusername.getText();
        try {
            UserService userService = new UserService();

            User user = userService.getUserByEmail(recipientEmail);
            System.out.println(user);
            if (user != null) {
                String code = generateRandomCode();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/codeEntry.fxml"));
                Parent root = loader.load();
                CodeEntry codeEntryController = loader.getController();

                // Pass the student object to the Code Entry screen
                codeEntryController.initData(user, code);

                // Send the password reset email
                emailService.sendPasswordResetEmail(recipientEmail, code);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                displayErrorMessage("User not found with the provided email.");
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            displayErrorMessage("Failed to send password reset email. Please try again later.");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private void loadCodeEntryFXML(String code) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/codeEntry.fxml"));
            Parent root = loader.load();
            codeEntryController = loader.getController(); // Retrieve the controller instance
            codeEntryController.onCodeGenerated(code); // Set the generated code
            // Optionally, you can set additional properties or data to the controller
            // codeEntryController.setSomeProperty(someValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }

