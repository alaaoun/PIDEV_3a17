package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.reclamation;
import services.reclamationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class StatController implements Initializable {

    @FXML
    private Pane pnItems;
    @FXML
    private HBox proB;
    @FXML
    private HBox userB;
    @FXML
    private HBox resB;
    @FXML
    private HBox recB;
    @FXML
    private Canvas pnItems1;

    /**
     * Initializes the controller class.
     */
    private void drawPieChart(int activeUsers, int blockedUsers, Pane pane) {
        // Create a PieChart
        PieChart pieChart = new PieChart();

        // Add data to the PieChart
        pieChart.getData().add(new PieChart.Data("Reclamations traitées : " + activeUsers, activeUsers));
        pieChart.getData().add(new PieChart.Data("Reclamations non traitées : " + blockedUsers, blockedUsers));

        // Set colors for the PieChart slices
        pieChart.getData().get(0).getNode().setStyle("-fx-pie-color: green;");
        pieChart.getData().get(1).getNode().setStyle("-fx-pie-color: red;");

        // Set size for the PieChart
        pieChart.setPrefSize(500, 300);

        // Add the PieChart to the provided Pane
        pane.getChildren().add(pieChart);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reclamationService Us = new reclamationService();
        drawPieChart(Us.getRecWithRep(), Us.getRecWithoutRep(), pnItems);

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

    @FXML
    void mail(ActionEvent event) throws SQLException {
        reclamationService rec = new reclamationService();
        List<reclamation> recl = rec.getReclamationList();

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

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation d'envoie");
            alert.setHeaderText("Voulez-vous envoyez les mails?");
            alert.setContentText("Cette action est requise.");

            // Show the confirmation dialog and wait for the user's response
            Optional<ButtonType> resultt = alert.showAndWait();
            if (resultt.get() == ButtonType.OK) {
            for(reclamation r : recl) {
                if(r.getStater() == 0) {
                    // Create a new email message
                    Message msg = new MimeMessage(session);

                    // Set the "From" address for the email
                    // msg.setFrom(new InternetAddress("ahmed.benabid2503@gmail.com"));

                    // Add the "To" address for the email (including the recipient's name)
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(r.getEmailrec()));

                    // Set the subject and body text for the email
                    msg.setSubject("Reponce de reclamation");
                    msg.setText("Mr(s) ,vous pouvez contacter notre support.");

                    // Create an alert to notify the user that the email was sent successfully


                    // Send the email


                        System.out.println("En cours d'envoie...");
                        Transport.send(msg);
                        System.out.println("Envoyé avec succès !");




                    // Print a message to the console to indicate that the email was sent successfully


                }}
            } else {
                // Close the dialog and do nothing
                alert.close();
                System.out.println("Echec d'envoie!");
            }
        } catch (AddressException e) {
            // Create an alert to notify the user that there was an error with the email address
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }


    }




}