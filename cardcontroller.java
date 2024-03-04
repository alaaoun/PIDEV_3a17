package GUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import  models.trotinette;
import models.trotinette;

import javafx.fxml.Initializable;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class cardcontroller {

    @FXML
    private HBox qrCodeCommand;

    @FXML
    private HBox addcart;

    public HBox getAddcart() {
        return addcart;
    }

    @FXML
    private Text chid;

    @FXML
    private Text cid;

    @FXML
    private Text did;



    @FXML
    private Text vidd;





    public void setProductData(trotinette trotinette) throws SQLException {


        vidd.setText("vitesse (kmh) :" + trotinette.getVitesse());
        chid.setText("charge  : " + trotinette.getCharge());
        cid.setText("couleur  : " + trotinette.getCouleur());
        did.setText("dispo   :  " + trotinette.getDispo() );


        // add product to cart btn



qrCodeCommand.setOnMouseClicked(event -> {

        // Achats.setAchatId(achat.getId());

        String text =  " vitesse : " + trotinette.getVitesse()
                + "\ncharge: " +trotinette.getCharge() + "\n couleur: "
                +trotinette.getCouleur()+ "\nAdresse: " +trotinette.getDispo();
        // Créer un objet QRCodeWriter pour générer le QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // Générer la matrice de bits du QR code à partir du texte saisi
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            // Convertir la matrice de bits en image BufferedImage
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            // Enregistrer l'image en format PNG
            // File outputFile = new File("qrcode.png");
            // ImageIO.write(bufferedImage, "png", outputFile);
            // Afficher l'image dans l'interface utilisateur

            ImageView qrCodeImg = (ImageView) ((Node) event.getSource()).getScene().lookup("#qrCodeImg");


            qrCodeImg.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

            HBox qrCodeImgModel = (HBox) ((Node) event.getSource()).getScene().lookup("#qrCodeImgModel");
            qrCodeImgModel.setVisible(true);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    });

}}


