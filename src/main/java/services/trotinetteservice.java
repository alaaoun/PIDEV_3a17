package services;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import models.trotinette;
import models.station;
import utils.MYdatabase;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class trotinetteservice implements Iservice<trotinette>{
    private Connection connection;
    public trotinetteservice(){
        connection = MYdatabase.getInstance().getConnection();
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

    public Map<String, Long> getTrotinetteCountByStationWithLieu() throws SQLException {
        Map<String, Long> trotinetteCount = new HashMap<>();
        String req = "SELECT s.lieu, COUNT(t.id_trotinette) AS trotinette_count " +
                "FROM trotinette t " +
                "INNER JOIN station s ON t.id_station = s.id_station " +
                "GROUP BY s.lieu";

        try (Statement stmt = connection.createStatement(); ResultSet res = stmt.executeQuery(req)) {
            while (res.next()) {
                String lieu = res.getString("lieu");
                long trotinetteCounts = res.getLong("trotinette_count");
                trotinetteCount.put(lieu, trotinetteCounts);
            }
        }

        return trotinetteCount;
    }



    @Override
    public void ajouter(trotinette trotinette) throws SQLException {

        String sql = "insert into trotinette (vitesse, charge, couleur, dispo, id_station) " +
                "values('" + trotinette.getVitesse() + "','" + trotinette.getCharge()
                + "','" + trotinette.getCouleur() +"','" + trotinette.getDispo() +"','" +trotinette.getId_station() +"')";

        Statement statement =  connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifer(trotinette trotinette,int id_trotinette) throws SQLException {
        String sql = "UPDATE trotinette SET vitesse = ?, charge = ?, couleur = ? ,   dispo = ?, id_station = ?  WHERE id_trotinette = ?";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, trotinette.getVitesse());
        preparedStatement.setInt(2, trotinette.getCharge());
        preparedStatement.setString(3,trotinette.getCouleur());
        preparedStatement.setString(4, trotinette.getDispo());
        preparedStatement.setInt(5, trotinette.getId_station());
        preparedStatement.setInt(6, trotinette.getId_trotinette());


        preparedStatement.executeUpdate();

    }





    @Override
    public void supprimer(int id_trotinette) throws SQLException {
        String req = "DELETE FROM `trotinette` WHERE id_trotinette=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id_trotinette);
        preparedStatement.executeUpdate();
    }
    public List<trotinette> joiner() throws SQLException {
        List<trotinette> trotinette = new ArrayList<>();
        String req = "SELECT * FROM trotinette t INNER JOIN station s ON t.id_station=s.id_station;";

        Statement st = connection.createStatement();
        ResultSet rs = st. executeQuery(req);
        while (rs.next()) {
            trotinette t = new trotinette();

            t.setId_trotinette(rs.getInt("id_trotinette"));
            t.setId_station(rs.getInt( "id_station")); ;
            t.setCharge(rs.getInt( "charge"));
            t.setVitesse(rs.getInt( "vitesse"));
            t.setCouleur(rs.getString(  "couleur"));
            t.setDispo(rs.getString(  "dispo"));
            trotinette.add(t); }
            return trotinette;}
    @Override
    public  List<trotinette> recuperer() throws SQLException {
        String sql = "select * from trotinette";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<trotinette> list = new ArrayList<>();
        while (rs.next()){
            trotinette p = new trotinette();
            p.setId_trotinette(rs.getInt("id_trotinette"));
            p.setVitesse(rs.getInt("vitesse"));
            p.setCharge(rs.getInt("charge"));
            p.setCouleur(rs.getString("couleur"));
            p.setDispo(rs.getString("dispo"));
            p.setId_station(rs.getInt("id_station"));
            list.add(p);

        }
        return list;
    }
    public List<trotinette> searchProducts(String search) {
        List<trotinette> trotinetteList = new ArrayList<>();
        try {
            String query = "SELECT * FROM trotinette WHERE couleur LIKE ? OR dispo LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcours du résultat de la requête
            while (resultSet.next()) {
                trotinette trotinette = new trotinette();
                trotinette.setId_trotinette(resultSet.getInt("id_trotinette"));
                trotinette.setVitesse(resultSet.getInt("vitesse"));
                trotinette.setCharge(resultSet.getInt("charge"));
                trotinette.setCouleur(resultSet.getString("couleur"));
                trotinette.setDispo(resultSet.getString("dispo"));
                trotinette.setId_station(resultSet.getInt("id_station"));

                trotinetteList.add(trotinette);
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trotinetteList;
    }

    public int getNombreTrotinettesParStation(int idStation) throws SQLException {
        // Implémentez la logique pour récupérer le nombre de trotinettes pour une station spécifique
        // Par exemple, vous pouvez exécuter une requête SQL pour compter les trotinettes associées à une station donnée
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int nombreTrotinettes = 0;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion-equipements", "root", "");
            String query = "SELECT COUNT(*) FROM trotinette WHERE id_station = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idStation);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nombreTrotinettes = resultSet.getInt(1);
            }
        } finally {
            // Fermez les ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return nombreTrotinettes;
    }
}


