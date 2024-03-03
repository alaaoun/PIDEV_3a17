
package GUI;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.trotinette;
import javafx.scene.layout.VBox;
import services.trotinetteservice;
import GUI.cardcontroller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class listeproduitcontroller implements Initializable {


    trotinetteservice ts = new trotinetteservice();



    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModel;


    @FXML
    private GridPane productsListContainer;


    @FXML
    void CLOSE(MouseEvent event) {
        qrCodeImgModel.setVisible(false);
    }
    @FXML
    private HBox gestionRes;
    @FXML
    private HBox gestionRec;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        qrCodeImgModel.setVisible(false);
        traffickerProductionsGridPane();
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

    @FXML
    void capture(MouseEvent event) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, "png", new File("screenshot.png"));

            // Afficher une alerte pour informer l'utilisateur que la capture d'écran a été effectuée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Capture d'écran");
            alert.setHeaderText(null);
            alert.setContentText("La capture d'écran a été effectuée avec succès.");

            // Ajouter un bouton "OK" à l'alerte
            alert.getButtonTypes().setAll(ButtonType.OK);

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            alert.showAndWait();

        } catch (AWTException | IOException ex) {
            // Gérer les exceptions en affichant un message d'erreur dans la console
            System.err.println(ex);
        }
    }

    private void traffickerProductionsGridPane() {
        int column = 0;
        int row = 1;
        try {
            List<trotinette> trotinetteList = ts.recuperer();
            for (int i = 0; i < trotinetteList.size(); i++) {

                cardcontroller trotinetteCardController = new cardcontroller();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/card.fxml"));
                fxmlLoader.setController(trotinetteCardController);
                VBox productCard = fxmlLoader.load();
                trotinetteCardController.setProductData(trotinetteList.get(i));
                if (column == 5) {
                    column = 0;
                    ++row;
                }
                productsListContainer.add(productCard,column++,row);
                GridPane.setMargin(productCard, new Insets(0, 20, 20, 10));
                int finalI = i;
                trotinetteCardController.getAddcart().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                       controller.idtro = trotinetteList.get(finalI).getId_trotinette();
                    }

                });
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // GÃ©rer l'exception correctement
        }


    }


}
