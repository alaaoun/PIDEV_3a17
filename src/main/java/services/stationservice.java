package services;


import utils.MYdatabase;
import java.sql.*;
import models.station;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class stationservice implements Sservice<station>{
    private Connection connection;
    public stationservice(){
        connection = MYdatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(station station) throws SQLException {

        String sql = "insert into station (lieu,etat,name) " +
                "values('" + station.getLieu() + "','" + station.getEtat() + "','" + station.getName() + "')";

        Statement statement =  connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifer(station station) throws SQLException {

    }

    @Override
    public void supprimer(int id_station) throws SQLException {

    }

    @Override
    public List<station> recuperer() throws SQLException {
        return null;
    }
}
