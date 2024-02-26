package services;

import models.reservation;
import utlis.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements Iservice<reservation>{

    private Connection connection;
    public ReservationService(){
        connection = MyDatabase.getInstance().getConnection();

    }
    @Override
    public void ajouter(reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (model, qte, datersv, heurersv, periode, prix) VALUES ('" + reservation.getModel() + "', " + reservation.getQte() + ", '" + reservation.getDatersv() + "', '" +  reservation.getHeurersv() +  "', " + reservation.getPeriode() + ", " + reservation.getPrix() + ")";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(reservation reservation) throws SQLException {
        try {
            String sql = "UPDATE reservation SET model = '" + reservation.getModel() + "', "
                    + "qte = " + reservation.getQte() + ", "
                    + "datersv = '" + reservation.getDatersv() + "', "
                    + "heurersv = '" + reservation.getHeurersv() + "', "
                    + "periode = " + reservation.getPeriode() + ", "
                    + "prix = " + reservation.getPrix() + " "
                    + "WHERE id = " + reservation.getId();

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la réservation : " + e.getMessage());
            e.printStackTrace(); // Ceci imprime la trace de la pile complète de l'exception
            throw e; // Re-lance l'exception pour qu'elle soit gérée à un niveau supérieur si nécessaire
        }
    }
    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la réservation avec l'ID " + id + " : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<reservation> recuperer() throws SQLException {
        String sql = "select * from reservation";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<reservation> list = new ArrayList<>();
        while (rs.next()){
            reservation r = new reservation();
            r.setId(rs.getInt("id"));
            r.setQte(rs.getInt("qte"));
            r.setPeriode(rs.getInt("periode"));
            r.setPrix(rs.getInt("prix"));
            r.setModel(rs.getString("model"));
            r.setHeurersv(rs.getString("heurersv"));
            r.setDatersv(rs.getString("datersv"));
            list.add(r);

        }
        return list;
    }
}
