package GUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import  models.trotinette;
import services.trotinetteservice;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class cardcontroller {



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

    }



}


