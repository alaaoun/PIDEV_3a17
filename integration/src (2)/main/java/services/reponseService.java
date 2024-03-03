package services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.reponse;
import utils.MYdatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class reponseService implements repService<reponse> {

    private Connection connection;

    public reponseService() {
        connection = MYdatabase.getInstance().getConnection();
    }
    ;

    @Override
    public void ajouteReponse(reponse reponse) throws SQLException {
        String req = "insert into reponse (emailrep, response, daterep,idrec) values  ('" + reponse.getEmailrep() + "','" + reponse.getResponse() + "','" + reponse.getDaterep()+ "','" + reponse.getIdRec() + "')";
        Statement ste = connection.createStatement();
        ste.executeUpdate(req);
    }

}