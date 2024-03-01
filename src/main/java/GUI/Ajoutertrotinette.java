package GUI;
import facebook4j.FacebookException;
import facebook4j.PostUpdate;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.station;
import nl.captcha.Captcha;
import services.stationservice;
import services.trotinetteservice;

import java.io.IOException;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.trotinette;


public class Ajoutertrotinette implements Initializable {
    trotinetteservice trotinetteservice=new trotinetteservice();
    @FXML
    private TableView<trotinette> tabletrotinette;

    @FXML
    private TableColumn<trotinette, Integer> v;
    @FXML
    private TableColumn<trotinette, Integer> is;

    @FXML
    private TableColumn<trotinette, Integer> it;
    @FXML
    private TableColumn<trotinette, String> d;
    @FXML
    private TableColumn<trotinette, String> c;

    @FXML
    private TableColumn<trotinette, Integer> ch;




    @FXML
    private TextField vitesseid;
    @FXML
    private TextField id;
    @FXML
    private TextField chargeid;
    @FXML
    private TextField couleurid;
    @FXML
    private TextField dispoid;
    @FXML
    private TextField etatid;

    @FXML
    private ImageView cap;

    @FXML
    private TextField captchaT;

    private Captcha captcha;

    @FXML
    private TextField lieuid;

    @FXML
    private TextField nomid;

    @FXML
    private TextField idstation;
    @FXML
    private TextField fb;
    @FXML
    private TextField ids;




   public Captcha setCaptcha()
    {
        Captcha captchaV = new Captcha.Builder(250, 150)
                .addText()
                .addBackground()
                .addNoise()
                .addBorder()
                .build();

        System.out.println(captchaV.getImage());
        Image image = SwingFXUtils.toFXImage(captchaV.getImage(), null);
        cap.setImage(image);
        return captchaV;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        captcha = setCaptcha();
    }
@FXML
void p(MouseEvent event) throws SQLException {
    trotinette trotinette = new trotinette();

    trotinetteservice trotinetteservice = new trotinetteservice();
    // Remplissage des données
    List<trotinette> trotinettes = trotinetteservice.recuperer();

    String appId = "7280106152045125";
    String appSecret = "04a086210d8017c5edfe868894c612de";
    String accessTokenString ="EABndN80eqkUBOZCYXyq5LPZCT6PdxSMfkpIJzBty85l8HoEHHL9djiCYbKD411SdG6Ke2UQBZAZCSlefiybjZAGvJ3Mj0bszHvYW98j0ZBb36xNJGaqvEyvZA7tCm4aLuIZA36lYtNLI0VspZBz1X2ZCZBRik6CwOypD2NH4eGjmeilga3NaO9J1rDZCjJqlSmNqpFXurc3Hdxu4TRo2ajFSM92Mkj5CFlC0e31wZAaY1oTWEUeIZD";


        String message = fb.getText();
    Facebook facebook = new FacebookFactory().getInstance();
    facebook.setOAuthAppId(appId, appSecret);
    facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));
    try {
        facebook.postFeed(new PostUpdate(message)); // Utilisez la méthode postFeed avec un objet PostUpdate pour publier un message sur Facebook
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Message posté sur Facebook avec succès");
        alert.show();
    } catch (FacebookException e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Message posté sur Facebook avec succès : " );
        alert.show();
    }


    // Votre code pour poster le message sur Facebook en utilisant Facebook4J
    }





    @FXML
    void ajoutertrotinette(MouseEvent event) throws SQLException {
        // Check if any of the fields are empty
        trotinetteservice trotinetteservice=new trotinetteservice();
        int idStation = Integer.parseInt(idstation.getText());
        int nombreTrotinettes =trotinetteservice.getNombreTrotinettesParStation(idStation);
        if (nombreTrotinettes >= 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La station a atteint le maximum de trotinettes (3)");
            alert.show();
            return;
        }

        // Si la limite n'est pas atteinte, procéder à l'ajout de la trotinette
        // Check if any of the fields are empty
        if (vitesseid.getText().isEmpty() || chargeid.getText().isEmpty() || couleurid.getText().isEmpty() || dispoid.getText().isEmpty() || idstation.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
            return; // Exit the method if any field is empty
        }
if(captcha.isCorrect(captchaT.getText())) {
    // If all fields are filled, proceed with adding the scooter
    try {
        trotinette trotinette = new trotinette(Integer.parseInt(vitesseid.getText()), Integer.parseInt(chargeid.getText()), couleurid.getText(), dispoid.getText(), Integer.parseInt(idstation.getText()));
        trotinetteservice.ajouter(trotinette);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Trotinette a été ajouté avec succès");
        alert.show();
    } catch (NumberFormatException e) {
        // Handle the case where vitesseid or chargeid or idstation is not a valid number
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Veuillez saisir des valeurs numériques pour les champs Vitesse, Charge et ID Station");
        alert.show();
    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.show();
    }
}
else {
    captcha = setCaptcha();
    captchaT.setText("");
}
    }

    @FXML
    public void supprimertrotinette(ActionEvent actionEvent) throws SQLException {
        // Récupérer l'ID de la trotinette à supprimer (supposons qu'il soit stocké dans un champ de texte nommé idTrotinette)
        int id_trotinette = Integer.parseInt(id.getText());

        // Créer une instance de TrotinetteService
        trotinetteservice trotinetteService =new trotinetteservice();

        try {
            // Appeler la méthode de suppression de trotinette en utilisant l'ID récupéré
            trotinetteService.supprimer(id_trotinette);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Trotinette supprimée avec succès");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur, afficher une alerte d'erreur avec le message d'erreur obtenu
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void excel(MouseEvent event) throws IOException {
        ExcelHandler handler = new ExcelHandler();
        String filePath = "example.xlsx";
        handler.writeExcelFile(filePath);
        try {
            handler.readExcelFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void afficherrtrotinette(MouseEvent event) throws IOException {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/afficher.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        couleurid.getScene().setRoot(root);

    }
    @FXML
    void front(ActionEvent event) throws IOException {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/front.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        couleurid.getScene().setRoot(root);

    }
    @FXML
    void stat(ActionEvent event) throws IOException {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/statistique.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        couleurid.getScene().setRoot(root);

    }
    @FXML
    void ajouterstation(MouseEvent event) throws SQLException {
        // Check if any of the fields are empty
        if (lieuid.getText().isEmpty() || nomid.getText().isEmpty() || etatid.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
            return; // Exit the method if any field is empty
        }

        // If all fields are filled, proceed with adding the station
        try {
            station station = new station(lieuid.getText(), nomid.getText(), etatid.getText());
            stationservice stationservice = new stationservice();
            stationservice.ajouter(station);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Station a été ajoutée avec succès");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML

    public void supprimerstation(ActionEvent actionEvent) throws SQLException {
        // Récupérer l'ID de la trotinette à supprimer (supposons qu'il soit stocké dans un champ de texte nommé idTrotinette)
        int id_station = Integer.parseInt(ids.getText());

        // Créer une instance de TrotinetteService
        stationservice stationservice =new stationservice();

        try {
            // Appeler la méthode de suppression de trotinette en utilisant l'ID récupéré
            stationservice.supprimer(id_station);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("station supprimée avec succès");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur, afficher une alerte d'erreur avec le message d'erreur obtenu
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


  /*@FXML
    void getData(MouseEvent event) {



    }*/



    }

