package GUI;
import javafx.scene.input.MouseEvent;
import models.trotinette;
import services.trotinetteservice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StatistiqueController implements  Initializable {
    trotinetteservice trotinetteservice=new trotinetteservice();
    @FXML
    private Pane content_area;


    @FXML
    private PieChart pieChart;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> Management = FXCollections.observableArrayList( "Liste Categories", "Statistiques");


        populatePieChart();

    }


    @FXML
    void retour(MouseEvent event) {


        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Ajoutertrotinette.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pieChart.getScene().setRoot(root);

    }

    private void populatePieChart() {
        try {
            Map<String, Long> trotinetteCountsByLocation = trotinetteservice.getTrotinetteCountByStationWithLieu();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (Map.Entry<String, Long> entry : trotinetteCountsByLocation.entrySet()) {
                String lieu = entry.getKey();
                long trotinetteCount = entry.getValue();

                // Create a label that includes the location and the number of trotinettes
                String label = lieu + " (" + trotinetteCount + " trotinettes)";

                // Add data to the PieChart with the customized label
                PieChart.Data data = new PieChart.Data(label, trotinetteCount);
                pieChartData.add(data);
            }
            pieChart.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}








