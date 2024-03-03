package services;
import javafx.collections.ObservableList;
import models.reponse;

import java.sql.SQLException;
import java.util.List;

public interface repService <R>
{
    void ajouteReponse ( R r ) throws SQLException;


}