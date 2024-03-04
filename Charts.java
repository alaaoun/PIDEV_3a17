package GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import models.User;
import services.UserService;
import javafx.scene.chart.CategoryAxis;
import java.sql.SQLException;
import java.util.List;

public class Charts {
    @FXML
public BarChart<String, Integer> barChart;

    @FXML
    public AnchorPane main_form;
    @FXML
    public CategoryAxis xAxis;

    UserService userService=new UserService();

    @FXML
    public void initialize() {
        try {
            List<User> users = userService.getUsersWithPhoneNumbers(); // Retrieve users from the database

            int orangeCount = 0;
            int oreedooCount = 0;
            int telecomCount = 0;

            for (User user : users) {
                String phoneNumber = user.getNum_tel();
                if (phoneNumber.startsWith("5")) {
                    orangeCount++;
                } else if (phoneNumber.startsWith("2")) {
                    oreedooCount++;
                } else if (phoneNumber.startsWith("9")) {
                    telecomCount++;
                }
            }

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Orange", orangeCount));
            series.getData().add(new XYChart.Data<>("Ooredoo", oreedooCount));
            series.getData().add(new XYChart.Data<>("Telecom", telecomCount));
            series.setName("lignes téléphoniques");
            xAxis.setCategories(FXCollections.observableArrayList("Orange", "Ooredoo", "Telecom"));
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
