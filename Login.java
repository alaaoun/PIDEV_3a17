package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyDataBase;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

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
        MyDataBase connectNow = MyDataBase.getInstance();
        Connection connectDB = connectNow.getConn();
        String verifylogin = "SELECT count(1), role FROM utlilisateur WHERE adresse= '" + FXusername.getText() + "' AND mdp= '" + FXpassword.getText() + "' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifylogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    String role = queryResult.getString("role");
                    FXloginmessage.setText("Welcome");

                    if (role.equals("Admin")) {

                        openAdminInterface();
                    } else {
                        FXloginmessage.setText("Welcome User");

                    }
                } else {
                    FXloginmessage.setText("Invalid login. Please try again.");
                } {

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
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    }

