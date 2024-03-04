package GUI;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Recu {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label daterecu;

    @FXML
    private Label mtotalrecu;

    @FXML
    private Label nomclient;

    @FXML
    private Label refrecu;

    @FXML
    private Label taxrecu;

    @FXML
    private Label totalrecu;

    public Label getDaterecu() {
        return daterecu;
    }


    public Label getMtotalrecu() {
        return mtotalrecu;
    }

    public void setMtotalrecu(Double mtotalrecu) {
        this.mtotalrecu.setText(String.valueOf(mtotalrecu));
    }

    public Label getNomclient() {
        return nomclient;
    }

    public void setNomclient(String nomclient) {
        this.nomclient.setText(nomclient);
    }

    public Label getRefrecu() {
        return refrecu;
    }

    public void setRefrecu(int refrecu) {
        this.refrecu.setText(String.valueOf(refrecu));
    }

    public Label getTaxrecu() {
        return taxrecu;
    }

    public void setTaxrecu(Double taxrecu) {
        this.taxrecu.setText(String.valueOf(taxrecu));
    }

    public Label getTotalrecu() {
        return totalrecu;
    }

    public void setTotalrecu(Double totalrecu) {
        this.totalrecu.setText(String.valueOf(totalrecu));
    }



    @FXML
    void initialize() {

    }



    public void setDaterecu(java.util.Date date) {
        // Convertir java.util.Date en java.sql.Timestamp
        Timestamp timestamp = new Timestamp(date.getTime());

        // Formater la date au format souhaité (avec date et heure)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(timestamp);

        // Assigner la chaîne de caractères formatée à l'étiquette
        daterecu.setText(dateString);
    }

}
