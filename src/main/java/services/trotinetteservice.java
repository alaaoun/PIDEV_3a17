package services;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import models.trotinette;
import utils.MYdatabase;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class trotinetteservice implements Iservice<trotinette>{
    private Connection connection;
    public trotinetteservice(){
        connection = MYdatabase.getInstance().getConnection();
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
            String query = "SELECT * FROM trotinette WHERE couleur LIKE ? OR description LIKE ?";
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

}
