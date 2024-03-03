package services;
import models.recu;
import models.reservation;
import utils.MYdatabase;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecuService implements Rservice<recu>{

    private Connection connection;
    public RecuService(){
        connection = MYdatabase.getInstance().getConnection();

    }
    @Override
    public void payer(recu recu) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(recu.getDate());
        String sql = "INSERT INTO recu (name_client, impot, total, mtotal, date) VALUES ('" + recu.getName_client() + "', '" + recu.getImpot() + "', '" +  recu.getTotal() +  "', " + recu.getMtotal() + ", '" + formattedDate + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public List<recu> recupererRecu() throws SQLException {
        String sql = "SELECT * FROM recu ORDER BY date DESC LIMIT 1";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<recu> list = new ArrayList<>();
        while (rs.next()){
            recu ru = new recu();
            ru.setId(rs.getInt("id"));
            ru.setName_client(rs.getString("name_client"));
            ru.setImpot(rs.getDouble("impot"));
            ru.setTotal(rs.getDouble("total"));
            ru.setMtotal(rs.getDouble("mtotal"));
            ru.setDate(rs.getDate("date"));
            list.add(ru);

        }
        return list;
    }


}
