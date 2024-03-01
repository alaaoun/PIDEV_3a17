
package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.trotinette;
import javafx.scene.layout.VBox;
import services.trotinetteservice;
import GUI.cardcontroller;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        qrCodeImgModel.setVisible(false);
        traffickerProductionsGridPane();

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
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // GÃ©rer l'exception correctement
        }


    }


}
