package services;

import models.reservation;
import utils.MYdatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements Iservice1<reservation>{

    private Connection connection;
    public ReservationService(){
        connection = MYdatabase.getInstance().getConnection();

    }
    @Override
    public void ajouter(reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (model, qte, datersv, heurersv, periode, prix,impot,total,mtotal) VALUES ('" + reservation.getModel() + "', " + reservation.getQte() + ", '" + reservation.getDatersv() + "', '" +  reservation.getHeurersv() +  "', " + reservation.getPeriode() + ", " + reservation.getPrix() + ", " + reservation.getImpot()+", "+reservation.getTotal()+", "+reservation.getMtotal()+ ")";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }


    @Override
    public void modifier(reservation reservation) throws SQLException {
        try {
            String sql = "UPDATE reservation SET model = ?, "
                    + "qte = ?, "
                    + "datersv = ?, "
                    + "heurersv = ?, "
                    + "periode = ?, "
                    + "prix = ? "
                    + "WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reservation.getModel());
            preparedStatement.setInt(2, reservation.getQte());
            preparedStatement.setString(3, reservation.getDatersv());
            preparedStatement.setString(4, reservation.getHeurersv());
            preparedStatement.setInt(5, reservation.getPeriode());
            preparedStatement.setInt(6, reservation.getPrix());
            preparedStatement.setInt(7, reservation.getId());

            preparedStatement.executeUpdate();

            // Retourne la réservation mise à jour

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
