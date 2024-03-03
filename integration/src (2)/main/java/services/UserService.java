package services;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.User;
import utils.MYdatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserService implements IService2<User> {
 private Connection conn;
public UserService(){
    conn= MYdatabase.getInstance().getConnection();
}
    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO utlilisateur (nom, mail, num_tel, adresse, mdp, role_id) " +
                "VALUES ('" + user.getNom() + "', '" + user.getMail() + "', '" +
                user.getNum_tel()  + "', '" + user.getAdresse() + "', '" + user.getMdp() + "', 1)";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void ajouteruser(User user) throws SQLException {
        String sql = "INSERT INTO utlilisateur (nom, mail, num_tel, adresse, mdp, role_id) " +
                "VALUES ('" + user.getNom() + "', '" + user.getMail() + "', '" +
                user.getNum_tel()  + "', '" + user.getAdresse() + "', '" + user.getMdp() + "', 2)";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void ajouteradmin(User user) throws SQLException {
        String sql = "INSERT INTO utlilisateur (nom, mail, num_tel, adresse, mdp) " +
                "VALUES ('" + user.getNom() + "', '" + user.getNum_tel() + "', '" +
                user.getAdresse()  + "', '" + user.getMail() + "', '" + user.getMdp() + "', '')";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void modifierUser(User user) throws SQLException {
        String sql = "UPDATE utlilisateur SET nom=?,mail=?, num_tel=?,adresse=?, mdp=? WHERE id=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, user.getMail());
        preparedStatement.setString(3, user.getNum_tel());
        preparedStatement.setString(4, user.getAdresse());
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
        String req = "SELECT u.id, u.nom, u.mail, u.num_tel, u.adresse, u.mdp, r.role_name " +
                "FROM utlilisateur u " +
                "JOIN roles r ON u.role_id = r.id";
        List<User> personnes = new ArrayList<>();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            User p1 = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("mail"),
                    rs.getString("num_tel"), rs.getString("adresse"), rs.getString("mdp"), rs.getString("role_name"));

            personnes.add(p1);
        }
        return personnes;
    }
    public void updatePassword(User user) throws SQLException {
        String request = "UPDATE utlilisateur SET mdp=? WHERE mail=?";
        PreparedStatement preparedStatement = conn.prepareStatement(request);
        preparedStatement.setString(1, user.getMdp());
        preparedStatement.setString(2, user.getMail()); // Assuming ID is the primary key
        preparedStatement.executeUpdate();
    }
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM utlilisateur WHERE mail = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String nom = resultSet.getString("nom");
            String adresse = resultSet.getString("adresse");
            String numtel = resultSet.getString("num_tel");
            String mdp  = resultSet.getString("mdp");
            // Create and return a Student object
            return new User(nom,email,numtel,adresse, mdp);
        }

        // Return null if student not found with the given email
        return null;
    }
    public List<User> getUsersWithPhoneNumbers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, nom, num_tel FROM utlilisateur"; // Remplacez par votre requête réelle

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String num_tel = resultSet.getString("num_tel");

                User user = new User(id, nom, num_tel);
                userList.add(user);
            }
        }

        return userList;
    }
    public void generatePdfFromTableView(List<User> users) {
        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("User.pdf"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            document.open();

            // Create a table with three columns
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            // Add table headers
            table.addCell("ID");
            table.addCell("Nom");
            table.addCell("Adresse");
            table.addCell("Numéro");
            table.addCell("Mail");
            table.addCell("Mdp");
            table.addCell("Role");
            // Populate table with data
            for (User user : users) {
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getNom());
                table.addCell(user.getAdresse());
                table.addCell(user.getNum_tel());
                table.addCell(user.getMail());
                table.addCell(user.getMdp());
                table.addCell(user.getRole_id());



            }

            // Add the table to the document
            document.add(new com.itextpdf.text.Paragraph("User Data Table"));
            document.add(table);

            document.close();
            System.out.println("PDF created successfully. Check table_view.pdf");
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
        }
    }
}





