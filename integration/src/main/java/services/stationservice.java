package services;


import models.trotinette;
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

    public String getlieu(int id_station) throws SQLException {
        String lieu = ""; // Default value
        String req = "SELECT lieu FROM station WHERE id_station = ?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, id_station);
            try (ResultSet res = pre.executeQuery()) {
                if (res.next()) {
                    lieu = res.getString("lieu");
                }
            }
        }
        return lieu;
    }


    @Override
    public void modifer(station station, int id_station) throws SQLException {


        String sql = "UPDATE station SET lieu = ?, name = ?, etat = ?   WHERE id_station = ?";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, station.getLieu());
        preparedStatement.setString(2, station.getName());
        preparedStatement.setString(3,station.getEtat());
        preparedStatement.setInt(4, station.getId_station());


        preparedStatement.executeUpdate();

    }


    @Override
    public void supprimer(int id_station) throws SQLException {
        String req = "DELETE FROM `station` WHERE id_station=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id_station);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<station> recupererr() throws SQLException {
        String sql = "select * from station";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<station> list = new ArrayList<>();
        while (rs.next()){
            station p = new station();

            p.setLieu(rs.getString("lieu"));
            p.setEtat(rs.getString("etat"));
            p.setId_station(rs.getInt("id_station"));
            p.setName(rs.getString("name"));


            list.add(p);

        }
        return list;
    }

    }
