package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.reservation;
import services.ReservationService;
import services.reclamationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BackReservation implements Initializable {
    @FXML
    private ImageView cap;
    @FXML
    private TableColumn<reservation, String> coldatersv;
    @FXML
    private TableColumn<reservation, String> colheurersv;
    @FXML
    private TableColumn<reservation, Integer> colid;
    @FXML
    private TableColumn<reservation, Integer> colperiode;
    @FXML
    private TableColumn<reservation, Integer> colprix;
    @FXML
    private TableColumn<reservation, Integer> colqte;
    @FXML
    private Pane pnItems;
    @FXML
    private TableView<reservation> table;
    @FXML
    private TableColumn<reservation, String> colmodel;

    @FXML
    private HBox proB;

    @FXML
    private HBox recB;

    @FXML
    private HBox resB;

    @FXML
    private HBox userB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReservationService ser = new ReservationService();
        try {
            List<reservation> reservations = ser.recuperer();
            ObservableList<reservation> data = FXCollections.observableArrayList(reservations);

            // Assigning cell value factories to each column
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colmodel.setCellValueFactory(new PropertyValueFactory<>("model"));
            colqte.setCellValueFactory(new PropertyValueFactory<>("qte"));
            coldatersv.setCellValueFactory(new PropertyValueFactory<>("datersv"));
            colheurersv.setCellValueFactory(new PropertyValueFactory<>("heurersv"));
            colperiode.setCellValueFactory(new PropertyValueFactory<>("periode"));
            colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

            // Adding data to the table
            table.setItems(data);


            drawPieChart(ser.getMore150(), ser.getUnder150(), pnItems);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/backReservation.fxml"));
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
    private void drawPieChart(int more150, int under150, Pane pane) {
        // Create a PieChart
        PieChart pieChart = new PieChart();

        // Add data to the PieChart
        pieChart.getData().add(new PieChart.Data("Prix > 150 : " + more150, more150));
        pieChart.getData().add(new PieChart.Data("Prix < 150 : " + under150, under150));

        // Set colors for the PieChart slices
        pieChart.getData().get(0).getNode().setStyle("-fx-pie-color: green;");
        pieChart.getData().get(1).getNode().setStyle("-fx-pie-color: red;");

        // Set size for the PieChart
        pieChart.setPrefSize(500, 300);

        // Add the PieChart to the provided Pane
        pane.getChildren().add(pieChart);
    }
}

