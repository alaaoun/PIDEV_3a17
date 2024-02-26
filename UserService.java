package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.User;
import utils.MyDataBase;

public class UserService implements IService<User> {
 private Connection conn;
public UserService(){
    conn= MyDataBase.getInstance().getConn();
}
    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO utlilisateur (nom, adresse, num_tel, mail, mdp, role) " +
                "VALUES ('" + user.getNom() + "', '" + user.getAdresse() + "', '" +
                user.getNum_tel() + "', '" + user.getMail() + "', '" + user.getMdp() + "', 'Admin')";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void ajouteruser(User user) throws SQLException {
        String sql = "INSERT INTO utlilisateur (nom, adresse, num_tel, mail, mdp, role) " +
                "VALUES ('" + user.getNom() + "', '" + user.getAdresse() + "', '" +
                user.getNum_tel() + "', '" + user.getMail() + "', '" + user.getMdp() + "', 'Utilisateurs')";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void ajouteradmin(User user) throws SQLException {
        String sql = "INSERT INTO utlilisateur (nom, adresse, num_tel, mail, mdp, role) " +
                "VALUES ('" + user.getNom() + "', '" + user.getAdresse() + "', '" +
                user.getNum_tel() + "', '" + user.getMail() + "', '" + user.getMdp() + "', '')";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void modifierUser(User user) throws SQLException {
        String sql = "UPDATE utlilisateur SET nom=?, adresse=?, num_tel=?, mail=?, mdp=? WHERE id=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, user.getAdresse());
        preparedStatement.setString(3, user.getNum_tel());
        preparedStatement.setString(4, user.getMail());
        preparedStatement.setString(5, user.getMdp());
        preparedStatement.setInt(6, user.getId());

        preparedStatement.executeUpdate();
    }



    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM utlilisateur WHERE id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }
    @Override
    public List<User> afficher() throws SQLException {
        Statement st = conn.createStatement();
        String req = "SELECT * FROM utlilisateur";
        List<User> personnes = new ArrayList<>();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            User p1=new User(rs.getInt("id"),rs.getString("nom"),rs.getString("num_tel")
                    ,rs.getString("mail"),rs.getString("adresse"),rs.getString("mdp"),rs.getString("role"));


            personnes.add(p1);

        }
        return personnes;
    }




}
